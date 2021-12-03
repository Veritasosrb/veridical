package nlp;
import java.io.*;
 
public class test1{

public static void main(String a[]){
try{
 
String prg = "import sys\nprint(sys.argv[1])\n";
BufferedWriter out = new BufferedWriter(new FileWriter("trial5.py"));
out.write(prg);
out.close();
String number1 = "hi";


Process p = Runtime.getRuntime().exec("python trial5.py "+number1);
BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
String ret = in.readLine();
System.out.println("value is : "+ret);
}catch(Exception e){}
}
}