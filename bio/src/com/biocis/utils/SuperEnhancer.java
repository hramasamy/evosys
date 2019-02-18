package com.biocis.utils;


class SuperEnhancer {
    static {
        try {
            System.loadLibrary ("SuperEnhancer") ;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    static public void main(String[] args) {
        if (args.length<4) {
            System.out.println("usage SuperEnhancer filename, chromosome, session, cache");
            System.exit(1);
        }
        PerformSearch(args[0], args[1], args[2], args[3]);
    }

    private static native int PerformSearch(String filename, String chromosome, String session, String cache);
}
