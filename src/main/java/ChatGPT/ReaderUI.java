package ChatGPT;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.animation.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.sourceforge.tess4j.TesseractException;


public class ReaderUI {
	private TextArea resultTextArea;
    private CheckBox translate2ECheckBox;
    private CheckBox translate2CCheckBox;
    private CheckBox summarizeCheckBox;
    private CheckBox moreInfoCheckBox;
    private CheckBox responseCheckBox;
    private Slider fontSizeSlider;
    private Text fontSizeText;    
    private String dragDropString;
    private File selectedFile;
    private Text dragDropText;
    
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("txt", "jpg", "pdf");  ////
    
    public void start(Stage primaryStage) {

        // 拖曳檔案區域
        VBox dragDropArea = new VBox();
        dragDropArea.setStyle("-fx-border-style: dashed; -fx-border-color: lightgrey; -fx-border-width: 3;");
        dragDropArea.setMinSize(200, 150);
        dragDropArea.setAlignment(Pos.CENTER);

        dragDropArea.setOnDragOver(event -> {
            if (event.getGestureSource() != dragDropArea && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        dragDropArea.setOnDragDropped(event -> {  // 限制可以進來的檔案
            var dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasFiles()) {
                File file = dragboard.getFiles().get(0);
                String fileExtension = getFileExtension(file);
                if (ALLOWED_EXTENSIONS.contains(fileExtension)) {
                    selectedFile = file;
                    dragDropString=selectedFile.getName(); 
                    dragDropText.setText(dragDropString); 
                    success = true;
                } else {
                    showAccessDeniedMessage();
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // 拖曳檔案文字
        dragDropString="Drag and drop files here\n (txt, jpg, pdf)"; ////
        dragDropText = new Text(dragDropString); ////
        dragDropText = new Text("Drag and drop files here\n (txt, jpg, pdf)");
        dragDropText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        dragDropText.setTextAlignment(TextAlignment.CENTER);
        dragDropText.wrappingWidthProperty().bind(dragDropArea.widthProperty().subtract(20));
        dragDropArea.getChildren().addAll(dragDropText);

        // 其他按鈕和選項
        translate2ECheckBox = new CheckBox("Translate to English");
        translate2CCheckBox = new CheckBox("Translate to Chinese");
        summarizeCheckBox = new CheckBox("Summarize");
        responseCheckBox = new CheckBox("Response");   ////
        moreInfoCheckBox = new CheckBox("More Information");
        
        
        translate2ECheckBox.setOnAction(event -> {
            if (translate2ECheckBox.isSelected()) {
            	translate2CCheckBox.setSelected(false);
            }
        });

        translate2CCheckBox.setOnAction(event -> {
            if (translate2CCheckBox.isSelected()) {
            	translate2ECheckBox.setSelected(false);
            }
        });
        
        VBox AllCheckBox = new VBox(25);
        AllCheckBox.setAlignment(Pos.CENTER_LEFT);
        AllCheckBox.getChildren().addAll(translate2ECheckBox, translate2CCheckBox, summarizeCheckBox, responseCheckBox, moreInfoCheckBox);

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitButton.setMinWidth(120);
        submitButton.setOnAction(e -> {
        	try {
        		String extension = getFileExtension(selectedFile);
            	String filePath = selectedFile.getAbsolutePath();
            	String result = "";
            	boolean[] fiveChecked = new boolean[5];
            	fiveChecked[0] = translate2ECheckBox.isSelected();
            	fiveChecked[1] = translate2CCheckBox.isSelected();
            	fiveChecked[2] = summarizeCheckBox.isSelected();
            	fiveChecked[3] = responseCheckBox.isSelected();
            	fiveChecked[4] = moreInfoCheckBox.isSelected();

            	if (extension.equalsIgnoreCase("jpg")) {
            		DealJpg Jpg = new DealJpg(filePath);
        			result = Jpg.getJpgResponse(fiveChecked);
            	}
            	
            	if (extension.equalsIgnoreCase("txt")) {
            		DealTxt txt = new DealTxt(filePath);
            		result = txt.getTxtResponse(fiveChecked);
            	}
            	
            	if (extension.equalsIgnoreCase("pdf")) {
            		DealPdf pdf = new DealPdf(filePath);
            		result = pdf.getPdfResponse(fiveChecked);
            	}
            	resultTextArea.setText(result);
				
			} catch (IOException | TesseractException e1) {
				e1.printStackTrace();
			}
 
        });
        
        // 滑塊調整字體大小
        fontSizeText=new Text("Font Size");
        fontSizeSlider = new Slider(10, 30, 12); // 最小值、最大值、初始值
        fontSizeSlider.setShowTickMarks(true);
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.setMajorTickUnit(2);
        fontSizeSlider.setBlockIncrement(2);
        fontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
        	resultTextArea.setStyle("-fx-font-size: " + newValue.intValue() + ";");
        });
        
        VBox AllSlider = new VBox(10);
        AllSlider.setAlignment(Pos.CENTER);
        AllSlider.getChildren().addAll(fontSizeText, fontSizeSlider);
        
        // 左邊區域
        VBox leftBox = new VBox(30);
        leftBox.setPadding(new Insets(30));
        leftBox.setAlignment(Pos.CENTER);
        leftBox.getChildren().addAll(dragDropArea, AllCheckBox, AllSlider, submitButton);
        
        // 右邊區域
        resultTextArea = new TextArea();
        resultTextArea.setEditable(false);
        resultTextArea.setWrapText(true); // 自動換行

        ScrollPane scrollPane = new ScrollPane(resultTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // 禁用水平滾動軸
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // 根據需要顯示垂直滾動軸
        
        // 主佈局
        BorderPane root = new BorderPane();
        root.setLeft(leftBox);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("My Reader"); //標題
    }
    
    private String getFileExtension(File file) {
        String extension = "";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        return extension;
    }
    
    
    // 留著
    private void showAccessDeniedMessage() {
        dragDropText.setText("Access denied");
        dragDropText.setFill(Color.RED);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
        	dragDropText.setText(dragDropString);
            dragDropText.setFill(Color.BLACK);
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }
}
