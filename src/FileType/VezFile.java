package FileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.Vez;

public class VezFile extends FileType {
    private List<Vez> list;

    public VezFile(List<String[]> fileContent) {
        List<Vez> listOfObjects = new ArrayList<Vez>();
        for (String[] line : fileContent) {
            // System.out.println(Arrays.toString(line)); 
            
            try {
                Vez b = new Vez(
                    Integer.parseInt(line[0]),
                    line[1],
                    line[2],
                    Integer.parseInt(line[3]),
                    Integer.parseInt(line[4]),
                    Integer.parseInt(line[5]),
                    Float.parseFloat(line[6])
                );
                listOfObjects.add(b);

            } catch (Exception e) {
            }
        }
        list = listOfObjects;
    }

    public List<Vez> getList()
    {
        return list;
    }

}
