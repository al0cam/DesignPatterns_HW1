package FileType;

import java.util.ArrayList;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import models.Brod;

public class BrodFile extends FileType {
    private List<Brod> list;

    public BrodFile(List<String[]> fileContent) {
        List<Brod> listOfObjects = new ArrayList<>();
        for (String[] line : fileContent) {
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
                ErrorCatcherSingleton.getInstance().increaseErrorCountForLine(line,e);
            }
        }
        list = listOfObjects;
    }

    @Override
	public List<Brod> getList()
    {
        return list;
    }

}
