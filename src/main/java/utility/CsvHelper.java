package utility;

import storage.Node;

import java.io.*;
import java.util.ArrayList;

public class CsvHelper {

    public static ArrayList<Node> readCsv(String file) {
        ArrayList<Node> arrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 2) {
                    throw new RuntimeException("file have invalid format - must be key-value format");
                }
                arrayList.add(new Node(values[0], values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void writeCsv(String fileName, StringBuilder sb) {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            writer.write(sb.toString());
            System.out.println("done!");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}