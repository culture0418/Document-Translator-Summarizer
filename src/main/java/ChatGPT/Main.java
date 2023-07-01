package ChatGPT;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import net.sourceforge.tess4j.TesseractException;

public class Main extends Application{
	
	public static void main(String[] args) throws IOException, InterruptedException, TesseractException{
		launch(args);
	}
	
	@Override
    public void start(Stage primaryStage) {
        ReaderUI readerUI = new ReaderUI();
        readerUI.start(primaryStage);
    }
	
}
