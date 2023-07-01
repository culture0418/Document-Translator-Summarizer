package ChatGPT;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadTxt {
	
	private String filePath = "";
	
	// constructor
	public ReadTxt(String filePath) {
		this.filePath = filePath;
	}
	// get the file content
	public String readContent() throws FileNotFoundException {
		Scanner fileReader = new Scanner(new File(filePath));
		StringBuilder fileInput = new StringBuilder();
		while (fileReader.hasNext()) {
			fileInput.append(fileReader.nextLine());
		}
		fileReader.close();
		return fileInput.toString();
	}

}
