// ************************************************************************
// Name:     DEFINE.H
// Author:   Kiduk Yang, 11/29/96
//             modified, 01/13/98
// ------------------------------------------------------------------------
// Description: constants declarations
//   declare global constants with #define statments.
// ------------------------------------------------------------------------
// NOTE:    change PMAP according to system mappings.
// ************************************************************************


//#define DEBUG             // turn debugging on

// constant declarations: adjust if necessary.

#define AZN 26              // Number of letters in a to z
#define AZL 3               // Length of hash key for word lookup
#define MAXHN 17576         // Hash table size: AZN**AZL

#define NL 100          // maximum filename length
#define FNL 10          // Maximum length of filename
#define FFNL 100        // Maximum length of path & filename
#define WL 50           // maximum word length
#define TL 100          // maximum term length
#define BWL  5000       // maximum inverted index entry length
#define SIZE  8L        // size of numeric (integer) element in RAB file
#define FSIZE  32L      // size of numeric (float) element in RAB file
#define STSIZE  50L     // size of single term element in RAB file
#define DTSIZE  100L    // size of double term element in RAB file
#define CTAG 8281       // number of tags in RDICT


// system specific '~' mappings.
#define PMAP   "/export/home/ /export/"
#define UMAP   "/export/home/ /export/"

// ----- created by makelist.cc -----
#define RSWLIST "ran_stoplist"  // stopword search file
#define RXLIST "ran_xlist"      // exception word search file

// ----- created by makelist2.cc -----
#define RDICT "ran_dict"        // dictionary search file

// protected word file buffer for interactive input
#define PWFILE0 "pwbuffer"


