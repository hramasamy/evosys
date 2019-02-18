#include <Sequence/PolyTable.hpp>
#include <Sequence/PolyTableManip.hpp>

namespace Sequence
{
  polySiteVector rotatePolyTable(const Sequence::PolyTable *data)
  /*!
    Rotate a polymorphism table
    into a vector of pairs, where the
    pairs are of type std::pair<double, string>,
    representing the site position and the characters
    at that site
    \param data a pointer to a Sequence::PolyTable
    \ingroup polytables 
  */
  {
    polySiteVector L;
    for (unsigned i = 0 ; i < data->numsites() ; ++i)
      {
        std::string s;
        for(unsigned j = 0 ; j < data->size() ; ++j)
          {
            s += (*data)[j][i];
          }
        L.push_back( polymorphicSite(data->position(i), s) );
      }
    return L;
  }
}
