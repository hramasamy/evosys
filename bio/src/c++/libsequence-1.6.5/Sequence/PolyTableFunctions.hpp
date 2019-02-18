#ifndef __POLYTABLE_FUNCTIONS_HPP__
#define __POLYTABLE_FUNCTIONS_HPP__
#include <Sequence/SeqExceptions.hpp>

/*! \file PolyTableFunctions.hpp
  There are lots of ways one may want to manipulate a Sequence::PolyTable.
  In the past, useful manipulation functions were added as member functions.
  That quickly got out of control, breaking binary compatibility left and 
  right between different library versions.  Declarations for such functions
  are now being added to this file.
  \short Operations on non-const Sequence::PolyTable objects
 */

namespace Sequence
{
  class PolyTable;
  bool containsCharacter(const PolyTable * t,
			 const char & ch);

  void fillIn(PolyTable * t,
	      const unsigned & refseq = 0,
	      const char & identical = '.');

  void addIdentityChar(PolyTable *t,
		       const unsigned & refseq = 0,
		       const char & identical = '.');

  void RemoveGaps(PolyTable *t,
		  const char & gapchar = '-') throw (SeqException);

  void RemoveInvariantColumns(PolyTable *t,
			      const bool & skipOutgroup = false,
			      const unsigned & outgroup = 0) throw (SeqException);

  bool PolyTableValid(const PolyTable * t);
}
#endif
