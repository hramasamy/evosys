#ifndef __SEQ_PROPERTIES_HPP__
#define __SEQ_PROPERTIES_HPP__
/*! \file SeqProperties.hpp
  \brief Function objects to ask simple questions about sequence composition.
  Declares Sequence::ambiguousNucleotide,Sequence::invalidPolyChar.
 */
#include <functional>
#include <cctype>
#include <Sequence/Comparisons.hpp>

namespace Sequence
{
  struct ambiguousNucleotide : public std::unary_function<char,bool>
			       /*! \struct ambiguousNucleotide Sequence/SeqProperties.hpp
				*/
  {
    inline bool operator()(const char & c) const
    /*!
      \return true if c is not A,G,C, or T, false otherwise
      \note Case-insensitive
    */
    {
      const char ch = std::toupper(c);
      return (ch != 'A' &&
	      ch != 'G' &&
	      ch != 'T' &&
	      ch != 'C' );
    }
  };

  struct invalidPolyChar : public std::unary_function<char,bool>
			   /*! \struct invalidPolyChar Sequence/SeqProperties.hpp
			     This functor can be used to determine
			     if a range contains characters that
			     the SNP analysis routines in this
			     library cannot handle gracefully
			    */
  {
    inline bool operator()(const char & nucleotide) const
    /*!
      \return true if c is not in the set {A,G,C,T,N,-,.}, false otherwise. The period
      (.) can be used as an "identical to the 1st seq in a file" character, so 
      should be considered valid
      \note Case-insensitive
    */
    {
      const char ch = std::toupper(nucleotide);
      return ( ch != 'A' &&
	       ch != 'G' &&
	       ch != 'C' &&
	       ch != 'T' &&
	       ch != 'N' &&
	       ch != '-' && 
	       ch != '.' );
    }
  };

}

#endif
