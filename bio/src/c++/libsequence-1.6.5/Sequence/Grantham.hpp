#ifndef GRANTHAM_H
#define GRANTHAM_H
/*! \file Grantham.hpp
  @brief Grantham's distances (Sequence::Grantham)
*/

/*! \class Sequence::Grantham Sequence/Grantham.hpp
  A functor to return the Grantham's distance between
  two amino acids.
 
  @short Grantham's distances
*/

namespace Sequence
  {
  class Grantham
    {
    private:
      double D[60][60];
    public:
      Grantham(void);
      const double operator()(char aa1, char aa2);
    };
}
#endif
