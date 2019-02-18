#include <Sequence/SeqConstants.hpp>
#if ( defined(HAVE_CLIMITS) && (!(defined(HAVE_LIMITS))) )
#include <climits>
#else
#include <limits>
#endif

namespace Sequence
{
#if ( defined(HAVE_CLIMITS) && (!(defined(HAVE_LIMITS))) )
  /*! \var const unsigned SEQMAXUNSIGNED
    The maximum value of an unsinged integer.
  */
  const unsigned SEQMAXUNSIGNED = UINT_MAX;
  /*! \var const unsigned SEQMAXDOUBLE
    The maximum value of an double
  */
  const double SEQMAXDOUBLE = DOUBLE_MAX;
#else
  /*! \var const unsigned SEQMAXUNSIGNED
    The maximum value of an unsinged integer.
  */
  const unsigned SEQMAXUNSIGNED = std::numeric_limits<unsigned>::max();
  /*! \var const unsigned SEQMAXDOUBLE
    The maximum value of an double
  */
  const double SEQMAXDOUBLE = std::numeric_limits<double>::max();
#endif
}
