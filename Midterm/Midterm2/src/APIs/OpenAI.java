package APIs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class OpenAI extends Prompts{

    public static void generateImage(String description, String outputPath,String p)
            throws IOException, InterruptedException {

        String apiKey = System.getenv("OpenAI_Token");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("No se encontró OPENAI_API_KEY en las variables de entorno.");
        }
        System.out.println(apiKey);
        String prompt = switch (p) {
            case "initial" -> pInitialImage + description;
            case "final" -> pFinalImage + description;
            default -> "";
        };

        String requestBody = """
                {
                  "model": "gpt-image-2",
                  "prompt": %s
                }
                """.formatted(asJsonString(prompt));

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/images/generations"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error OpenAI " + response.statusCode() + ": " + response.body());
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        String base64Image = root.path("data").get(0).path("b64_json").asText();
        if (base64Image == null || base64Image.isBlank()) {
            throw new RuntimeException("OpenAI no devolvió imagen.");
        }

        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        Path path = Path.of(outputPath);
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        Files.write(path, imageBytes);
    }


    public static String generatePhrase(String description)
            throws IOException, InterruptedException {

        String apiKey = System.getenv("OpenAI_Token");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("OPENAI_API_KEY was not found.");
        }

        String prompt = phrase+ description;

        String requestBody = """
                {
                  "model": "gpt-5.4-mini",
                  "input": %s
                }
                """.formatted(asJsonString(prompt));

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/responses"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error OpenAI " + response.statusCode() + ": " + response.body());
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        String text = root.path("output_text").asText();
        if (text == null || text.isBlank()) {
            throw new RuntimeException("OpenAI no devolvió texto.");
        }

        return text.trim();
    }

    public static void generateSpeech(String text, String outputPath, String phrase)
            throws IOException, InterruptedException {

        String apiKey = System.getenv("OpenAI_Token");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("No se encontró OPENAI_API_KEY.");
        }
        String prompt = """
        Based on the following description, write a continuous and natural narration in Spanish.
        Do not mention that it comes from segments or from JSON.
        The narration must sound fluid and cohesive, as if it were a single story.
        The narration must end with the following phrase: """ + phrase + """

        Description:
        """ + text;

                String requestBody = """
                {
                  "model": "gpt-4o-mini-tts",
                  "voice": "alloy",
                  "input": %s,
                  "format": "mp3"
                }
                """.formatted(asJsonString(prompt));

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/audio/speech"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error OpenAI " + response.statusCode() + ": " + new String(response.body()));
        }

        Path path = Path.of(outputPath);
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        Files.write(path, response.body());
    }

    private static String asJsonString(String text) {
        return "\"" + text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t") + "\"";
    }

}