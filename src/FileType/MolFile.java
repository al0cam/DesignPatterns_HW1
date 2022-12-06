package FileType;

import java.util.ArrayList;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import models.Mol;

public class MolFile extends FileType {
    private List<Mol> list;

    public MolFile(String path, List<String[]> fileContent) {
        List<Mol> listOfObjects = new ArrayList<>();
        for (String[] line : fileContent) {
            try {
                Mol b = new Mol(
                    Integer.parseInt(line[0]),
                    line[1]
                );
                listOfObjects.add(b);
               
            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().catchLineError(path,line,e);
            }
        }
        list = listOfObjects;
    }

    @Override
	public List<Mol> getList()
    {
        return list;
    }

}
