package us.evosys.hadoop.jobs.ver2

import org.apache.hadoop.conf.{Configured}
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.slf4j.{Logger, LoggerFactory}
import scala.collection.JavaConversions._
import org.apache.hadoop.util.{ToolRunner, Tool}
import org.apache.hadoop.fs.{Path}
import org.apache.hadoop.mapreduce.lib.input.{TextInputFormat, FileSplit, FileInputFormat}
import org.apache.hadoop.mapreduce._
import java.lang.IllegalStateException
import java.io.{DataOutput, DataInput}
import org.apache.hadoop.io.{WritableUtils, LongWritable, Writable, Text}
import util.matching.Regex
import org.apache.hadoop.mapred.lib.MultipleTextOutputFormat
import collection.Iterator
import us.evosys.hadoop.jobs.ver1.GenomeIngestionMapper.WINDOW_SIZE
import us.evosys.hadoop.jobs.{NonSplitableInputFormat, HImplicits}


/**
 * Created by IntelliJ IDEA.
 * User: smishra
 * Date: 1/4/12
 * Time: 8:40 AM
 * To change this template use File | Settings | File Templates.
 */

object GenomeIngestor extends Configured with Tool with HImplicits {
  val logger: Logger = LoggerFactory.getLogger(getClass)

  def run(args: Array[String]): Int = {
    val conf = getConf
    conf.setQuietMode(false)
    conf.set(WINDOW_SIZE, 3 + "")


    val job: Job = new Job(conf, "Genome Ingestor Ver2")

    job.setInputFormatClass(classOf[NonSplitableInputFormat])

    job.setJarByClass(this.getClass)

    job.setMapperClass(classOf[GenomeIngestionMapper])
    job.setMapOutputKeyClass(classOf[Text])
    job.setMapOutputValueClass(classOf[Node])

    job.setCombinerClass(classOf[GenomeIngestionReducer])

    job.setReducerClass(classOf[GenomeIngestionReducer])

    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[Node])
    //job.setNumReduceTasks(0)
    for (i <- 0 to args.length - 2) {
      logger.info("processing {}: {}", i, args(i))
      FileInputFormat.addInputPath(job, args.apply(i))
    }

    FileOutputFormat.setOutputPath(job, args.last)

    job.waitForCompletion(true) match {
      case true => 0
      case false => 1
    }
  }

  def main(args: Array[String]) {
    System.exit(ToolRunner.run(this, args))
  }
}

private case class Window(size: Int) {
  var offset: Int = 0 - size
  var w: List[Char] = List()
  val re: Regex = "(?i)A|T|G|C".r
  val charSeq: Array[Char] = new Array(1)

  def slide(c: Char): Option[Node] = {
    charSeq(0) = c
    if (re.findFirstIn(charSeq).getOrElse("") != "") {
      offset += 1
      w ::= c
      if (w.size == size) {
        val value = w.reverse
        w = w.take(size - 1)

        return Some(new Node(value.mkString,offset))
      }
    }

    None
  }
}

protected[ver2]  object Node {
  def sortByOffset(node: Node): Node = node.toList.sortBy(_.offset).reduceLeft(_.add(_))

  def sort(node: Node): Node = node.toList.sorted.reduceLeft(_.add(_))
}

protected[ver2] class Node(var name: String, var chrName: String, var offset: Int) extends Writable with Iterable[Node] with Ordered[Node] {
  def this() = this ("", "", 0)

  def this(name: String, offset: Int) = this (name, "", offset)

  var firstSib: Node = _

  private var lastSib: Node = _
  private var freq: Int = 0

  var prevSibDist: Int = 0

  def getFreq = freq match {
    case 0 =>
      var node = this
      do {
        freq += 1
        node = node.firstSib
      } while (node != null)
      freq
    case _ => freq
  }

  def add(sibling: Node): Node = {
    if (!this.name.equalsIgnoreCase(sibling.name) || !this.chrName.equalsIgnoreCase(sibling.chrName)) {
      throw new IllegalArgumentException(String.format("Expected %s %s Found %s %s", this.name, this.chrName, sibling.name, sibling.chrName))
    }

    if (this.offset == sibling.offset) {
      System.err.println("skipping add " + sibling.mkString)
      return this
    }

    freq = 0
    lastSib match {
      // it there are siblings
      case n: Node =>
        n.firstSib = sibling
        n.lastSib = sibling
        this.lastSib = sibling
      case _ => //if there are no siblings
        sibling.prevSibDist = sibling.offset - offset
        firstSib = sibling;
        lastSib = sibling
    }

    this
  }

  def merge(that: Node) = {
    var o = that
    while (o != null) {
      this.add(o)
      o = o.firstSib
    }

    this
  }

  def asList: List[Node] = {
    var l: List[Node] = List()
    l = this :: l

    var node: Node = this.firstSib

    while (node != null) {
      val tmp = node.copy
      l = tmp :: l
      node = node.firstSib
    }

    l.reverse
  }

  def copy() = {
    val node = new Node(this.name, this.chrName, this.offset)
    node.prevSibDist = this.prevSibDist
    node
  }

  def deepCopy = {
    val node = new Node(this.name, this.chrName, this.offset)
    var sib = this.firstSib
    while (sib != null) {
      node.add(new Node(sib.name, sib.chrName, sib.offset))
      sib = sib.firstSib
    }
    node
  }

  override def mkString = name + ":" + chrName + ":" + offset + ":" + prevSibDist

  override def toString = {
    val sb: StringBuilder = new StringBuilder(this.getFreq + ":" + offset + ",")
    var node: Node = this.firstSib
    var l: List[Int] = List()
    while (node != null) {
      l = node.offset :: l
      node = node.firstSib
    }
    sb.append(l.mkString(","))
    sb.toString
  }

  def write(out: DataOutput) {
    WritableUtils.writeString(out, this.name + ":" + this.chrName)
    WritableUtils.writeVInt(out, getFreq)
    WritableUtils.writeVInt(out, offset)

    var node: Node = this.firstSib
    while (node != null) {
      WritableUtils.writeVInt(out, node.offset)
      node = node.firstSib
    }
  }

  def readFields(in: DataInput) {
    val node = new Node()
    val l = WritableUtils.readString(in)

    l.split(":") match {
      case p: Array[String] if p.size == 2 => node.name = p(0); node.chrName = p(1)
      case _ => throw new IllegalArgumentException("Expected name:chName found " + l)
    }

    val count = WritableUtils.readVInt(in)
    node.offset = WritableUtils.readVInt(in)

    for (i <- 1 to count if i < count) {
      val nextOffset = WritableUtils.readVInt(in)
      node.add(new Node(node.name, node.chrName, nextOffset))
    }

    this.reset(node)
  }

  private def reset(node: Node) {
    this.name = node.name
    this.chrName = node.chrName
    this.offset = node.offset
    this.firstSib = null
    this.lastSib = null

    var sib = node.firstSib
    while (sib != null) {
      this.add(sib)
      sib = sib.firstSib
    }
  }

  def iterator: Iterator[Node] = NodeIterator(this)

  case class NodeIterator(node: Node) extends Iterator[Node] {
    var nn: Node = node

    def hasNext: Boolean = nn != null

    def next(): Node = {
      val next = nn.copy
      nn = nn.firstSib
      next
    }
  }

  override def equals(o: Any): Boolean = o match {
    case that: Node => (this.name + this.chrName + this.offset).toLowerCase.equals((that.name + that.chrName + that.offset).toLowerCase)
    case _ => false
  }

  def compare(that: Node): Int = {
    this.name.toLowerCase.compare(that.name.toLowerCase) match {
      case n: Int if n == 0 => this.chrName.toLowerCase.compare(that.chrName.toLowerCase) match {
        case n: Int if n == 0 => this.offset.compare(that.offset)
        case n: Int => n
      }
      case n: Int => n
    }
  }
}

protected[ver2] class GenomeIngestionMapper extends Mapper[LongWritable, Text, Text, Node] with HImplicits {
  val logger: Logger = LoggerFactory.getLogger(getClass)

  var started: Boolean = false
  var stub: List[Char] = List()
  var winSize: Int = _
  var window: Window = _

  protected override def map(lnNumber: LongWritable, line: Text, context: Mapper[LongWritable, Text, Text, Node]#Context): Unit = {
    val flName = context.getInputSplit match {
      case fs: FileSplit => fs.getPath.getName
      case _ => ""
    }

    //Initialize
    if (!started) {
      winSize = context.getConfiguration.get(WINDOW_SIZE) match {
        case s: String => Integer.parseInt(s)
        case _ => throw new IllegalStateException("Window size not set")
      }
      logger.info("window size {}", winSize)

      window = Window(winSize)

      started = true
    }

    //Ignore the non genome related line
    if (line.startsWith(">")) {
      logger.info("skipping {}", line)
      return
    }

    val flOffset = context.getInputSplit.asInstanceOf[FileSplit].getStart
    logger.trace("file Offset {}", flOffset)

    val effLine: String = stub.isEmpty match {
      case false => stub.mkString + line.toUpperCase
      case _ => line.toUpperCase
    }

    //set the stub to used for next iteration
    stub = line.toCharArray.takeRight(winSize - 1).toList

    effLine foreach (c =>
      window.slide(c) match {
        case some: Some[Node] =>
          val node = some.get
          node.chrName = flName
          context.write(node.name + ":" + flName, node)
        case None =>
      }
      )
  }
}

protected[ver2] class GenomeIngestionReducer extends Reducer[Text, Node, Text, Node] with HImplicits {
  protected override def reduce(key: Text, value: java.lang.Iterable[Node], context: Reducer[Text, Node, Text, Node]#Context): Unit = {
    val itr = value.iterator()
    val res = itr.next.deepCopy

    while (itr.hasNext) {
      val next = itr.next().deepCopy
      res.merge(next)
    }
    context.write(key, res)
  }
}