package com.biocis.servlets;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class ClusterRefreshServlet extends HttpServlet {

    //    ResourceBundle rb = ResourceBundle.getBundle("LocalStrings");

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
	//        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
	Vector ChromosomeLists = new Vector () ;

	String SessionId = request.getParameter ("session") ;
        String OrganismName = request.getParameter("organism");
	String OrganismFile = request.getParameter("file") ;
	String ConfigDirectory = "/usr/local/apache/htdocs/gbrowse/databases/" ;
	File Rfile = new File (ConfigDirectory + SessionId + "/RefreshComplete") ;


	out.println("<html>");
	out.println("<head>");

	String title = "Cluster Result Refresh Page" ;

	out.println("<title>" + title + "</title>");
	out.println ("<img src=\"http://192.168.1.102/graphics/clear.gif\"> <right><a href=\"http://192.168.1.102/results.html\">HELP</a></right><p>" ) ;
	if (!Rfile.exists())
	    {
		out.println("<META HTTP-EQUIV=Refresh CONTENT=\"10\"; URL=http://192.168.1.102:9000/biocis/servlet/ClusterRefreshServlet>") ;
		out.println("</head>");
		out.println ("<body>") ;
	    }
	else
	    {
		out.println("</head>");
		out.println ("<body>") ;
		out.println ("<b> All units are done</b>") ;
	    }

	ReadChromosomeLists (ConfigDirectory + OrganismName, ChromosomeLists, out) ;
	for (int i = 0 ; i < 10000 ; i++) {}
	int TotalDone ;
	TotalDone = 0 ;
	for (int i = 0 ; i < ChromosomeLists.size() ; i++)
	    {
		String DoneFileName ;
		DoneFileName = ConfigDirectory + SessionId + "/" + SessionId +  "_" + (String) ChromosomeLists.elementAt(i) + "/" + (String) ChromosomeLists.elementAt(i) + ".done" ;
		File DoneFile = new File (DoneFileName) ;
		if (!DoneFile.exists())
		    {
			//			out.println ("<h3> Results Summary for " +  (String) ChromosomeLists.elementAt(i) + "</h3>") ;
			//			out.println ("<table border=1><tr><td>"  + (String) ChromosomeLists.elementAt(i) + " is being searched, Please wait </td></tr></table>" ) ;
		    }
		else
		    {
			String NoResFileName = ConfigDirectory + SessionId + "/" + SessionId +  "_" + (String) ChromosomeLists.elementAt(i) + "/" + (String) ChromosomeLists.elementAt(i) + ".nores" ;
			File NoRes = new File (NoResFileName) ;

			TotalDone++ ;
			if (!NoRes.exists())
			    {
				out.println ("<h3> Results Summary for " +  (String) ChromosomeLists.elementAt(i) + "</h3>") ;
				out.println ("<table><tr><td> Click on the right to see detailed results </td><td> <A href=http://192.168.1.102/gbrowse/databases/" + SessionId + "/" + SessionId + "_" + ChromosomeLists.elementAt(i) + " target=" + (String)ChromosomeLists.elementAt(i) + ">" +  (String)ChromosomeLists.elementAt(i) + "</A></td></tr>") ;
				out.println ("</table>") ;
				out.println ("<table border=1>") ;
				PrintResults (DoneFile, out) ;
			    }
			else
			    {
			    }

		    }
		out.println ("<p>") ;

	    }

	if (TotalDone == ChromosomeLists.size())
	    {
		FileWriter wr = new FileWriter (Rfile) ;
		PrintWriter pw = new PrintWriter (wr) ;
		pw.println ("<b> All the units are done</b>") ;
		out.println ("<b> All the units are done</b>") ;
		wr.close() ;
	    }
	else
	    {
		Rfile.delete() ;
		out.println ("<table> <tr><td> <b> Total units searched  " + TotalDone  + " units, will refresh the results in 10 seconds with more results</b></td></tr>") ;
		out.println ("<tr><td><b> The program is searching the  " + (ChromosomeLists.size() - TotalDone) + " units, will refresh the results in 10 seconds</b></td></tr></table>") ;
	    }
        out.println("</body>") ;
	out.println("</html>");

    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request, response);
    }

    public void ReadChromosomeLists (String filename, Vector ch, PrintWriter out)
    {

	File file = new File (filename) ;

	if (!file.exists() || !file.canRead())
	    {
		out.println ("ClusterRefresh.java : Error in ReadChromosomeLists Module <p>") ;
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
			out.println ("ClusterExecute.java : IO error occurred " + file + "<p>") ;
		    }
	    }
	catch (FileNotFoundException e)
	    {
		out.println ("ClusterExecute.java : File not found " + file + "<p>") ;
	    }

    }

    void PrintResults (File Done, PrintWriter out)
    {

	DataInputStream dis = null;
	String record ;
      try {

           FileInputStream fis = new FileInputStream(Done);
           BufferedInputStream bis = new BufferedInputStream(fis);
           dis = new DataInputStream(bis);

           while ( (record=dis.readLine()) != null ) {
              out.println(record);
           }

        } catch (IOException e) {
           // catch io errors from FileInputStream or readLine()
           out.println("Uh oh, got an IOException error!" + e.getMessage());

        } finally {
           // if the file opened okay, make sure we close it
           if (dis != null) {
	      try {
                 dis.close();
	      } catch (IOException ioe) {
	      }
           }
        }
    }

}
