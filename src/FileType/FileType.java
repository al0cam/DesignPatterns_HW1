package FileType;

import java.util.List;

public abstract class FileType {
    // public abstract FileType getFileType(List<String[]> fileContent);
    public abstract <T> List<T> getList();
}
