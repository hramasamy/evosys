#ifndef __ENSURE_FLOATING_HPP__
#define __ENSURE_FLOATING_HPP__

#include <Sequence/preferFloatingTypes.hpp>
#include <boost/mpl/identity.hpp>

namespace Sequence
{
  template <typename T, typename T2, typename floatingType = double>
  struct ensureFloating
  /*! \struct ensureFloating Sequence/ensureFloating.hpp
    \ingroup metaprogramming
    A metaprogram to ensure that a floating type is chosen.  During 
    compilation, if either T or T2 are floating types, the floating
    type is selected according to the rules of Sequence::preferFloatingTypes.
    If neither T nor T2 is a floating type, floatingType is selected.
    Compile-time assertions check that T and T2 are arithmetic types,
    and the floatingType is indeed a floating type.
   */
  {
    BOOST_STATIC_ASSERT( (boost::is_arithmetic<T>::value) &&
			 (boost::is_arithmetic<T2>::value) );
    BOOST_STATIC_ASSERT( (boost::is_float<floatingType>::value) );
    /*!
      resolves to the selected type
    */
    typedef typename boost::mpl::if_<
      typename boost::mpl::not_<
      typename boost::is_float<typename preferFloatingTypes<T,T2>::type>
    >,
      typename boost::mpl::identity<floatingType>::type,
      typename preferFloatingTypes<T,T2>::type >::type type;
  };
}
#endif
