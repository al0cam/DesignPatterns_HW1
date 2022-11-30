package FileType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import models.ZahtjevRezervacije;

public class ZahtjevRezervacijeFile extends FileType {
    private List<ZahtjevRezervacije> list;

    public ZahtjevRezervacijeFile(List<String[]> fileContent) {
        List<ZahtjevRezervacije> listOfObjects = new ArrayList<>();
        for (String[] line : fileContent) {
            try {
                ZahtjevRezervacije b = new ZahtjevRezervacije(
                    Integer.parseInt(line[0]),
                    LocalDateTime.parse(line[1],DateTimeFormatter.ofPattern("dd.MM.yyyy. H:mm:ss")),
                    Integer.parseInt(line[2])
                );
                listOfObjects.add(b);
            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().catchLineError(line,e);
            }
        }
        list = listOfObjects;
    }

    @Override
	public List<ZahtjevRezervacije> getList()
    {
        return list;
    }

}
