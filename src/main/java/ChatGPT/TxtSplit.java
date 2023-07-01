package ChatGPT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TxtSplit {
	
	public int splitTxt(String txtPath) throws IOException {
	     int maxSize = 4000; // 設定每個檔案的最大字元數
	     int count = 1;
	     StringBuilder sb = new StringBuilder(maxSize);
	     
	     try(BufferedReader reader = new BufferedReader(new FileReader(txtPath))){
	    	  String line;
	    	  while((line = reader.readLine()) != null) {
	    		  if(sb.length() + line.length() > maxSize) {
	    			  writePartFile(sb.toString(), count++);
	                  sb.setLength(0); // 重置 StringBuilder
	    		  }
	    		  sb.append(line);
	    	  }
	    	  if(sb.length() > 0) 
	    	  {
	                writePartFile(sb.toString(), count++);
	          }
	     }
	     return count;
	}
	
	
	private static void writePartFile(String content,int count) throws IOException {
		 String fileName = String.format("C:\\Users\\USER\\Documents\\workspace\\Java\\Java.FinalProject\\src\\test\\resources\\txts\\part-%04d.txt", count);
		  try(FileWriter writer = new FileWriter(fileName)) {
	            writer.write(content);
	        }
	}

}

