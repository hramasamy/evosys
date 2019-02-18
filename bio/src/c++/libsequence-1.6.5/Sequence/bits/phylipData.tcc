// Code for the -*- C++ -*- namespace Sequence::phylipData<T>

#include <Sequence/phylipData.hpp>
#include <string>
#include <iterator>
#include <utility>
#include <algorithm>
#include <cctype>

namespace Sequence
{
  template < typename T >
  std::istream & phylipData < T >::read (std::istream & s)
  {
    unsigned nsam,nsites;
    s >> nsam >> nsites;
    std::vector<T> _data(nsam);
    unsigned site;
    char ch;
    std::string name,seq(nsites,'0');

    for(unsigned i = 0 ; i < nsam ; ++i)
      {
	if (_namelen > 0)
	  {
	    s >> name;
	  }
	else
	  {
	    name.clear();
	    for(unsigned nchar=0;nchar<_namelen;++nchar)
	      {
		s>>ch;
		name+=ch;
	      }
	  }
	site = 0;
	while(site<nsites)
	  {
	    s >> ch;
	    if (! std::isspace(ch) )
	      {
		seq[site++]=ch;
	      }
	  }
	_data[i] = T(name,seq);
      }
    assign(_data.begin(),_data.end());
    return s;
  }

  template < typename T >
  std::ostream & phylipData < T >::print (std::ostream & s) const
  {
    unsigned nsam = this->size();
    s << nsam 
      << '\t' 
      << (*this)[0].length() 
      << '\n';
    typename phylipData<T>::const_iterator beg = this->begin(),
      end = this->end();
    while(beg<end-1)
      {
	if (_namelen > 0)
	  {
	    s<< beg->first
	     << '\t'
	     << beg->second
	     << '\n';
	  }
	else
	  {
	    if (beg->first.length() == _namelen)
	      {
		std::string::const_iterator b = beg->first.begin();
		std::copy(b,b+_namelen,
			  std::ostream_iterator<char>(s,""));
	      }
	    else
	      {
		//need to pad the name with spaces
		unsigned len = _namelen - beg->first.length();
		std::string newName(beg->first + 
				    std::string(len,' '));
		std::string::const_iterator b = newName.begin();
		std::copy(b,b+_namelen,
			  std::ostream_iterator<char>(s,""));
	      }
	    s << beg->second
	      << '\n';
	  }
	++beg;
      }
    //don't print a newline for the last record
    if (_namelen>0)
      {
	s<< beg->first
	 << '\t'
	 << beg->second;
      }
    else
      {
	if (beg->first.length() == _namelen)
	  {
	    std::string::const_iterator b = beg->first.begin();
	    std::copy(b,b+_namelen,
		      std::ostream_iterator<char>(s,""));
	  }
	else
	  {
	    //need to pad the name with spaces
	    unsigned len = _namelen - beg->first.length();
	    std::string newName(beg->first + 
				std::string(len,' '));
	    s << newName;
	  }
	s << beg->second;
      }
    return s;
  }

 
template<typename T>
phylipData<T> & phylipData<T>::operator=( const AlignStream<T> & rhs)
/*!
  An "assignment operator" member function.
  If a phylipData object was constructed with a value of 
  0 for the sequence name length, this function
  will set _namelen to the max sequence name contained in \a rhs.
  If the object was constructed with a value k > 0, the 
  sequence name length will remain unchanged.
*/
{
  this->assign(rhs.begin(),rhs.end());
  bool change_namelen=false;
  if (_namelen == 0)
    change_namelen=true;
  unsigned l=0;
  for(typename phylipData<T>::const_iterator i = this->begin() ;
      i != this->end();
      ++i)
    {
      l = i->first.length();
      if (change_namelen)
	_namelen = (l > _namelen ) ? l : _namelen;
    }
  return *this;
}

}
