import Controllers.Total;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class Tests {
    private Total total = new Total();

    @Test
    public void getFile() throws FileNotFoundException {
        List<String> testList = Arrays.asList("Anton 54",
                                                   "Vasya 32",
                                                   "Victor 37");

        assertEquals(testList, total.getFile(new File("src\\Profiles\\TestRecord")));
    }

    @Test
    public void setRecord() throws IOException {
        List<String> inputList1 = Arrays.asList("Pavel 1", "Fedot 7", "Anton 54", "Victor 233", "Anton 222",
                "Anton 222", "Anton 222", "Anton 222", "Anton 222", "Anton 222");
        List<String> inputList2 = Arrays.asList("Pavel 1", "Fedot 7", "Anton 54", "Victor 233", "Anton 222",
                "Anton 222", "Anton 222", "Anton 222", "Anton 222", "Anton 222");


        List<String> outList1 = Arrays.asList("Fedot 7", "SadDragon 22", "Anton 54", "Victor 233", "Anton 222",
                "Anton 222", "Anton 222", "Anton 222", "Anton 222", "Anton 222");
        List<String> outList2 = Arrays.asList("Fedot 7", "Anton 54", "Victor 233", "Anton 222",
                "Anton 222", "Anton 222", "Anton 222", "Anton 222", "Anton 222", "SadDragon 777");
        assertEquals(outList1, total.setRecord("SadDragon", 22, inputList1));
        assertEquals(outList2, total.setRecord("SadDragon", 777, inputList2));
    }

    @Test
    public void setFile() throws IOException {
        List<String> inputList = Arrays.asList("Pavel 1", "Fedot 7", "Anton 54", "Victor 233", "Anton 222",
                "Anton 222", "Anton 222", "Anton 222", "Anton 222", "Anton 222");
        total.setFile(inputList);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src\\Profiles\\Record")));
        List<String> outList = bufferedReader.lines().collect(Collectors.toList());
        assertEquals(outList, inputList);
    }

    @Test
    public void checkrecord(){
        List<String> inputList = Arrays.asList("Pavel 1", "Fedot 7", "Anton 54", "Victor 233", "Anton 222",
                "Anton 222", "Anton 222", "Anton 222", "Anton 222", "Anton 222");
        assertTrue(total.checkRecord(700, inputList));
        assertFalse(total.checkRecord(1, inputList));
    }

}
