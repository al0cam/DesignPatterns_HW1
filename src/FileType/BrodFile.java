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
            System.out.println(Arrays.toString(line)); 
            Brod b = new Brod();
            b.setNaziv(line[2]);
            listOfObjects.add(b);
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
