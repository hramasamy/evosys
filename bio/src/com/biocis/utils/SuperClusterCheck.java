package com.biocis.utils;

public class SuperClusterCheck {

    String filename ;
    String chromosome ;
    String session ;
    String cache ;

    static {
	System.loadLibrary ("ClusterECheck") ;

    }
    public SuperClusterCheck (String filename, String chromosome, String session, String cache)
    {
	this.filename = filename ;
	this.chromosome = chromosome ;
	this.session = session ;
	this.cache = cache ;
    }

    public void CheckCluster ()
    {
	//	System.out.println ("Came here okkkk") ;
	PerformCheck(filename, chromosome, session, cache) ;
    }

    public native int PerformCheck(String filename, String chromosome, String session, String cache) ;

        public static void main(String[] args)
            {
    	    //	    System.out.println ("Came here") ;
    	    SuperClusterCheck bt = new SuperClusterCheck(args[0], args[1], args[2], args[3]) ;
    	    bt.CheckCluster() ;
    	}
}

