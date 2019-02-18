#ifndef __SEQUENCE_SNN_HPP__
#define __SEQUENCE_SNN_HPP__

#include <Sequence/Comparisons.hpp>
#include <Sequence/PolyTable.hpp>
#include <vector>
#include <utility>
#include <cassert>

namespace Sequence
{
  double Snn_statistic( const unsigned individuals[],
			const std::vector< std::vector<double> > & dkj,
			const unsigned config[],
			const size_t & npop,
			const unsigned & nsam );

  template< typename uniform_int_generator >
  std::pair<double,double>
  Snn_test(const PolyTable & snpTable,
	   const unsigned config[],
	   const size_t & npop,
	   uniform_int_generator & uni_int,
	   const unsigned & nperms = 10000);

  template< typename uniform_int_generator >
  std::vector< std::vector<double> >
  Snn_test_pairwise(const PolyTable & snpTable,
		    const unsigned config[],
		    const size_t & npop,
		    uniform_int_generator & uni_int,
		    const unsigned & nperms = 10000);

  template< typename uniform_int_generator >
  std::pair<double,double>
  Snn_test(const PolyTable & snpTable,
	   const unsigned config[],
	   const size_t & npop,
	   const uniform_int_generator & uni_int,
	   const unsigned & nperms = 10000);

  template< typename uniform_int_generator >
  std::vector< std::vector<double> >
  Snn_test_pairwise(const PolyTable & snpTable,
		    const unsigned config[],
		    const size_t & npop,
		    const uniform_int_generator & uni_int,
		    const unsigned & nperms = 10000);
}
#endif
#include <Sequence/bits/Snn.tcc>
