package FileType;

import java.util.ArrayList;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import models.Vez;

public class VezFile extends FileType {
    private List<Vez> list;

    public VezFile(List<String[]> fileContent) {
        List<Vez> listOfObjects = new ArrayList<>();
        for (String[] line : fileContent) {
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
                ErrorCatcherSingleton.getInstance().increaseErrorCountForLine(line,e);
            }
        }
        list = listOfObjects;
    }

    @Override
	public List<Vez> getList()
    {
        return list;
    }

}
