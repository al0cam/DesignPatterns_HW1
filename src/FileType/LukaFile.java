package FileType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import models.Luka;

public class LukaFile extends FileType {
    private List<Luka> list;

    public LukaFile(List<String[]> fileContent) {
        List<Luka> listOfObjects = new ArrayList<Luka>();
        for (String[] line : fileContent) {
            // System.out.println(Arrays.toString(line)); 


            try {
                Luka b = new Luka(
                    line[0],
                    Float.parseFloat(line[1]),
                    Float.parseFloat(line[2]),
                    Float.parseFloat(line[3]),
                    Integer.parseInt(line[4]),
                    Integer.parseInt(line[5]),
                    Integer.parseInt(line[6]),
                    LocalDateTime.parse(line[7].trim(),DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss"))
                );
                listOfObjects.add(b);
            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().increaseErrorCount(line,e);
            }
        }
        list = listOfObjects;
    }
    
    public List<Luka> getList()
    {
        return list;
    }

}
