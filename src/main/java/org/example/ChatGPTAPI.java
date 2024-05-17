package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTAPI {
    public static String chatGPT(String userPrompt){
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "YOUR_API_KEY";
        String model = "gpt-3.5-turbo";

        final String SYSTEM_PROMPT = "You are a professional salesman who know exactly how to fight objections. The user will input 3 things. Product name, description of the product and the objection they are facing which you need to help with. To fight objections use real-life sales skills and persuasion. If possible try to go through 3 stages: empathization, clarification and argumentation. Start every stage from new line and with the prefix.";

        String prompt = "[{\"role\": \"system\", \"content\": \""+SYSTEM_PROMPT+"\"}," +
                "{\"role\": \"user\", \"content\": \""+userPrompt+"\"}]";

        try{

            // HTTP POST request
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // Request body
            String body = "{\"model\": \"" + model + "\", \"messages\": " + prompt +"}";

            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();
            while ((line = br.readLine()) != null ){
                response.append(line);
            }
            br.close();

            return extractMessageFromJSONResponse(response.toString());

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response){
        int start = response.indexOf("content")+11; // Index where the content starts
        int empathization = response.indexOf("Empathization"); // Index where the Empathization stage starts
        int clarification = response.indexOf("Clarification"); // Index where the Clarification stage starts
        int argumentation = response.indexOf("Argumentation"); // Index where the Argumentation stage starts
        int end = response.indexOf("\"", start); // Index where the content ends

        StringBuilder sb = new StringBuilder();
        sb.append(response, start, empathization);
        sb.append(response, empathization, clarification);
        sb.append(response, clarification, argumentation);
        sb.append(response, argumentation, end);
        return sb.toString().replaceAll("\\n", ""); // Returns the substring containing only the response
    }
}
