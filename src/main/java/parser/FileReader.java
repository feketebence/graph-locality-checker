package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    private final String inputFilePath;

    public FileReader(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String readFile() {
        StringBuilder inputBuilder = new StringBuilder();

        try {
            File inputFile = new File(this.inputFilePath);
            Scanner scanner = new Scanner(inputFile);

            while(scanner.hasNextLine()) {
                inputBuilder.append(scanner.nextLine()).append("\n");
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File " + this.inputFilePath + "not found");
            e.printStackTrace();
        }

        return inputBuilder.toString();
    }
}
