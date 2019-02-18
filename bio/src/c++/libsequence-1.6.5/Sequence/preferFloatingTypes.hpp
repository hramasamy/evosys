#ifndef __PREFER_FLOATING_TYPES_HPP__
#define __PREFER_FLOATING_TYPES_HPP__

#include <boost/mpl/if.hpp>
#include <boost/mpl/and.hpp>
#include <boost/mpl/not.hpp>
#include <boost/type_traits.hpp>
#include <boost/static_assert.hpp>

/*! \defgroup metaprogramming Metaprogramming
  @short A collection of metaprograms implemented using the boost::mpl (http://www.boost.org)
*/
namespace Sequence
{
  template< typename T, typename T2 >
  struct preferFloatingTypes
  /*! \struct preferFloatingTypes Sequence/preferFloatingTypes.hpp
    \ingroup metaprogramming
    This class contains a public member "type", which is a typedef.
    What type is a typedef for is determined as follows:
    When instantiated with two types, \a T and \a T2,
    if \a T or \a T2 is double, type is double. Else, if either \a T
    or \a T2 is float, and the other type is non-floating,
    type is float.  If both \a T and \a T2 are non-floating, type is \a T.
    For example:
    \code
    std::cout << typeid(Sequence::preferFloatingTypes<float,double>::type).name ()
    << std::endl
    << typeid(Sequence::preferFloatingTypes<double,float>::type).name()
    << std::endl
    << typeid(Sequence::preferFloatingTypes<double,int>::type).name()
    << std::endl
    << tpyeid(Sequence::preferFloatingTypes<float,int>::type).name()
    << std::endl
    << typeid(Sequence::preferFloatingTypes<int,unsigned>::type).name()
    << std::endl;
    \endcode
    \note Type selection is done using the BOOST metaprogramming library
    (mpl).  See http://www.boost.org for details and mpl documentation.
    Also, if \a T or \a T2 are not arithmetic types, compilation will fail,
    and this requirement is enforced via 
    BOOST_STATIC_ASSERT( (boost::is_arithmetic<T>::value)
    && (boost::is_arithmetic<T2>::value) ).
   */
  {
    //compile-time assertion that T and T2 are arithmetic types
    BOOST_STATIC_ASSERT( (boost::is_arithmetic<T>::value) &&
			 (boost::is_arithmetic<T2>::value) );
    /*!
      resolves to the selected type
    */
    typedef typename boost::mpl::if_< 
      typename boost::mpl::and_< 
      typename boost::is_float<T2>, 
      typename boost::mpl::not_< 
      boost::is_same<T,double> > >,
      T2,
      T >::type type;
  };
} //namespace Sequence

#endif
