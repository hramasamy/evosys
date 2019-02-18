#ifndef RECOMBINATION_H
#define RECOMBINATION_H

/*! \file Sequence/Recombination.hpp
  @brief namespace Sequence::Recombination
*/

/*! \namespace Sequence::Recombination
  \ingroup popgenanalysis
  This namespace exists primarily so that the file Poly.cc (which defines
  Sequence::Poly) does not get too large.  The routines defined in this namespace
  all have to do with properties of the association between sites.  Current methods implemented
  are:\n
  1.) Recombination::HudsonsC, which calculates Hudson's C, aka \f$\rho_{87}\f$\n
  \n
  2.) Recombination::Disequilibrium, which calculated several measures of LD for all pairs of sites,
  and implements a frequency filter to remove low-frequency variants if desired.
 
  @short Methods dealing with recombination
*/
#include <vector>

namespace Sequence
{
  class PolyTable;
  namespace Recombination
  {
    double HudsonsC (const Sequence::PolyTable * data, bool haveOutgroup,
		     unsigned outgroup);
    std::vector < std::vector<double> > Disequilibrium (const Sequence::PolyTable * data,
							bool haveOutgroup=false,
							unsigned outgroup=0,
							unsigned mincount = 1);
  }
}
#endif
