#include <Sequence/Fasta.hpp>
#include <stdexcept>
#include <iostream>

namespace Sequence
{
  /*! \example baseComp.cc */
  Fasta::Fasta (const std::string &name, const std::string &seq):
    Seq(name.c_str(),seq.c_str())
	       /*!
		 constructor for const std::string
	       */
  {}
  Fasta::Fasta (const char *name,const char *seq):Seq(name,seq)
	       /*!
		 constructor for const char *
	       */
  {}
  
  Fasta::Fasta (const Seq & seq) : Seq(seq) 
    /*! copy constructor */
  {}

  std::istream & Fasta::read (std::istream & stream) throw (Sequence::badFormat,std::exception)
  {
    char ch;
    bool seqflag;

    if (!(stream >> ch))
      {
        stream.setstate (std::ios::badbit);
        return (stream);
      }
    else
      stream.putback (ch);

    stream >> ch;

    if (ch != '>')
      {
        throw badFormat("Fasta.cc: error, file not in FASTA format");
      }
    first.clear();
    while (1)
      {
        stream.get (ch);
        if (ch == '\n')
          break;
	first += ch;
      }
    seqflag = 1;
    second.clear();
    second.reserve(1000);
    while (seqflag)
      {
        stream >> ch;
        if (ch == '>')
          {
            stream.putback (ch);
            seqflag = 0;
          }
        else if (stream.eof ())
          seqflag = 0;
        else
          {
	    second += ch;
          }
      }
    return (stream);
  }

  std::ostream & Fasta::print (std::ostream & stream) const
  {
    stream << '>'
	   << first
	   << '\n'
	   << second;
    return stream;
  }
}
