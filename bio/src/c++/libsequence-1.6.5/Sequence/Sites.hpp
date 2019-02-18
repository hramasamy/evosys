#ifndef SITES_H
#define SITES_H
/*! \file Sites.hpp
  @brief class Sequence::Sites calculates the length of a pairwise alignment between coding
  sequences in terms of site degeneracy.  Used by Sequence::Comeron95
*/

/*!
  \class Sequence::Sites Sequence/Sites.hpp
  \ingroup kaks
  This class calculates the length of each sequence in a pairwise
  comparison in terms of site degeneracy.  The 4 values computed
  are:\n
  1.) L0 -- the number of non-degenerate sites in the comparison.\n
  2.) L2S -- the number of two-fold degenerate sites in the comparison
  for which transitions are synonymous.\n
  3.) L2V -- the number of two-fold degenerate sites in the comparison
  for which transversion are synonymous.\n
  4.) L4 -- the number of fourfold degenerate sites in the comparison.\n
  \n
  In order to count these numbers, one has to know how degenerate
  each codon is, which is why objects of this type must be constructed
  with objects of type Sequence::RedundancyCom95.
 
  @author Kevin Thornton
  @short Calculate length statistics for divergence calculations
*/

#include <string>

class Sequence::Seq;
class Sequence::RedundancyCom95;
namespace Sequence
  {

  class Sites
    {
    private:
      int validcds;
      int seqlen;
      int maxhits;
      int genetic_code;
      double _L0;
      double _L2S;
      double _L2V;
      double _L4;
      void siteinc (const RedundancyCom95 * sitesObj,
                    const std::string & codon1,const  std::string &codon2);
      void count_sites (const Sequence::Seq * sequence1,
                        const Sequence::Seq * sequence2,
                        const RedundancyCom95 * sitesObj);
    public:
      explicit Sites (const RedundancyCom95 * sitesObj, const Sequence::Seq * seq1,
                      const Sequence::Seq * seq2, const int max = 3,
                      const int code = 0);
      ~Sites (void);
      const double L0(void) const
      /*!
        \return alignment length in terms of non-degenerate sites
      */
        {
          return _L0;
        }
      const double L2S(void) const
      /*!
        \return alignment length in terms of transitional-degenerate sites
      */
        {
          return _L2S;
        }
      const double L2V(void) const
      /*!
        \return alignment length in terms of transversional-degenerate sites
      */
        {
          return _L2V;
        }
      const double L4(void) const
      /*!
        \return alignment length in terms of fourfold-degenerate sites
      */
        {
          return _L4;
        }
    };
}
#endif
