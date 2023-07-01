package ChatGPT;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatgptResponse {
	private String model1 = "text-davinci-003";
	private String model2 = "gpt-3.5-turbo";
	private int maxToken = 1000;
	private double temperature = 1.0;
	
	private String url = "https://api.openai.com/v1/chat/completions";
	private String token = "Bearer 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";
	
	private HttpURLConnection connection;
	
	public ChatgptResponse(String text, String prompt) {
		
        try {
        	
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", token);
            connection.setDoOutput(true);

            
            JSONObject data = new JSONObject();
            JSONArray messages = new JSONArray();
            JSONObject messages1 = new JSONObject();
            JSONObject messages2 = new JSONObject();
            messages1.put("role", "user");
            messages2.put("content",prompt + text);
            messages.put(messages1);
            messages.put(messages2);
            
            data.put("model", model2);
            //data.put("prompt", prompt + text);
            data.put("max_tokens", maxToken);
            data.put("temperature", temperature);
            //data.put("messages", messages);
            
           data.put("messages", new JSONArray().put(new JSONObject().put("role", "user").put("content", prompt + text)));
            

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.toString().getBytes());
            //outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // suppose input English text summary
    public String getGptResponse() {
        try {
            int responseCode = connection.getResponseCode();

            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            System.out.println("res onject" + response.toString());
            //String responseText = jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text");
            String responseText = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

            return responseText;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
}
