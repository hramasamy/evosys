#include <SpeciesIndex.h>
#include <SpeciesDefines.h>

    int 
    SpeciesIndex::ConvertTriplet(const char *trip) 
    {
      int i ;
      int tripValue ;
      tripValue = 0 ;
      for (i = 0 ; i < strlen(trip) ; i++)
	{
	  tripValue |= (ATGC[trip[i] - 0x41] << (i * 2)) ;
	}
      return tripValue ;
    }


void 
SpeciesIndex::ReadPositionFile()
{

    string line ;
    ifstream myfile (filename.c_str());
    if (myfile.is_open())
      {
	while ( myfile.good() )
	  {
	    getline (myfile,line);
	    if (line.length() ==0) 
	      break ;
	    Splitter LeftBracket(line, "[") ;
	    if (LeftBracket.size() > 0)
	      {
		Splitter split(LeftBracket[0], "\t") ;
		if (split.size() > 0)
		  {
		    Splitter split2(split[1], ":") ;
		    if (split2.size() > 0)
		      {
			TripFreq[ConvertTriplet(split[0].c_str())] = atoi(split2[1].c_str()) ;
		      }
		  }
	      }
	  }
	myfile.close();
      }

    else cout << "Unable to open file"; 

    return ;

}
