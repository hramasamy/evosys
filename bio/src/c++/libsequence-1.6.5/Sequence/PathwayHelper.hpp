#ifndef __PATHWAYHELPER_H__
#define __PATHWAYHELPER_H__
/*! \file PathwayHelper.hpp
  @brief declarations of Sequence::Intermediates2 and Sequence::Intermediates3
*/
/*!
  \defgroup CodonPaths Classes and functions to aid in the calculations of the pathways between two codons
  This group of classes and functions deals with determining 
  either the counts of silent and replacement differences between codons
  or the intermedate codons that occurs between two different codons
*/
#include <string>

namespace Sequence
{
  void Intermediates2(std::string *intermediates,const std::string &codon1, const std::string &codon2);
  void Intermediates3(std::string *intermediates, const  std::string &codon1, const std::string &codon2);
}
#endif

