package com.biocis.utils;


public class SuperCluster implements Runnable {

    String filename ;
    String chromosome ;
    String session ;
    String cache ;

    static {
	System.loadLibrary ("SuperClusterBundle") ;
    }

    public SuperCluster (String filename, String chromosome, String session, String cache)
    {
	this.filename = filename ;
	this.chromosome = chromosome ;
	this.session = session ;
	this.cache = cache ;
    }

    public void SetParameters (String filename, String chromosome, String session, String cache)
    {
	this.filename = filename ;
	this.chromosome = chromosome ;
	this.session = session ;
	this.cache = cache ;
    }

    public void run()
    {
	FindCluster() ;
    }


    public void FindCluster ()
    {
	PerformSearch(filename, chromosome, session, cache) ;
    }

    public native int PerformSearch(String filename, String chromosome, String session, String cache) ;

    public static void main(String[] args)
    {
	SuperCluster bt = new SuperCluster(args[0], args[1], args[2], args[3]) ;
	bt.FindCluster() ;
    }

}

