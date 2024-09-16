import java.io.*;

public class FileIOApp {
    public static void main(String[] args) {
        String inputFile = "simple-apps/file-io-app/input.txt";
        String outputFile = "simple-apps/file-io-app/output.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line.toUpperCase());
                writer.newLine();
            }
            System.out.println("File processing complete.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}