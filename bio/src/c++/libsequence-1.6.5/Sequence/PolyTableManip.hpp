#ifndef __POLY_TABLE_MANIP_H__
#define __POLY_TABLE_MANIP_H__

/*! \file PolyTableManip.hpp
  @brief Declaration of Sequence::rotatePolyTable
*/
#include <Sequence/typedefs.hpp>

namespace Sequence
{
  class PolyTable;

  polySiteVector rotatePolyTable(const Sequence::PolyTable *data);
}
#endif
