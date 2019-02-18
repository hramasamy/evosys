#ifndef __GRANTHAMWEIGHTS_H__
#define __GRANTHAMWEIGHTS_H__

/*! \file GranthamWeights.hpp
  @brief declaration of classes to weight codons by Grantham distance (i.e. for Sequence::Comeron95). Declares 
  Sequence::GranthamWeights2 and  Sequence::GranthamWeights3
*/

/*!
  \class Sequence::GranthamWeights2 Sequence/GranthamWeights.hpp
  \ingroup weights
  @short Weights paths by Grantham's distances for codons differing at 2 sites
*/

/*!
  \class Sequence::GranthamWeights3 Sequence/GranthamWeights.hpp
  \ingroup weights
  @short Weights paths by Grantham's distances for codons differing at 3 sites
*/
#include <Sequence/SeqEnums.hpp>
#include <Sequence/WeightingSchemes.hpp>

namespace Sequence
  {
  class Grantham;
  class GranthamWeights2 : public WeightingScheme2
    {
    private:
      Sequence::GeneticCodes code;
      mutable double __weights[2];//logical const
    public:
      explicit GranthamWeights2(Sequence::GeneticCodes genetic_code = Sequence::UNIVERSAL);
      ~GranthamWeights2(void);
      void Calculate(const std::string &codon1, const std::string &codon2) const;
      double *weights(void) const;
    };

  class GranthamWeights3 : public WeightingScheme3
    {
    private:
      GeneticCodes code;
      mutable double __weights[6];//logical const
    public:
      explicit GranthamWeights3(Sequence::GeneticCodes genetic_code = Sequence::UNIVERSAL);
      ~GranthamWeights3(void);
      void Calculate(const std::string &codon1, const std::string &codon2) const;
      double *weights(void) const;
    };
}
#endif
