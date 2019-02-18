package com.biocis.utils;


import java.io.*;

public class CopyBytes {
  public static void main(String[] args) throws IOException {
    File inputFile = new File("l.gif");
    File outputFile = new File("l1.gif");

    FileInputStream in = new FileInputStream(inputFile);
    FileOutputStream out = new FileOutputStream(outputFile);
    int c;

    while ((c = in.read()) != -1)
      out.write(c);

    in.close();
    out.close();
  }
}
