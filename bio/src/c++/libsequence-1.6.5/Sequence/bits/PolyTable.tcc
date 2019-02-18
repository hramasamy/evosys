// Code for the -*- C++ -*- namespace Sequence::PolyTable template members
#ifndef __POLY_TABLE_TCC__
#define __POLY_TABLE_TCC__

#include <iterator>

namespace Sequence
{
  template<typename numeric_type,
	   typename string_type>
  bool PolyTable::assign( const numeric_type * _positions, 
			  const unsigned & _num_positions,
			  const string_type * _data,
			  const unsigned & _num_individuals )
  {
    //The numeric array must be convertible to double
    BOOST_STATIC_ASSERT( (boost::is_convertible<numeric_type,double>::value) );
    //The character type must be eithe char * or std::string
    BOOST_STATIC_ASSERT( (boost::is_same<string_type,char *>::value  ||
			  boost::is_same<string_type,std::string>::value ) );
  
    positions.resize(_num_positions);
    data.resize(_num_individuals);
    positions.assign(_positions,_positions+_num_positions);
    data.assign(_data,_data+_num_individuals);
    non_const_access = true;
    for(std::vector<std::string>::const_iterator itr = data.begin() ;
	itr < data.end() ; ++itr)
      {
	if (itr->length() != _num_positions)
	  {
	    positions.clear();
	    data.clear();
	    return false;
	  }
      }
    return true;
  }

//   template<typename iterator>
//   bool PolyTable::rear_insert( const iterator beg,
// 			       const iterator end )
//   /*!
//     Insert a range of string types (chr * or std::string)
//     at the end of a PolyTable object.
//    */
//   {
//     typedef typename std::iterator_traits<iterator>::value_type vtype;
//     BOOST_STATIC_ASSERT( (boost::is_same<vtype,char *>::value  ||
// 			  boost::is_same<vtype,std::string>::value ) );
//     data.insert(data.end(),beg,end);
//     return true;
//   }

}

#endif
