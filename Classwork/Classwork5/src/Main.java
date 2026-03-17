import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        //String[] command = new String[]{"curl", "-X", "POST", "https://postman-echo.com/post", "--data", "name=Moya"};
        //String[] command = new String[]{"cmd.exe", "/c", "dir"};
        //String[] command = new String[]{"whoami"};

        FileWriter finalText = new FileWriter("archivo.txt", true);

        System.out.print("Enter the file name or path: ");
        String filePath = scanner.nextLine();

        System.out.print("Write to which language change the file: ");
        String language = scanner.nextLine();


        String apiKey = System.getenv("OpenAIToken");  // read the variable OpenAIToken
        //System.out.println(apiKey);

        String fileText;
        try {
            fileText = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Error reading file");
            e.printStackTrace();
            return;
        }
        String text= "Could you change the following text to " + language + ": " + fileText;
        text = text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");


        String json = "{\"model\":\"gpt-4.1-mini\",\"input\":\"" + text + "\"}";
        Files.writeString(Paths.get("body.json"), json);
        String[] command = new String[]{
                "curl",
                "-X", "POST",
                "https://api.openai.com/v1/responses",
                "-H", "Content-Type: application/json",
                "-H", "Authorization: Bearer " + apiKey,
                "-sS",
                "--max-time", "60",
                "--data-binary", "@body.json"
        };



        String responseText;
        try{
            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            final Process process = builder.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            responseText = output.toString();

            System.out.println("Exit code: " + exitCode);


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }

        try{
            finalText = new FileWriter("archivo.txt");
            finalText.write(responseText);
            finalText.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Complete transduction");
        //System.out.println(responseText);


    }
}