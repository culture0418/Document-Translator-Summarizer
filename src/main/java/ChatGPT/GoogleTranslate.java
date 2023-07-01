package ChatGPT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class GoogleTranslate {
	
private String apiKey = "AIzaSyBfYJUeCEpIYFsrKDjZIaYRHNbnoRDKru8";
	
	// translate the English into Chinese
    public String getChineseTranslateResponse(String text) throws IOException {
    		String targetLanguage = "zh-TW";
            String encodedText = URLEncoder.encode(text, "UTF-8");
            String urlStr = "https://translation.googleapis.com/language/translate/v2?key=" + apiKey + "&q=" + encodedText + "&target=" + targetLanguage;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = "";
            String line;
            while ((line = in.readLine()) != null) {
                response += line;
            }
            in.close();

            // Extract the translated text from the JSON response
            String translatedText = response.split("\"translatedText\": \"")[1].split("\"")[0];
            
            return translatedText;
    }
    
    public String getEnglishTranslateResponse(String text) throws IOException {
		String targetLanguage = "en";
        String encodedText = URLEncoder.encode(text, "UTF-8");
        String urlStr = "https://translation.googleapis.com/language/translate/v2?key=" + apiKey + "&q=" + encodedText + "&target=" + targetLanguage;

        // Send the HTTP GET request
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String response = "";
        String line;
        while ((line = in.readLine()) != null) {
            response += line;
        }
        in.close();

        // Extract the translated text from the JSON response
        String translatedText = response.split("\"translatedText\": \"")[1].split("\"")[0];
        
        return translatedText;
    }

}
