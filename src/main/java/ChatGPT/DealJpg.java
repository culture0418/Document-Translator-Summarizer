package ChatGPT;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class DealJpg {
	private String filePath = "";
	private boolean isChecked = false;
	private String outputFilePath = "C:\\Users\\USER\\Documents\\workspace\\Java\\Java.FinalProject\\src\\test\\resources\\output.jpg";
	
	// constructor
	public DealJpg(String filePath) {
		this.filePath = filePath;
	}
	
    public String getJpgResponse(boolean[] fiveChecked) throws IOException, TesseractException {
    	String text = ocrGetText();
    	GoogleTranslate googleTranslate = new GoogleTranslate();
    	StringBuilder str = new StringBuilder();
    	
        if (fiveChecked[0]) {
        	String translateResponse = googleTranslate.getEnglishTranslateResponse(text);
        	str.append("[translate to English]\n");
        	str.append(translateResponse + "\n");
			str.append("\n");
        	this.isChecked=true;
        }
        
        if (fiveChecked[1]) {
        	String translateResponse = googleTranslate.getChineseTranslateResponse(text);
        	str.append("[translate to Chinese]\n");
        	str.append(translateResponse + "\n");
			str.append("\n");
        	this.isChecked=true;
        }
        if (fiveChecked[2]) {
        	ChatgptResponse chatgpt = new ChatgptResponse(text, "summary the text start with word \"Title\" and bullet points");
        	String chatgptResponse = chatgpt.getGptResponse();
        	str.append("[summary]\n");
        	str.append(chatgptResponse + "\n");
			str.append("\n");
        	this.isChecked=true;
        }
        if (fiveChecked[3]) {
        	ChatgptResponse chatgpt = new ChatgptResponse(text, "answer all the questions");
        	String chatgptResponse = chatgpt.getGptResponse();
        	str.append("[response]\n");
        	str.append(chatgptResponse + "\n");
			str.append("\n");
        	this.isChecked=true;
        }
        if (fiveChecked[4]) {
        	ChatgptResponse chatgpt = new ChatgptResponse(text, "If I want to find relevant information about this document, what kind of keywords should I use when searching on Google? (show them with number list) ");
        	String chatgptResponse = chatgpt.getGptResponse();
        	str.append("[more information]\n");
        	str.append(chatgptResponse + "\n");
			str.append("\n");
        	this.isChecked=true;
        }
        if (!this.isChecked) {
        	return text;
        }
        else {
        	return str.toString();
        }
    }
    
    private String ocrGetText() throws IOException, TesseractException {
    	
		File outputFile = new File(outputFilePath);
		File file = new File(filePath);
		String filename = file.getName();
		String extension = filename.substring(filename.lastIndexOf('.') + 1);
		
		//transfer every image to jpg file
		if(extension != "jpg")
		{
			BufferedImage inputimage = ImageIO.read(new File(filePath));
	        ImageIO.write(inputimage, "jpg", outputFile);
	        filePath = outputFilePath;
		}
		
		Tesseract tesseract = new Tesseract();
		tesseract.setDatapath("C:\\Users\\USER\\Documents\\workspace\\Java\\Java.FinalProject\\src\\test\\resources");
		//tesseract.setLanguage("chi_tra");
		tesseract.setTessVariable("user_defined_dpi", "70");
		String fulltext = tesseract.doOCR(new File(filePath));
		return fulltext;
		
	}

}
