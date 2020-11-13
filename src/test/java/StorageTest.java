import utility.CsvHelper;
import org.testng.annotations.Test;
import storage.Node;
import storage.Storage;

import java.util.ArrayList;
import java.util.function.Predicate;

public class StorageTest {

    @Test(description = "Read data from CSV file")
    public void readCSV() {
        ArrayList<Node>values = CsvHelper.readCsv("src\\test\\testData\\testData.csv");
        Storage storage = new Storage();
        storage.addNodes(values);
        storage.printStorage();
    }

    @Test(description = "Output the lowest level hierarchy elements to a file in CSV format")
    public void checkOutputLeafs() {
        ArrayList<Node>values  = CsvHelper.readCsv("src\\test\\testData\\testData.csv");
        Storage storage = new Storage();
        storage.addNodes(values);
        Predicate leafDetector = (Object node) -> ((Node) node).getChilds().size() == 0;
        storage.printConditionToFile(leafDetector, "src\\test\\output\\result.csv");
    }

    @Test(description = "Output all groups of elements with names which are equal or consist of the same " +
            "letters to a file in CSV format")
    public void sameLetterGroups() {
        ArrayList<Node>values  = CsvHelper.readCsv("src\\test\\testData\\testData.csv");
        Storage storage = new Storage();
        storage.addNodes(values);
        storage.findSameGroups("src\\test\\output\\result1.csv");
    }
}
