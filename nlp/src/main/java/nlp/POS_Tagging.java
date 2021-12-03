package nlp;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.opencsv.CSVReader;

public class POS_Tagging {
	//static CSVReader readbdd;

	public Vector<String> pos_data() throws Exception
	{
		// TODO Auto-generated method stub
	      // display tokens
	      Vector<String> l=new Vector<String>();
	      String text="\0";
		  String s="";
		  try { 
			 //method to retrieve data from excel sheet
			 //Fetch_data fd=new Fetch_data();
			// readbdd=new CSVReader(new FileReader("input\\cortex_autologout_input.csv"));
			//	String text="User logins to the webpage. If user stays in Application and does not do any operation for a specific period in any tab Then each tab will report inactivity timeout around the same time, Else customer resumes activity in any tab each tab will be refreshed with the latest operation timestamp. Inactivity timeout trigger will put a popup which says your session will be expired due to inactivity in next Netinsights-timeout-grace-period seconds. If user performs any operation in Netinsights-timeout-grace-period seconds after inactivity timeout comes out Then activity will be performed according to operation selected by user, Else automatically log offs. If user clicks logout in inactivity timeout Then user will log off, Else if customer clicks Continue, will be closed and Netinsights-last-activity-timestamp will be refreshed to be the current timestamp"
			// set up pipeline properties
			// String text="User opens the item page.";
			 String readbdd = "input\\\\cortex_autologout_input.csv";
			 
		        // Reads text from a character-input stream, buffering characters so as to provide for the
		        // efficient reading of characters, arrays, and lines.
		        BufferedReader crunchifyBufferReader = null;
		        String crunchifyLine = "";
		        crunchifyBufferReader = new BufferedReader(new FileReader(readbdd));
	            if ((crunchifyLine = crunchifyBufferReader.readLine()) != null) {
	            	text=crunchifyLine;
	            	
	            }
		      Properties props = new Properties();
		      // set the list of annotators to run
		      props.setProperty("annotators", "tokenize,ssplit,pos");
		      // build pipeline
		      StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		      // create a document object
		      CoreDocument document = new CoreDocument(text);
		      pipeline.annotate(document);
	
		      for(CoreSentence sent : document.sentences())
		      {
		      
		      for (CoreLabel tok : sent.tokens()) {
		       // System.out.println(String.format("%s %s", tok.word(), tok.tag()));
		    	if(tok.word().equals(","))
		    	{
		    		s+=tok.word()+" ";
		    	}
		    	else
		    	{
		        s+=tok.word()+" "+tok.tag()+" ";
		    	}
		        
		      }
		    
		      l.add(s);
		      
		      s="\0";
		      }
		      
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		return l;

	}

}
