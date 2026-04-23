package APIs;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.google.genai.types.UploadFileConfig;

//import com.google.genai.types.ImageConfig;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Gemini extends Prompts{

    public static String describeVideo(String videoPath) throws Exception {
        Client client = Client.builder().apiKey(System.getenv("GEMINI_API_KEY")).build();

        var uploadedFile = client.files.upload(
                Path.of(videoPath).toFile(),
                UploadFileConfig.builder()
                        .mimeType("video/mp4")
                        .build()
        );
        System.out.println("name: " + uploadedFile.name().orElse("sin name"));
        System.out.println("uri: " + uploadedFile.uri().orElse("sin uri"));
        System.out.println("mime: " + uploadedFile.mimeType().orElse("sin mime"));

        // Esperar hasta que el archivo esté ACTIVE
        while (uploadedFile.state().isEmpty() ||
                !"ACTIVE".equals(uploadedFile.state().get().toString())) {

            System.out.println("Processing video...");
            System.out.println("state: " +
                    (uploadedFile.state().isPresent()
                            ? uploadedFile.state().get().toString()
                            : "sin estado"));

            Thread.sleep(5000);

            uploadedFile = client.files.get(
                    uploadedFile.name().orElseThrow(),null
            );
        }


        GenerateContentResponse response = client.models.generateContent(
                "gemini-3-flash-preview",
                List.of(
                        Content.fromParts(
                                Part.fromUri(
                                        uploadedFile.uri().get(),
                                        uploadedFile.mimeType().get()
                                ),
                                Part.fromText(descriveVideo)
                        )
                ),
                GenerateContentConfig.builder()
                        .responseMimeType("application/json")
                        .build()
        );

        return response.text();
    }

    public static void generateImage(String description, String outputPath, String p) throws Exception {
        try (Client client = Client.builder()
                .apiKey(System.getenv("GEMINI_API_KEY"))
                .build()) {

            System.out.println(client.apiKey());
            String prompt = switch (p) {
                case "initial" -> pInitialImage + description;
                case "final" -> pFinalImage + description;
                default -> "";
            };

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-3.1-flash-image-preview",
                    prompt,null
            );


            for (Part part : response.parts()) {
                if (part.inlineData().isPresent()) {
                    var blob = part.inlineData().get();
                    if (blob.data().isPresent()) {
                        Files.write(Path.of(outputPath), blob.data().get());
                        return;
                    }
                }
            }

            throw new RuntimeException("Gemini no devolvió ninguna imagen.");
        }
    }

}