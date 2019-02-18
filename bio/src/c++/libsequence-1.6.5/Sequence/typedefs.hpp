/*! \file typedefs.hpp
  Typedefs used in the library are defined here.
  Wherever possible, types from namespace std
  are given forward declarations. 
  @brief typedefs used by libsequence
*/
#ifndef __SEQUENCE_TYPEDEFS_HPP
#define __SEQUENCE_TYPEDEFS_HPP
#include <vector>

//forward declarations from namespace std
namespace std
{
  /*! forward declaration */
  template<typename A,typename B> struct pair;
  /*! forward declaration */
  template<typename _char, typename _char_traits, typename allocator>
  struct basic_string;
  /*! forward declaration */
  typedef struct basic_string<char, char_traits<char>, 
			      allocator<char> > string;
}
namespace Sequence
{
  /*! 
    A CodonUsageTable is a vector of pairs.  In each pair,
    the first element is the codon, and the second element
    is an integer counting the number of occurrences of 
    the codon
  */
  typedef std::vector< std::pair<std::string,int> > CodonUsageTable;

  /*!
    For polymorphism data, a Site can be represented as
    a position (a double) and the characters at 
    that positions (a std::string)
  */
  typedef std::pair< double, std::string > polymorphicSite;

  /*!
    A polymorphism data set can be represented as
    a vector containing a sequence of polymorphicSite
  */
  typedef std::vector< polymorphicSite > polySiteVector;
}
#endif
