package com.biocis.utils;

import java.io.*;
import java.text.*;
import java.util.*;
import com.sbs.utils.AppLogger;
import com.sbs.utils.string.Strings;
import com.sbs.utils.threads.IRunnable;
import com.sbs.utils.threads.SbsRunnable;
import com.sbs.utils.threads.ThreadUtils;

public class SuperEnhancerSystem extends SbsRunnable {
    static AppLogger logger = AppLogger.getLogger(SuperEnhancerSystem.class);

    String filename ;
    List chromosomes ;
    String session ;
    String cache ;
    SuperEnhancer enhancer ;
    String organism ;
    String OrgName ;
    List processList = new Vector();

    public SuperEnhancerSystem (String  filename, List chromosomes, String session, String cache, String organism, String Name)
    {
        this.filename = filename;
        this.chromosomes = chromosomes ;
        this.session = session ;
        this.cache = cache ;
        this.organism = organism ;
        this.OrgName = Name ;
    }

    public void run ()
    {
        super.run();

        String flname = filename + "/config.txt";
        logger.debug("Total chromosomes to search: " + chromosomes);
        for (int i  = 0 ;  i < chromosomes.size() ; i++)  {
            String chName = (String) chromosomes.get(i);
            if (Strings.isNotEmpty(chName)) {
                logger.debug("Going to search: " + chName);

                createSetup (filename, session, chName, organism);

                String command = "java -classpath /usr/share/tomcat6/webapps/BioScope/WEB-INF/classes com.biocis.utils.SuperEnhancer " +  filename  + "/config.txt " + chName + " " +  session +  " " + cache ;
		System.out.println(command) ;

                try {
                    executeCommand (command, true) ;
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            if (this.getSate()== IRunnable.STOPPED) {
                logger.debug("Stop requested, skipped processing: " + (chromosomes.size() - i));
                break;
            }
        }

    }

    public String toString() {
        StringBuffer buffer = new StringBuffer(this.getClass().getName());
        buffer.append("[").append("filename: " + this.filename).append(", chromsomes: ");
        buffer.append(this.chromosomes).append(", session: ").append(this.session).append(", organism: ").append(this.organism);
        buffer.append("orgName: ").append(this.OrgName).append("]");
        return buffer.toString();
    }

    public void PreSetup (String ConfigDir, String Sesid, String CName, String ReplaceString)
    {
        String ResultsDir = ConfigDir + "/" + CName  ;
        DeleteFile (ResultsDir + "/" + CName + ".done") ;
    }

    public void createSetup (String ConfigDir, String Sesid, String CName, String ReplaceString)
    {
        try {
            String ResultsDir = ConfigDir + "/" + CName  ;
            File file = new File (ResultsDir) ;
            if (!file.exists() || !file.canRead())
            {
                file.mkdirs() ;
            }
            String OrgDir = OrgName ;

            DeleteFile (ResultsDir + "/" + CName + ".done") ;
            DeleteFile (ResultsDir + "/" + CName + ".fa" ) ;
            executeCommand ("ln -s  /usr/local/apache/htdocs/gbrowse/databases/" + OrgDir.toUpperCase() + "/" + CName + "/" + CName + ".fa " + ResultsDir + "/" , true) ;
            DeleteFile (ResultsDir + "/" + CName + ".gene" ) ;
            DeleteFile (ResultsDir + "/" + CName + ".gff" ) ;
            appendFiles ("/usr/local/apache/htdocs/gbrowse/databases/" + OrgDir.toUpperCase() + "/" + CName + "/" + CName + ".gff", ResultsDir + "/" + CName + ".gff" ) ;
            executeCommand ("/usr/local/apache/cgi-bin/replacestring.pl -f /usr/local/apache/conf/gbrowse.conf/generic.conf  -s =organism -r =" + ReplaceString + " -o /usr/local/apache/conf/gbrowse.conf/" + Sesid + "_temp.conf" , true) ;
            executeCommand ("/usr/local/apache/cgi-bin/replacestring.pl -f /usr/local/apache/conf/gbrowse.conf/" + Sesid + "_temp.conf" + "  -s /usr/local/apache/htdocs/gbrowse/databases  -r /usr/local/apache/htdocs/gbrowse/databases" + "/" + Sesid + "/" + Sesid + "_" + CName  +  " -o /usr/local/apache/conf/gbrowse.conf/" + Sesid + "_" + CName + ".conf" , true) ;
            DeleteFile ("/usr/local/apache/conf/gbrowse.conf/" + Sesid + "_temp.conf" ) ;
            appendFiles ("/usr/local/apache/conf/gbrowse.conf/Enhancer.conf", "/usr/local/apache/conf/gbrowse.conf/" + Sesid + "_" + CName + ".conf") ;
        }
        catch (Exception ex) {
            logger.warn("Failed during setup. Cause: " + ex.getMessage(), ex);
        }

    }

    public void copyBinaryFiles (String FileToBeCopied, String FileToCopy)
    {
        try
        {
            File inputFile = new File(FileToBeCopied);
            File outputFile = new File(FileToCopy);

            FileInputStream in = new FileInputStream(inputFile);
            FileOutputStream out = new FileOutputStream(outputFile);
            int c;

            while ((c = in.read()) != -1)
                out.write(c);

            in.close();
            out.close();
        }
        catch (IOException e)
        {
            logger.warn(e);
        }
    }

    public void appendFiles (String FileContent, String FileToAppend) {
        try {
            RandomAccessFile raf = new RandomAccessFile(FileToAppend, "rw");
            // Position yourself at the end of the f
            //     ile
            raf.seek(raf.length());
            // Write the String into the file. Note
            //     that you must
            // explicitly handle line breaks.
            try
            {
                FileReader fr = new FileReader (FileContent) ;
                BufferedReader in = new BufferedReader (fr) ;
                String line ;
                try
                {
                    while ((line = in.readLine()) != null)
                    {
                        raf.writeBytes(line + "\n") ;
                    }
                    fr.close() ;
                }
                catch (IOException e)
                {
                    logger.warn(e);
                }

            }
            catch (FileNotFoundException e)
            {
                logger.warn(e);
            }
            raf.close() ;
        }
        catch (IOException e) {
            logger.warn(e);
        }
    }

    public void stop() {
        if (this.processList!=null && this.getSate()!=IRunnable.STOPPED) {
            for (int i=0; i<this.processList.size(); i++) {
                Process p = (Process)this.processList.remove(i);
                try {
                    if (p!=null) {
                        logger.debug(this.getName() + " --> Going to destroy the process");
                        p.destroy();
                    }
                }
                catch (Throwable t) {
                    logger.warn("Exception while destroying the process. ", t);
                }
            }
        }
        super.stop();
    }

    public void executeCommand (String command, boolean Wait) throws InterruptedException, IOException
    {
        if (this.getSate()==IRunnable.RUNNING) {
            logger.debug("Executing a command: " + command);

            Process p = Runtime.getRuntime().exec(command);
            processList.add(p);
            if (Wait)
            {
                int exitval = p.waitFor() ;
                logger.debug(" process exit value " + exitval) ;
            }
            processList.remove(p);
        }
        else {
            logger.debug("Can't proceed with execution, the state is either pending or stopped!");
        }
    }

    void DeleteFile (String flName)
    {
        File rmFile = new File (flName) ;
        if (rmFile.exists()){
            rmFile.delete() ;
        }
    }

}