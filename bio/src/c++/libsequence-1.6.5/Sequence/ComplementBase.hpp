#ifndef __COMPLEMENT_BASE_H__
#define __COMPLEMENT_BASE_H__
#include <functional>

/*! \file ComplementBase.hpp
  @brief Delcaration of Sequence::ComplementBase, a function object to return the complement of a DNA nucleotide
*/
/*! 
  \struct Sequence::ComplementBase Sequence/ComplementBase.hpp
  \ingroup functors
  a functor to complement a sequence\n
  example use:
  \code
  //reverse and complement a std::string
  #include <string>
  #include <algorithm>
  #include <Sequence/SeqFunctors.hpp>
  
  int main ()
  {
  std::string seq;
  //fill seq with DNA characters
  std::reverse(seq.begin(),seq.end());
  std::for_each(seq.begin(),seq.end(),Sequence::ComplementBase());
  }
  \endcode
*/
namespace Sequence
  {
  struct ComplementBase : public std::unary_function<char,void>
  {
    void operator()(char &ch) const;
  };
}
#endif
