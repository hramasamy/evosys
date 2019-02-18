#ifndef __SEQ_REGEXES_H__
#define __SEQ_REGEXES_H__

#include <boost/regex.hpp>
/*! \file SeqRegexes.hpp
  This file declares various functions using regular expressions.
  These functions are in a separate header because they have include
  directives that depend on other libraries, notably BOOST (http://www.boost.org)
  @short various useful rexex-based functions for dealing with data.
  Declares Sequence::basic_dna_alphabet,Sequence::full_dna_alphabet,
  Sequence::pep_alphabet, and the function Sequence::validSeq.
*/
namespace Sequence
{
  /*!
    A regex for the complement of the minimal DNA alphabet
  */
  const char *basic_dna_alphabet = "[^AGTCN\\-]";

  /*!
    A regex for the complement of a complete DNA alphabet
  */
  const char *full_dna_alphabet =  "[^AGCTNXMRWSKVHDB\\-]";

  /*!
    A regex for the complement of an amino acid alphabet 
  */
  const char *pep_alphabet = "[^ARNDBCQEZGHILKMFPSTWYV\\-]";


  /*! \example valid_dna.cc */
  template<typename Iter>
  bool validSeq(Iter beg, Iter end, const char *_pattern = Sequence::basic_dna_alphabet, const bool icase = true)
    /*!
      \param beg an iterator to the beginning of a range
      \param end an iterator to the end of a range
      \param _pattern the (complement of the) alphabet as a regular expression. 
      \param icase defaults to case insensitive matching. Pass "false" to make
      matching case sensitive
      The character set is complemented because
      we test for not in the alphabet
      \return true if \a beg and \a end define a range of valid characters.  The range is valid
      if and only if all characters in the range are present in the pattern (i.e. are not
      part of the set of characters that complement the pattern)
      \note requires the  boost_regex library to compile (see http://www.boost.org)
      \ingroup boost
    */
  {
    boost::regex in_alphabet(_pattern,icase);
    boost::match_results<Iter> match;
    return !(boost::regex_search(beg, end, match, in_alphabet, boost::match_default));
  }
}

#endif
