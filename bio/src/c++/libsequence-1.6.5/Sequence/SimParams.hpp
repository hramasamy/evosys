#ifndef SIMPARAMS_H
#define SIMPARAMS_H
/*! \file SimParams.hpp
  @brief Sequence::SimParams reads in the parameters of Dick Hudon's coalescent simulation program.  Used in conjunction with Sequence::SimData
*/

/*! \class Sequence::SimParams Sequence/SimParams.hpp
  \ingroup coalescent
  include SimParams.h
  Allows reading in and printing out of the parameter
  list that Hudson's coalescent simulation program spits
  out at the beginning of its execution.  An example of use
  is found in tajd.cc in the Examples section.
 
  @author Kevin Thornton
  @short Parameters for Hudson's simulation program
*/
#include <iosfwd>
#include <string>
#include <vector>
#include <cstdio>

namespace Sequence
{
  class SimParams
  {
    friend std::ostream& operator<<(std::ostream&,class SimParams &object);
  private:
    std::string _command_line;
    unsigned _howmany, tsam;
  public:
    SimParams(void);
    std::istream& read(std::istream& s);
    int fromfile ( FILE * openfile );
    std::string params (void) const
      /*!
	\return the command-line input to ms
	\note for complicated models, this can be parsed
	with a stringstream to figure out what the parameters are
      */
    {
      return _command_line;
    }
    unsigned totsam (void) const
      /*!
	\return the total sample size (# gametes)
      */
    {
      return (tsam);
    }
    unsigned runs (void) const
      /*!
	\return number of genealogies to generate
      */
    {
      return (_howmany);
    }
  };

  std::istream& operator>>(std::istream& s,  SimParams& c);
}
#endif
