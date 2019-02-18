package com.biocis.utils;

public class SuperEnhancerCheck {

    String filename ;
    String chromosome ;
    String session ;
    String cache ;

    static {
	String path = System.getProperty("java.library.path");
	System.out.println(path);
	System.loadLibrary ("SuperECheck") ;
    }

    public SuperEnhancerCheck (String filename, String chromosome, String session, String cache)
    {
	this.filename = filename ;
	this.chromosome = chromosome ;
	this.session = session ;
	this.cache = cache ;
    }

    static public void check(String filename, String chromosome, String session, String cache) {
        PerformCheck(filename, chromosome, session, cache) ;
    }

    static public native int PerformCheck(String filename, String chromosome, String session, String cache) ;
}

