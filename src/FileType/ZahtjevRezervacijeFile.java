package FileType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.ZahtjevRezervacije;

public class ZahtjevRezervacijeFile extends FileType {
    private List<ZahtjevRezervacije> list;

    public ZahtjevRezervacijeFile(List<String[]> fileContent) {
        List<ZahtjevRezervacije> listOfObjects = new ArrayList<ZahtjevRezervacije>();
        for (String[] line : fileContent) {
            System.out.println(Arrays.toString(line)); 
            ZahtjevRezervacije b = new ZahtjevRezervacije(
                Integer.parseInt(line[0]),
                LocalDateTime.parse(line[1]),
                Integer.parseInt(line[2])
            );
            listOfObjects.add(b);
        }
        list = listOfObjects;
    }

    public List<ZahtjevRezervacije> getList()
    {
        return list;
    }

}
