#ifndef POLYSITES_H
#define POLYSITES_H
/*! \file PolySites.hpp
  @brief Sequence::PolySites, generates polymorphism tables from data
*/
#include <Sequence/PolyTable.hpp>
namespace Sequence
  {
  class Fasta;
  class PolySites : public PolyTable
    {
    private:
      /*!
      PolySites::fillIt() is the function that actually fills the polymorphism table.
      */
      template<class __DataType>
      void fillIt(const std::vector < __DataType >&alignment,
                         bool strictInfSites = 0,
                         bool ignoregaps = 1,bool skipMissing=false,
                         unsigned freqfilter=0);
    protected:
      unsigned numseqs;
      unsigned seqlen;
    public:
      PolySites (void);
      template<typename __DataType>
      PolySites (const std::vector < __DataType >&alignment, bool strictInfSites =
                   0, bool ignoregaps = 1,bool skipMissing=false,
                 bool skipAdjSNP=false, unsigned freqfilter=0);
      PolySites (const std::vector < double > &List, const std::vector < std::string > &stringList);
      PolySites (PolyTable::const_site_iterator beg,
		 PolyTable::const_site_iterator end);
      ~PolySites(void)
      {}
      std::istream & read(std::istream &s) throw (Sequence::badFormat,std::exception);
      std::ostream & print(std::ostream &stream) const;
    };
}
#include <Sequence/bits/PolySites.tcc>
#endif
