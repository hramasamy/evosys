/*! \file Fasta.hpp
  @brief Declaration of Sequence::Fasta streams
*/

/*!
  \class Sequence::Fasta Sequence/Fasta.hpp
  \ingroup seqio
  Publicly derived from Sequence::Seq, this class defines
  how to read and print sequences in FASTA format, which looks like:\n
  >sequence name 1\n
  ATGATGATCAGATAGACATAGCAGATACATGT\n
  >sequence name 2\n
  ATGTTGGTTTTTTTTTAGAGATGTTTATAGGT\n
  ETC... 
 
  @short FASTA sequence stream
*/

#ifndef FASTA_H
#define FASTA_H

#include <Sequence/Seq.hpp>

namespace Sequence
  {
  class Fasta : public Seq
    {
    private:
    public:
      Fasta():Seq()/*!Generic constructor*/ {}
      Fasta (const std::string &name, const std::string &seq);
      Fasta(const char *name, const char *seq);
      Fasta (const Seq & s);
      ~Fasta()/*! placeholder for vtable */ {}
      /*!
	\exception Sequence::SeqException if memory can't be allocated. 
	(This is because the data are temporarily read into char *, 
	because that was found to be faster).
	\exception Sequence::badFormat if the input stream is not
	in FASTA format
      */
      std::istream&  read(std::istream &s) 
	throw (Sequence::badFormat,std::exception);
      /*!
	\param stream a std::ostream
	write the sequence in FASTA format to \a stream
      */
      std::ostream& print(std::ostream& s) const;
    };
}
#endif
