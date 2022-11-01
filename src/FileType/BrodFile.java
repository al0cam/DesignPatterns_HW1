package FileType;

import java.util.Arrays;
import java.util.List;

public class BrodFile implements FileType{

    @Override
    public List<Object> convertToObjects(List<String[]> fileContent) {
        List<Object> listOfObjects = null;
        for (String[] line : fileContent) {
            System.out.println(Arrays.toString(line)); 
        }
        return null;
    }


}
