package FileType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.Raspored;

public class RasporedFile extends FileType {
    private List<Raspored> list;

    public RasporedFile(List<String[]> fileContent) {
        List<Raspored> listOfObjects = new ArrayList<Raspored>();
        for (String[] line : fileContent) {
            // System.out.println(Arrays.toString(line)); 

            try {
                List<Integer> daniUTjednuInt = new ArrayList<>();
                for (String dan : line[2].split("[.]")) {
                    daniUTjednuInt.add(Integer.parseInt(dan));
                }
                
                Raspored b = new Raspored(
                    Integer.parseInt(line[0]),
                    Integer.parseInt(line[1]),
                    daniUTjednuInt,
                    LocalTime.parse(line[3].trim(),DateTimeFormatter.ofPattern("H:mm")),
                    LocalTime.parse(line[4].trim(),DateTimeFormatter.ofPattern("H:mm"))
                );
                listOfObjects.add(b);
            } catch (Exception e) {
            }
        }
        list = listOfObjects;
    }
    
    public List<Raspored> getList()
    {
        return list;
    }

}
