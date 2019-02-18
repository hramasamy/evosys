#include <Sequence/Unweighted.hpp>

namespace Sequence
  {
  void Unweighted2::Calculate(const std::string &codon1, const std::string &codon2) const
  /*!
    Calculate actually calculates the weights for each branch
    \param codon1 a std::string of length 3 representing a sense codon
    \param codon2 a std::string of length 3 representing a sense codon
  */
    {
      __weights[0] = 0.5;
      __weights[1] = 0.5;
    }

  double* Unweighted2::weights(void) const
  /*!
    \return a double * of size 2 (1 value for each branch)
  */
    {
      return __weights;
    }

  void Unweighted3::Calculate(const std::string &codon1, const std::string &codon2) const
  /*!
    Calculate actually calculates the weights for each branch
    \param codon1 a std::string of length 3 representing a sense codon
    \param codon2 a std::string of length 3 representing a sense codon
  */
    {
      __weights[0] = 1.0/6.0;
      __weights[1] = 1.0/6.0;
      __weights[2] = 1.0/6.0;
      __weights[3] = 1.0/6.0;
      __weights[4] = 1.0/6.0;
      __weights[5] = 1.0/6.0;
    }

  double* Unweighted3::weights(void) const
  /*!
    \return a double * of size 6 (1 value for each branch)
  */
    {
      return __weights;
    }
}
