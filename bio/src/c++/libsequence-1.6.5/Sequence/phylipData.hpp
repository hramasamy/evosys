/*! \file phylipData.hpp
  \short Sequence::phylipData -- read in phylip alignments
*/
#ifndef __PHYLIPDATA_HPP__
#define __PHYLIPDATA_HPP__

#include <Sequence/AlignStream.hpp>
#include <utility>
#include <string>
namespace Sequence
{
  template < typename T >
  class phylipData: public AlignStream < T >
		   /*!
		     Input of phylip-format alignments
		    */
  {
  private:
    mutable unsigned _namelen;
  public:
    phylipData (const unsigned namelen = 0)
      :AlignStream<T>(),
       _namelen(namelen)
    {}
    phylipData(const std::vector<T> & _data,
	       const unsigned namelen=0)
      :AlignStream<T>(_data),
       _namelen(namelen)
    {
    }
    phylipData (const AlignStream<T> &a) :
      AlignStream<T>(a),_namelen(0)
    {
    }
    ~phylipData(void)
    {}
    std::istream & read (std::istream & s);
    std::ostream & print (std::ostream & s) const;
    phylipData<T> & operator=( const AlignStream<T> & rhs);
  };
}
#include <Sequence/bits/phylipData.tcc>
#endif
