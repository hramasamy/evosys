#ifndef _STATE_COUNTER_H_
#define _STATE_COUNTER_H_
#include <functional>
/*! \file stateCounter.hpp
@brief declaration of Sequence::stateCounter, a class to keep track of nucleotide counts either at a site
in an alignment, or along a sequence
*/
/*!
  \class Sequence::stateCounter Sequence/stateCounter.hpp
  \ingroup functors
  \warning class data are public.  Use responsibly.
  @short keep track of state counts at a site in an alignment or along a sequence
*/
namespace Sequence
  {
  class stateCounter : public std::unary_function<char,void>
    {
    private:
      mutable char _gap;
    public:
      mutable unsigned a,g,c,t,zero,one,gap,n;
      mutable bool ndna;
      stateCounter(const char &gapchar = '-');
      void operator()(const char &ch) const;
      unsigned nStates(void) const;
    };
}
#endif
