//  -*- C++ -*- 
#ifndef __COUNTING_OPERATORS_TCC__
#define __COUNTING_OPERATORS_TCC__
#include <Sequence/CountingOperators.hpp>

namespace Sequence
{
  template<typename key, typename value>
  std::vector<std::pair<key,value> >
  operator+(const std::vector<std::pair<key,value> > &lhs,
	    const std::vector<std::pair<key,value> > &rhs)
  {
    typedef std::vector<std::pair<key,value> > rt; //return type
    rt rv(lhs);
    for(typename rt::const_iterator itr = rhs.begin();
	itr < rhs.end();
	++itr)
      {
	typename rt::iterator i = std::find_if(rv.begin(),
					       rv.end(),
					       std::bind2nd(first_is_equal<key,value>(),*itr));
	if (i != rv.end())
	  {
	    i->second += itr->second;
	  }
	else
	  {
	    rv.push_back(*itr);
  }
      }
    return rv;
  }

  template<typename key, typename value>
  std::vector<std::pair<key,value> >
  operator+=( std::vector<std::pair<key,value> > &lhs,
	      const std::vector<std::pair<key,value> > &rhs)
  {
    return lhs=lhs+rhs;
  }

  template< typename key, typename value,typename comparison>
  std::map<key,value,comparison> operator+(const std::map<key,value,comparison> &lhs,
			  const std::map<key,value,comparison> &rhs)
  {
    typedef std::map<key,value,comparison> rt;
    rt rv(lhs);
    for(typename rt::const_iterator itr = rhs.begin() ;
	itr != rhs.end() ;
	++itr)
      {
	typename rt::iterator i = rv.find(itr->first);
	if ( i != rv.end() )
	  {
	    i->second += itr->second;
	  }
	else
	  {
	    rv[itr->first] = itr->second;
	  }
      }
    return rv;
  }

  template< typename key, typename value,typename comparison>
  std::map<key,value,comparison> operator+=( std::map<key,value,comparison> &lhs,
			    const std::map<key,value,comparison> &rhs)
  {
    return lhs=lhs+rhs;
  }
}
#endif
