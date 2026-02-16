import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        Path input1 = Path.of("src/Text/diagonal");
        Path output1 = Path.of("diagonal.svg");

        Path input2 = Path.of("src/Text/senAndSun");
        Path output2 = Path.of("sun.svg");


        String code1 = Files.readString(input1, StandardCharsets.UTF_8);
        String code2 = Files.readString(input2, StandardCharsets.UTF_8);
        try{

            Files.writeString(output1,code1,StandardCharsets.UTF_8);
            Files.writeString(output2,code2,StandardCharsets.UTF_8);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}