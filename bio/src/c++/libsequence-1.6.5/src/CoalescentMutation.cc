#include <Sequence/Coalescent/Mutation.hpp>

namespace Sequence
{
  void output_gametes(FILE * fp,
		      const unsigned & segsites,
		      const unsigned & nsam,
		      const gamete_storage_type & gametes)
  /*!
    @brief Write an object of type gamete_storage type to a C-style file stream
    This function is used when you need to output simulated gametes using a
    method faster than the operator<< for class SimData.
    \param fp pointer to an open C-style output stream
    \param segsites the number of segregating sites in \a gametes
    \param nsam the number of individuals in \a gametes
    \param gametes the simulated sample.  Must be allocated to hold at least 
    \a segsites positions, and \a nsam strings of length \a segsites
  */
  {
    fprintf(fp,"//\n");
    if ( segsites > 0 )
      {
	fprintf(fp,"segsites: %d\npositions: ",segsites);
	for(unsigned i=0;i<segsites;++i)
	  {
	    fprintf(fp,"%f ",gametes.first[i]);
	  }
	fprintf(fp,"\n");
	for(unsigned i=0;i<nsam;++i)
	  {
	    for(unsigned j=0;j<segsites;++j)
	      {
		fprintf(fp,"%c",gametes.second[i][j]);
	      }
	    fprintf(fp,"\n");
	  }
      }
    else
      {
	fprintf(fp,"segsites: 0\n");
      }
  }
}
