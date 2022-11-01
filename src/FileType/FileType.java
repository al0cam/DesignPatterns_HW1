package FileType;

import java.util.List;

public interface FileType {
    List<Object> convertToObjects(List<String[]> fileContent);
}
