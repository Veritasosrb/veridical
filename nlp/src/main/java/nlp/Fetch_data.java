package nlp;

import java.io.FileReader;

import com.opencsv.CSVReader;

public class Fetch_data
{

	public String retrieve_data() throws Exception
	{
		 CSVReader reader = new CSVReader(new FileReader("./intermediate/UserStoryJiraNewOutputTrial2.csv"));
	      //Reading the contents of the csv file
	      StringBuffer buffer = new StringBuffer();
	      String line[];
	      String s="";
	      
	      while ((line = reader.readNext()) != null) {
	         for(int i = 0; i<line.length; i++) {
	            //System.out.print(line[i]);
	            //System.out.print("\n");
	            if(i==line.length-1)
	            {
	            	s=line[i];
	            }
	         }
	         
	      }
	      System.out.print(s);
	      return s;
	}

}
