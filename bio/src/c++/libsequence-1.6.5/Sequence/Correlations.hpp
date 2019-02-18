#ifndef __CORRELATIONS_H__
#define __CORRELATIONS_H__

/*! \file Correlations.hpp
  
  Generic algorithms for calculating and testing the significance
  of correlation coefficients
*/
/*! \example correlations.cc */
#include <iterator>
#include <functional>
#include <Sequence/ensureFloating.hpp>
#include <Sequence/Portability/random_shuffle.hpp>
namespace Sequence
{
  struct ProductMoment
  /*!
    Function object to calculate Pearson's product-moment correlation
    coefficient
    @ingroup stats
    @short Pearson's product-moment correlation
  */
  {
    template<typename iter1, typename iter2>
    typename ensureFloating<typename std::iterator_traits<iter1>::value_type,
			    typename std::iterator_traits<iter2>::value_type>::type
			    operator()(iter1 beg_x,iter1 end_x, iter2 beg_y) const;
  };

  struct SpearmansRank
  /*!
    Function object to calculate Spearman's rank correlation
    @ingroup stats
    @short Spearman's rank correlation
  */
  {
    template<typename iter1,typename iter2>
    typename ensureFloating<typename std::iterator_traits<iter1>::value_type,
			    typename std::iterator_traits<iter2>::value_type>::type
			    operator()(iter1 beg_x,iter1 end_x, iter2 beg_y) const;
  };

  template<typename iter1, typename iter2,  
	   typename correlation_type,
	   typename comparison_function,
	   typename UniformIntGenerator>
  typename ensureFloating<typename std::iterator_traits<iter1>::value_type,
			  typename std::iterator_traits<iter2>::value_type>::type
  PermuteCorrelation(iter1 beg_x, iter1 end_x, iter2 beg_y,
		     const correlation_type &  c,
		     const comparison_function & comp,
		     UniformIntGenerator & rand,
		     const unsigned & NPERM=10000);

  template<typename iter1, typename iter2,  
	   typename correlation_type,
	   typename comparison_function,
	   typename UniformIntGenerator>
  typename ensureFloating<typename std::iterator_traits<iter1>::value_type,
			  typename std::iterator_traits<iter2>::value_type>::type
  PermuteCorrelation(iter1 beg_x, iter1 end_x, iter2 beg_y,
		     const correlation_type &  c,
		     const comparison_function & comp,
		     const UniformIntGenerator & rand,
		     const unsigned & NPERM=10000);

}//namespace Sequence
#include <Sequence/bits/Correlations.tcc>
#endif
