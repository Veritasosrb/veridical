package nlp;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.opencsv.CSVWriter;

 


 class t{
	 static CSVWriter writerex;
    public static void main(String[] argv) {
    	try
    	{
    		writerex= new CSVWriter(new FileWriter("C:\\Users\\aditi.inamdar\\Documents\\outputtestcaseinitial\\UserStoryStorepathtrialhomepageautologoutcombinenodup.csv"));
			
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
 
        String crunchifyCSVFile = "C:\\Users\\aditi.inamdar\\Documents\\UserStoryStorepathtrialhomepageautologoutcombine.csv";
 
        // Reads text from a character-input stream, buffering characters so as to provide for the
        // efficient reading of characters, arrays, and lines.
        BufferedReader crunchifyBufferReader = null;
        String crunchifyLine = "";
 
        // This class implements the Set interface, backed by a hash table (actually a HashMap instance).
        // It makes no guarantees as to the iteration order of the set; in particular, it does not guarantee that the order will
        // remain constant over time. This class permits the null element.
        Set<String> crunchifyAllLines = new HashSet<String>();
        crunchifyAllLines.clear();
   
        try {
            crunchifyBufferReader = new BufferedReader(new FileReader(crunchifyCSVFile));
            while ((crunchifyLine = crunchifyBufferReader.readLine()) != null) {
            	crunchifyLine=crunchifyLine.strip();
            	
                if (crunchifyAllLines.add(crunchifyLine)) {
                	System.out.println(crunchifyLine.length());
                    //crunchifyLog("Processed line: " + crunchifyLine);
                    String[] cfl=crunchifyLine.split(",");
                    writerex.writeNext(cfl);
                    
                } else  {
                	
                    //crunchifyLog("--------------- Skipped line: " + crunchifyLine);
                }
            }
            writerex.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (crunchifyBufferReader != null) {
                try {
                    crunchifyBufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
    // Check if String with spaces is Empty or Null
    public static boolean crunchifyIsNullOrEmpty(String crunchifyString) {
 
        if (crunchifyString != null && !crunchifyString.trim().isEmpty())
            return false;
        return true;
    }
 
    // Simple method for system outs
    private static void crunchifyLog(String s) {
 
        System.out.println(s);
    }
 
}