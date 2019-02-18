// ************************************************************************
// Name:     FINDTERM.H
//           Kiduk Yang, 06/19/97
// ------------------------------------------------------------------------
// Description: return a value of Nth element (key) in RAB file
//   -- returns TERM given TERM#
// ------------------------------------------------------------------------
// INPUT:
//   $idir/ran_tnt -- RAB term#-term file
// ------------------------------------------------------------------------
// NOTE:  if not found, return 0
// ************************************************************************




void findterm(int key, char term[], fstream &fp) {

    const int head=8281;
    char wd[WL];

    fp.clear();
    key=key+head;
    fp.seekg(STSIZE*key,ios::beg); 
    fp.getline(wd,sizeof(wd));      
    strcpy(term,wd);

}
