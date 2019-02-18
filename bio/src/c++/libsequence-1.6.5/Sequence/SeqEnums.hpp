#ifndef SEQENUMS_H
#define SEQENUMS_H

/*! \file SeqEnums.hpp
  Defines a handfull of enumeration types useful
  for sequence data.
  @brief Definition of enumeration types
*/

namespace Sequence
  {
    /*! \enum Sequence::Nucleotides
      An enum type for nucleotide data.  Comes in handy when you need
      to iterate over all possible bases, etc.
      enum values are:A=0,T,G,C,N,GAP
    */
    enum Nucleotides {A,T,G,C,N,GAP};
    /*! \enum Sequence::GeneticCodes
      Only UNIVERSAL (=0)  is currently supported.
      The order of the genetic codes is that of NCBI's code tables, available at 
      http://www.ncbi.nlm.nih.gov/htbin-post/Taxonomy/wprintgc?mode=c#SG2\n
    */
    enum GeneticCodes{UNIVERSAL};
    /*! \enum Sequence::Mutations
      Values: Unknown=0,Ts, and Tv.\n
      Unknown means unknown, Ts means transition, Tv means transversion
    */
    enum Mutations{Unknown,Ts,Tv};
}
#endif
