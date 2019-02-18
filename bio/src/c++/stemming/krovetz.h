//////////////////////////////////////////
//DATE: NOVEMBER 11, 1996               //
//MADE BY WOOSEOB JEONG                 //
//Modified by Kiduk Yang 11/25/96       //
//FILE NAME : krovetz.h                 //
//                                      //
//////////////////////////////////////////



int Krovetz_i
    (char inword[], fstream &dict, fstream &xlist, int fword){


int indict;
stemwd wd(inword,fword);

// do not stem if word is in dictionary
indict= wd.sdict(dict);  
if (indict);  // do not stem if word is in dictionary

else if (wd.sf("ies")) {
    wd.rp(inword,"ies","ie");
    indict= wd.sdict(dict);  
    if (!indict) wd.rp(inword,"ie","y");
}

else if (wd.sf("es")) {
    wd.rp(inword,"es","e");
    indict= wd.sdict(dict);  
    if (!indict) {
        wd.rp(inword,"e","\0");
        indict= wd.sdict(dict);  
        if (!indict) wd.before(inword);
    }
}

else if (wd.sf("s") && !wd.sf("ous") && !wd.sf("ss")) {
    wd.rp(inword,"s","\0");
    indict= wd.sdict(dict);  
    if (!indict) wd.before(inword);
}

else {

    if (wd.sf("ed")) {
	// addition to Krovetz algorithm.
	wd.rp(inword,"ed","eed");
	indict= wd.sdict(dict);  
	if (!indict) {
	    wd.before(inword);
	    wd.rp(inword,"ed","e");
	    indict= wd.sdict(dict);  
	    if (indict) {
		wd.rp(inword,"e","\0");
		if (!wd.sxlst(xlist)) wd.before(inword);
	    }
	    else {
		wd.rp(inword,"e","ee");
		indict= wd.sdict(dict);  
		if (!indict) {
		    wd.before(inword);
		    wd.rp(inword,"e","\0");
		    indict= wd.sdict(dict);  
		    if (!indict) {
			if (wd.d()) {
			    wd.xd(inword);
			    indict= wd.sdict(dict);  
			    if (!indict) wd.before(inword);
			}
			else wd.before(inword);
		    }
		}
	    }
	}
 
    }


    if (wd.sf("ing")) {
	wd.rp(inword,"ing","e");
	indict= wd.sdict(dict);  
	if (indict) {
	    wd.rp(inword,"e","\0");
	    if (!wd.sxlst(xlist)) wd.before(inword);
	}
	else {
	    wd.rp(inword,"e","ee");
	    indict= wd.sdict(dict);  
	    if (!indict) {
		wd.before(inword);
		wd.rp(inword,"e","\0");
		indict= wd.sdict(dict);  
		if (!indict) {
		    if (wd.d()) {
			wd.xd(inword);
			indict= wd.sdict(dict);  
			if (!indict) wd.before(inword);
		    }
		    else wd.before(inword);
		}
	    }
	}
    }

}
  
return(indict);

}
