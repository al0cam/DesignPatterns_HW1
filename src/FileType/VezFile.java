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
            System.out.println(Arrays.toString(line)); 
            Vez b = new Vez();
            b.setNaziv(line[2]);
            listOfObjects.add(b);
        }
        list = listOfObjects;
    }


    // @Override
    // public FileType getFileType(List<String[]> fileContent) {
    //     VezFile VezFile = new VezFile();
    //     List<Vez> listOfObjects = new ArrayList<Vez>();
    //     for (String[] line : fileContent) {
    //         System.out.println(Arrays.toString(line)); 
    //         Vez b = new Vez();
    //         listOfObjects.add(b);
    //     }
    //     VezFile.list = listOfObjects;
    //     return VezFile;
    // }

    
    public List<Vez> getList()
    {
        return list;
    }

}
