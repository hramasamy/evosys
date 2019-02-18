#include <Sequence/PolyTable.hpp>
#include <Sequence/stateCounter.hpp>
#include <string>

namespace Sequence
{
  struct _PolySNPImpl
  /*!
    Implementation details for PolySNP.  This class is visible
    so that it can be accessed from classes derived from PolySNP.
    A PolySNP object contains a pointer to an instance of this class
    that is storage class protected.
  */
  {
    const PolyTable* _data;
    unsigned _nsites,_nsam,_outgroup;
    bool _haveOutgroup, _totMuts;
    unsigned _totsam;
    unsigned _DVK;
    double _DVH;
    bool _counted_singletons;
    bool _know_pi;
    bool _CalculatedDandV;
    double _pi;
    unsigned _singletons;
    unsigned _walls_Bprime,_NumPoly;
    double _walls_B,_walls_Q;
    bool _calculated_wall_stats;
    std::vector< Sequence::stateCounter > _counts;
    std::vector< std::pair< bool, Sequence::stateCounter > > _derivedCounts;
    bool _preprocessed;
    void preprocess(void);

    _PolySNPImpl (const Sequence::PolyTable * data, bool haveOutgroup ,
		  unsigned outgroup, bool totMuts);
  };
}
