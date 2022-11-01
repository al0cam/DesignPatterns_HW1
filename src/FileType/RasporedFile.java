package FileType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.Raspored;

public class RasporedFile extends FileType {
    private List<Raspored> list;

    public RasporedFile(List<String[]> fileContent) {
        List<Raspored> listOfObjects = new ArrayList<Raspored>();
        for (String[] line : fileContent) {
            System.out.println(Arrays.toString(line)); 
            Raspored b = new Raspored(
                Integer.parseInt(line[0]),
                Integer.parseInt(line[1]),
                Arrays.asList(Integer.parseInt(line[2])),
                LocalTime.parse(line[3]),
                LocalTime.parse(line[4])
            );
            listOfObjects.add(b);
        }
        list = listOfObjects;
    }
    
    public List<Raspored> getList()
    {
        return list;
    }

}
