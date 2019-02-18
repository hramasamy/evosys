#ifndef __UNWEIGHTED_H__
#define __UNWEIGHTED_H__

#include <Sequence/WeightingSchemes.hpp>
/*! \file Unweighted.hpp"
  @brief declares Sequence::Unweighted2 and  Sequence::Unweighted3
*/

/*!
  \class Sequence::Unweighted2 Sequence/Unweighted.hpp
  \ingroup weights
  @short weights all pathways equally
  \note This is generally not what you want to use (it biases the result to a higher Ka/Ks ratio)
*/

/*!
  \class Sequence::Unweighted3 Sequence/Unweighted.hpp
  \ingroup weights
  @short weights all pathways equally
  \note This is generally not what you want to use (it biases the result to a higher Ka/Ks ratio)
*/
namespace Sequence
  {
  class Unweighted2 : public WeightingScheme2
    {
    private:
      mutable double __weights[2];//logical const
    public:
      explicit Unweighted2(void)
      {}
      ;
      ~Unweighted2(void)
      {}
      ;
      void Calculate(const std::string &codon1, const std::string &codon2) const;
      double *weights(void) const;
    };

  class Unweighted3 : public WeightingScheme3
    {
    private:
      mutable double __weights[6];//logical const
    public:
      explicit Unweighted3(void)
      {}
      ;
      ~Unweighted3(void)
      {}
      ;
      void Calculate(const std::string &codon1, const std::string &codon2) const;
      double *weights(void) const;
    };
}
#endif
