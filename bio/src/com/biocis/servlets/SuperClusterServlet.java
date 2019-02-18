
package com.biocis.servlets ;

import java.lang.* ;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.biocis.utils.*;


public class SuperClusterServlet extends HttpServlet {

    //    ResourceBundle rb = ResourceBundle.getBundle("LocalStrings");

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {

        response.setContentType("text/html");
	PrintWriter out = response.getWriter () ;

        // img stuff not req'd for source code html showing
	// relative links everywhere!

        HttpSession session = request.getSession(true);

	SetConfigFiles (request, response, out) ;


    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request, response);
    }

    public void SetConfigFiles (HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException, ServletException
    {

	String ConfigDirectory = "/usr/local/apache/htdocs/gbrowse/databases" ;

	Map map = new TreeMap() ;
	Map overlap = new TreeMap() ;
	Map other = new TreeMap() ;
	Vector ChromosomeLists ;

	Enumeration names = request.getParameterNames() ;
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = request.getParameter(name);
	    if (name.length() == 1)
		{
		    map.put(name, value) ;
		    if (value.length() != 0)
			{
			    if (request.getParameter(name + "O") == null)
				{
				    overlap.put(name, "0") ;
				}
			    else
				{
				    overlap.put(name, "1") ;
				}
			}
		}
	    else
		{
		    other.put(name, value) ;
		}

        }

        HttpSession session = request.getSession(true);
	session.setMaxInactiveInterval(3600) ;
       	if(session.isNew())
	    {
	    }
	else
	    {
		sessionDestroyed (session.getId()) ;
		//		session.invalidate() ;
	    }
        String ConfigFileDirectory = ConfigDirectory + "/" + session.getId();
	String OrganismListFile = ConfigDirectory + "/" + other.get("organism") ;

	String DoCache ;

	if ((other.get("cache") != null) && (other.get("cache").equals("ON")))
	    {
		DoCache = "1" ;
	    }
	else
	    {
		DoCache = "0" ;
	    }

	String ReplaceString  ;
	FileReader fr = new FileReader (OrganismListFile + ".info") ;
	BufferedReader in = new BufferedReader (fr) ;
	try
	    {
		ReplaceString = in.readLine() ;
		fr.close() ;
	    }
	catch (IOException e)
	    {
		ReplaceString = "organism" ;
		out.println ("SuperClusterServlet.java : IO error occurred <p>") ;
	    }
	ChromosomeLists = new Vector() ;
	ReadChromosomeLists (OrganismListFile, ChromosomeLists, out) ;

	File RemoveConfigFile = new File (ConfigFileDirectory + "/config.txt") ;
	RemoveConfigFile.delete() ;
	RemoveConfigFile = new File( ConfigFileDirectory + "/config.err") ;
	RemoveConfigFile.delete() ;
	File RFile = new File (ConfigFileDirectory + "/RefreshComplete") ;
	RFile.delete() ;
	WriteConfigFile (ConfigFileDirectory, map, overlap, other, out) ;
	SuperClusterCheck CheckGrammar = new SuperClusterCheck (ConfigFileDirectory + "/config.txt", "", session.getId(), "") ;
	CheckGrammar.CheckCluster() ;

	String ErrorFileName ;
	ErrorFileName = ConfigFileDirectory +  "/config.err"  ;
	File ErrorFile = new File (ErrorFileName) ;
	if (ErrorFile.exists())
	    {
		out.println ("<html>") ;
		out.println("<body bgcolor=\"white\">");
		out.println("<head>");

		String title = "Cluster Error Page" ;

		out.println("<title>" + title + "</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("You have errors in the logical expression<br>") ;
		out.println("make sure you have nested them in parenthesis properly <br>") ;
		out.println("Some examples are <br>") ;
		out.println("(1A & 1B) will search for presence of atleast 1 of A and B <br>") ;
		out.println("(1A & (1B | 1C)) will search for presence of atleast 1 of A with B or C <br>") ;
		out.println("(1A & (1B & 1C)) will search for presence of atleast 1 of A and B and C <br>") ;
		out.println("</body>") ;
		out.println("</html>") ;

		return ;

	    }
	for (int i=0; i<ChromosomeLists.size(); i++) {
	    File RemoveFile = new File (ConfigFileDirectory + "/" + session.getId() + "_" + (String)ChromosomeLists.elementAt(i) + "/" + (String)ChromosomeLists.elementAt(i) + ".done") ;
	    RemoveFile.delete() ;
	    RemoveFile = new File( ConfigFileDirectory + "/" + session.getId() + "_" + (String)ChromosomeLists.elementAt(i) + "/" + (String)ChromosomeLists.elementAt(i) + ".nores") ;
	    RemoveFile.delete() ;

	    //		    out.println (Command + "<br>") ;
	}

	//	if (other.get("C1"))
	//	    {
	//		cache = "1" ;
	//	    }
	//	else
	//	    {
	//		cache = "0" ;
	//	    }

	SuperClusterSystem ESystem = new SuperClusterSystem (ConfigFileDirectory, ChromosomeLists, session.getId(), DoCache, ReplaceString, (String) other.get("organism")) ;

	Thread EThread = new Thread (ESystem) ;

	EThread.start() ;

	String urlValue = "http://cigbrowser.berkeley.edu:9000/Enhancer/servlet/ClusterRefreshServlet?session=" + session.getId() + "&organism=" + other.get("organism")  + "&file=" + ReplaceString  ;
	response.sendRedirect(urlValue);



    }

    public void ExecuteCommand (String Command, PrintWriter out)
    {
        try
            {
                //              out.println(Command + "<br>") ;
                Process p ;
                p = Runtime.getRuntime().exec(Command);
                try
                    {
                        int exitval = p.waitFor() ;
                        //                      System.out.println ("exit value " + exitval + "<br>") ;
                    }
                catch (InterruptedException e)
                    {
                        out.println ("SuperEnhancerServlet.java : while executing " + Command + "  <br>") ;
                    }
            }
        catch (IOException e)
            {
                out.println ("SuperEnhancerServlet.java : Command " + Command + "Failed  <br>") ;
            }

    }

    public void WriteConfigFile ( String filename, Map exp, Map over, Map rest, PrintWriter out)
    {
	File file = new File (filename) ;

	if (!file.exists() || !file.canRead())
	    {
		file.mkdirs() ;
	    }
	String ConfigFilename = filename + "/" + "config.txt" ;

	file = new File (ConfigFilename) ;

	try
	    {
		file.createNewFile() ;
	    }
	catch (IOException e)
	    {
		out.println ("SuperEnhancerServlet.java : Cannot create file " + filename + " <br>") ;
	    }

	try
	    {
		FileWriter wr = new FileWriter(ConfigFilename) ;
		PrintWriter br = new PrintWriter(wr) ;

		String line ;

		line = "<expressions>\n"  ;

		List keys = new ArrayList(exp.keySet()) ;
		Iterator keyIt = keys.iterator() ;
		while (keyIt.hasNext())
		    {
			Object key = keyIt.next() ;
			String val = (String) exp.get(key) ;
			if (val.length() != 0)
			    line += key + "=" + val + "\n" ;
		    }
		line += "</expressions>\n" ;

		line += "<overlap>\n"  ;

		keys = new ArrayList(over.keySet()) ;
		keyIt = keys.iterator() ;
		while (keyIt.hasNext())
		    {
			Object key = keyIt.next() ;
			String val = (String) over.get(key) ;
			if (val.length() != 0)
			    line += key + "=" + val + "\n" ;
		    }
		line += "</overlap>\n" ;

		line += "<organism>\nname=" + rest.get("organism") + "\n</" + "organism" + ">\n" ;
		line += "<directory>\nname=" + "/usr/local/apache/htdocs/gbrowse/databases/" + "\n</" + "directory" + ">\n" ;
		if (((String) rest.get("sites")).length() == 0)
		    {
			line += "<sites>\nnumber=1\n</" + "sites" + ">\n" ;
		    }
		else
		    {
			line += "<sites>\nnumber=" + rest.get("sites") + "\n</" + "sites" + ">\n" ;
		    }
		if (((String) rest.get("constraint")).length() == 0)
		    {
			line += "<constraint>\nwidth=20\n</" + "constraint" + ">\n" ;
		    }
		else
		    {
			line += "<constraint>\nwidth=" + rest.get("constraint") + "\n</" + "constraint" + ">\n" ;
		    }
		line += "<boolean>\ncondition=" + ((String)rest.get("boolean")).toUpperCase()  + "\n</" + "boolean" + ">\n" ;
		line += "<display>\nwidth=" + (String)rest.get("display")  + "\n</display>\n" ;
		line += "<direction>\nvalue=" + (String)rest.get("direction")  + "\n</direction>\n" ;
		line += "<nooverlap>\nlist=" + (String)rest.get("nooverlap")  + "\n</nooverlap>\n" ;
		line += "<genenames>\nlist=" + ((String)rest.get("genenames")).toUpperCase()  + "\n</genenames>\n" ;

		if (rest.get("anygene") != null)
		    {
			line += "<anygene>\nvalue=1\n</anygene>\n" ;
		    }
		else
		    {
			line += "<anygene>\nvalue=0\n</anygene>\n" ;
		    }

		line += "<save>\nuser=" + rest.get("user") + "\n</save>\n" ;
		line += "<precedence>\nvalue=" + rest.get("precedence") + "\n</precedence>\n" ;
		br.print(line) ;
		wr.close() ;
	    }
	catch (FileNotFoundException e)
	    {
		out.println ("SuperEnhancerServlet.java : config write error <br>") ;
	    }
	catch (IOException e)
	    {
		out.println ("SuperEnhancerServlet.java : config write error <br>") ;
	    }

    }

    public void ReadChromosomeLists (String filename, Vector ch, PrintWriter out)
    {

	File file = new File (filename) ;

	if (!file.exists() || !file.canRead())
	    {
		out.println ("EnhancerExeute.java : Error in ReadChromosomeLists Module <p>") ;
		return ;
	    }
	try
	    {
		FileReader fr = new FileReader (file) ;
		BufferedReader in = new BufferedReader (fr) ;
		String line ;
		try
		    {
			while ((line = in.readLine()) != null)
			    {
				ch.addElement(line) ;
			    }
			fr.close() ;
		    }
		catch (IOException e)
		    {
			out.println ("SuperEnhancerServlet.java : IO error occurred " + file + "<p>") ;
		    }
	    }
	catch (FileNotFoundException e)
	    {
		out.println ("SuperEnhancerServlet.java : File not found " + file + "<p>") ;
	    }
    }


    public void sessionDestroyed(String id) {

	String BaseDir = "/usr/local/apache/htdocs/gbrowse/databases/" ;
	File RemoveDir = new File(BaseDir + id ) ;
	File ConfDir = new File("/usr/local/apache/conf/gbrowse.conf/") ;
	System.out.println ("It Came here") ;
	if (!deleteDir(RemoveDir))
	    {
		System.out.println ("Deleting " + RemoveDir + "......") ;
		//		log ("Session Directory Removal failed (" + ses.getId() + "')") ;
	    }

	deleteConfFiles(ConfDir, id) ;


    }


    // Deletes all files and subdirectories under dir.
    // Returns true if all deletions were successful.
    // If a deletion fails, the method stops attempting to delete and returns false.
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    public static boolean deleteConfFiles(File dir, String session) {
        if (dir.isDirectory())
	    {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
		if (children[i].indexOf(session) != -1)
		    {
			File deleteFile = new File(dir, children[i]) ;
			deleteFile.delete() ;
		    }

	    }
        }
	return (true) ;
    }

}


