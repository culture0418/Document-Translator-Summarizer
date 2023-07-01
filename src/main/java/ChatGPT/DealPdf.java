package ChatGPT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import net.sourceforge.tess4j.TesseractException;

public class DealPdf {
	private String filePath = "";
	private boolean isChecked = false;

	// constructor
	public DealPdf(String filePath) {
		this.filePath = filePath;
	}
	
    public String getPdfResponse(boolean[] fiveChecked) throws InvalidPasswordException, IOException, TesseractException {
  	  	//PDFtotxt
    	pdfToTxt();
		
		// split the text file
  	  	String txtPath = "C:\\Users\\USER\\Documents\\workspace\\Java\\Java.FinalProject\\src\\test\\resources\\txts\\translate2txt.txt";
  	  	TxtSplit split = new TxtSplit();
  	  	int txtFileNum = split.splitTxt(txtPath);
  	  	
  	  	GoogleTranslate googleTranslate = new GoogleTranslate();
  	  	
		StringBuilder translateStr = new StringBuilder();
		StringBuilder summaryStr = new StringBuilder();
		StringBuilder inforStr = new StringBuilder();
		StringBuilder responseStr = new StringBuilder();
		StringBuilder allStr = new StringBuilder();

			
		if (fiveChecked[0]) {
	  	  	translateStr.append("[translate to English]\n");
	  	  	for (int i = 1; i < txtFileNum; i++) {
	  	  		ReadTxt txtReader = new ReadTxt(String.format("C:\\Users\\USER\\Documents\\workspace\\Java\\Java.FinalProject\\src\\test\\resources\\txts\\part-%04d.txt", i));
	  	  		String text = txtReader.readContent();
	  	  		String translateResponse = googleTranslate.getEnglishTranslateResponse(text);
	  	  		translateStr.append(translateResponse + "\n");
	  	  	}
	  	  	translateStr.append("\n");
	  	  	translateStr.append("--------------------------------------------------\n");
	  	  	allStr.append(translateStr.toString());
	  	  	this.isChecked=true;
	  	}
		
		if (fiveChecked[1]) {
			translateStr.append("[translate to Chinese]\n");
		  	for (int i = 1; i < txtFileNum; i++) {
	  	    	ReadTxt txtReader = new ReadTxt(String.format("C:\\Users\\USER\\Documents\\workspace\\Java\\Java.FinalProject\\src\\test\\resources\\txts\\part-%04d.txt", i));
	  	    	String text = txtReader.readContent();
		  		String translateResponse = googleTranslate.getChineseTranslateResponse(text);
		  		translateStr.append(translateResponse + "\n");
		  	}
		    translateStr.append("\n");
		    translateStr.append("--------------------------------------------------\n");
	        allStr.append(translateStr.toString());
		    this.isChecked=true;
		 }
		 if (fiveChecked[2]) {
		     summaryStr.append("[summary]\n");
		     for (int i = 1; i < txtFileNum; i++) {
	  	    	 ReadTxt txtReader = new ReadTxt(String.format("C:\\Users\\USER\\Documents\\workspace\\Java\\Java.FinalProject\\src\\test\\resources\\txts\\part-%04d.txt", i));
	  	    	 String text = txtReader.readContent();
		      	 ChatgptResponse chatgpt = new ChatgptResponse(text, "summary the input text in Chinese(show them with number list)");
			     System.out.println("------------- ----------------- ------------");
			      	
		      	 String chatgptResponse = chatgpt.getGptResponse();
		      	 int startIndex = chatgptResponse.indexOf("Title");
		      	 if (startIndex == -1) {
		      		summaryStr.append(chatgptResponse + "\n");
		      		summaryStr.append("\n");
		      	 }
		      	 else {
		      		summaryStr.append(chatgptResponse.substring(startIndex) + "\n");
		      		summaryStr.append("\n");
		      	 }
		      	 try {
		      		 Thread.sleep(10000); // 使系统休眠100毫秒
		      		} catch (InterruptedException e) {
		      		    e.printStackTrace();
		      		}
		     }
		     summaryStr.append("\n");
		     summaryStr.append("--------------------------------------------------\n");
	         allStr.append(summaryStr.toString());
		     this.isChecked=true;
		 }
		 if (fiveChecked[3]) {
		 	responseStr.append("[response]\n");
		 	for (int i = 1; i < txtFileNum; i++) {
	  	 	  	ReadTxt txtReader = new ReadTxt(String.format("C:\\Users\\USER\\Documents\\workspace\\Java\\Java.FinalProject\\src\\test\\resources\\txts\\part-%04d.txt", i));
	  	 	  	String text = txtReader.readContent();
		 		ChatgptResponse chatgpt = new ChatgptResponse(text, "answer all questions in details");
		 		String chatgptResponse = chatgpt.getGptResponse();
		 		responseStr.append(chatgptResponse + "\n");
		 		
		 		try {
		  		    Thread.sleep(10000); // 使系统休眠100毫秒
		  		} catch (InterruptedException e) {
		  		    e.printStackTrace();
		  		}
		 	}
		  	responseStr.append("\n");
		  	responseStr.append("--------------------------------------------------\n");
	    	allStr.append(responseStr.toString());
		  	this.isChecked=true;
		 }
		 if (fiveChecked[4]) {
			 
			 inforStr.append("[more information]\n");
			 for (int i = 1; i < txtFileNum; i++) {
			   ReadTxt txtReader = new ReadTxt(String.format("C:\\Users\\USER\\Documents\\workspace\\Java\\Java.FinalProject\\src\\test\\resources\\txts\\part-%04d.txt", i));
		  	     String text = txtReader.readContent();
			     ChatgptResponse chatgpt = new ChatgptResponse(text, "If I want to find relevant information about this document, what kind of keywords should I use when searching on Google? (show them with number list) ");
			     String chatgptResponse = chatgpt.getGptResponse();
			     inforStr.append(chatgptResponse + "\n");
			     inforStr.append("\n");
			     try {
		    		   Thread.sleep(10000); // 使系统休眠100毫秒
		    	 } catch (InterruptedException e) {
		    		   e.printStackTrace();
		    	 }
			 }
			 inforStr.append("\n");
			 inforStr.append("--------------------------------------------------\n");
			 allStr.append(inforStr.toString());
			 this.isChecked=true;
		  }
	      if (!this.isChecked) {
	      	String text = "";
		  	for (int i = 1; i < txtFileNum; i++) {
	  	  	  	ReadTxt txtReader = new ReadTxt(String.format("C:\\Users\\USER\\Documents\\workspace\\Java\\Java.FinalProject\\src\\test\\resources\\txts\\part-%04d.txt", i));
	  	  	  	text = txtReader.readContent();
		  	}
	      	return text;
	      }
	      else {
		  	return allStr.toString();
	      }
  	  	

    }
    
    private void pdfToTxt() throws IOException {
    	String storePath = "C:\\Users\\USER\\Documents\\workspace\\Java\\Java.FinalProject\\src\\test\\resources\\txts\\translate2txt.txt";
		File pdffile = new File(filePath);
		PDDocument pddocument = PDDocument.load(pdffile); 
		PDFTextStripper pdfstripper = new PDFTextStripper();
		String pdftext = pdfstripper.getText(pddocument);
		pddocument.close();
		
		//write into text file
		BufferedWriter bw = new BufferedWriter(new FileWriter(storePath));
		bw.write(pdftext);
		bw.close();
    }

}
