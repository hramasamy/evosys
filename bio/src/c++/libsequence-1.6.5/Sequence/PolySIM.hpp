#ifndef _POLYSIM_H_
#define _POLYSIM_H_
/*! \file PolySIM.hpp
  @brief declaration of Sequence::PolySIM, a class to analyze coalescent simulation data
*/
/*!
  \class Sequence::PolySIM Sequence/PolySIM.hpp
  \ingroup popgenanalysis
  This class inherits from Sequence::PolySNP.  It is a collection
  of analysis routines for coalescent simulation data, and is 
  constructed from a const Sequence::SimData *.  The main difference is
  that outgroup information is not required, as the 0,1 coding of a
  SimData object (usually) reflects ancestral and derived.
  @short Analysis of coalescent simulation data
*/
#include <Sequence/PolySNP.hpp>

namespace Sequence
  {
  class SimData;
  class PolySIM : public PolySNP
    {
    private:
      //functions for Hudson's Partition Test
      int poly (int *subslist,  int ss,
                int subsize, int subss, int *seq);
      int nextsample (int *subslist, int subsize, int nsam, int seq);
    protected:
      void WallStats(void);
    public:
      explicit PolySIM (const Sequence::SimData * data);
      virtual ~ PolySIM(void);
      //estimators of 4Nu
      double ThetaPi (void);
      double ThetaW (void);
      double ThetaH (void);
      double ThetaL (void);

      //calculate various numbers related to polymorphism
      unsigned NumMutations (void);
      unsigned NumSingletons (void);
      unsigned NumExternalMutations (void);
      //summary statistics of the site frequency spectrum
      double TajimasD (void);
      double Hprime (bool likeThorntonAndolfatto = false);
      double Dnominator (void);
      double FuLiD (void);
      double FuLiF (void);
      double FuLiDStar (void);
      double FuLiFStar (void);
      double WallsB(void);
      unsigned WallsBprime(void);
      double WallsQ(void);
      //Hudson's Haplotype Partition Test
      int HudsonsHaplotypeTest (int subsize, int subss);

      //recombination
      unsigned Minrec (void);
    };
}
#endif
