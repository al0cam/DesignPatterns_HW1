package FileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.Brod;

public class BrodFile extends FileType {
    private List<Brod> list;

    public BrodFile(List<String[]> fileContent) {
        List<Brod> listOfObjects = new ArrayList<Brod>();
        for (String[] line : fileContent) {
            // System.out.println(Arrays.toString(line)); 
            
            try {
                Brod b = new Brod(
                    Integer.parseInt(line[0]),
                    line[1],
                    line[2],
                    line[3],
                    Float.parseFloat(line[4]),
                    Float.parseFloat(line[5]),
                    Float.parseFloat(line[6]),
                    Float.parseFloat(line[7]),
                    Float.parseFloat(line[8]),
                    Float.parseFloat(line[9]),
                    Float.parseFloat(line[10])
                );
                listOfObjects.add(b);
            } catch (Exception e) {
                // TODO: write exception counter
            }
        }
        list = listOfObjects;
    }

    // @Override
    // public FileType getFileType(List<String[]> fileContent) {
    //     BrodFile brodFile = new BrodFile();
    //     List<Brod> listOfObjects = new ArrayList<Brod>();
    //     for (String[] line : fileContent) {
    //         System.out.println(Arrays.toString(line)); 
    //         Brod b = new Brod();
    //         listOfObjects.add(b);
    //     }
    //     brodFile.list = listOfObjects;
    //     return brodFile;
    // }
    
    public List<Brod> getList()
    {
        return list;
    }

}
