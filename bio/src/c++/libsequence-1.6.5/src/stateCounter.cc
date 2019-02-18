#include <Sequence/stateCounter.hpp>
#include <cctype>

namespace Sequence
  {
  /*! \var unsigned Sequence::stateCounter::a
    the number of times the character 'a' or 'A' has been counted
  */
  /*! \var unsigned Sequence::stateCounter::g
    the number of times the character 'g' or 'G' has been counted
  */
  /*! \var unsigned Sequence::stateCounter::c
    the number of times the character 'c' or 'C' has been counted
  */
  /*! \var unsigned Sequence::stateCounter::t
    the number of times the character 't' or 'T' has been counted
  */
  /*! \var unsigned Sequence::stateCounter::zero
    the number of times the character '0' has been counted
  */
  /*! \var unsigned Sequence::stateCounter::one
    the number of times the character '1' has been counted
  */
  /*! \var unsigned Sequence::stateCounter::gap
    the number of times the gap character  has been counted
  */
  /*! \var unsigned Sequence::stateCounter::n
    the number of times the missing data character 'N'  has been counted
  */

  /*! \var bool Sequence::stateCounter::ndna
    true if a non-DNA character has been encountered, false otherwise
  */
  stateCounter::stateCounter(const char &gapchar):_gap(gapchar),a(0),g(0),c(0),
      t(0),zero(0),one(0),gap(0),n(0),ndna(false)
      /*!
        \param gapchar charcter representing a gap in an alignment
      */
  {}

  void stateCounter::operator()(const char &ch) const
  /*!
    \param ch the character in position i,j in an alignment
  */
    {
      if (ch == _gap)
        {
          ++gap;
        }
      else
        {
          char _ch = toupper(ch);
          switch(_ch)
            {
            case 'A':
              ++a;
              break;
            case 'G':
              ++g;
              break;
            case 'C':
              ++c;
              break;
            case 'T':
              ++t;
              break;
            case '0':
              ++zero;
              break;
            case '1':
              ++one;
              break;
            case 'N':
              ++n;
              break;
            default:
              ndna = true;
              break;
            }
        }
    }

  unsigned stateCounter::nStates(void) const
  /*!
    \return the number of states counted, excluding gaps
    and missing data
  */
    {
      unsigned nstates = (a > 0) ? 1 : 0;
      nstates += (g > 0) ? 1 : 0;
      nstates += (c > 0) ? 1 : 0;
      nstates += (t > 0) ? 1 : 0;
      nstates += (zero > 0) ? 1 : 0;
      nstates += (one > 0) ? 1 : 0;
      return nstates;
    }
}
