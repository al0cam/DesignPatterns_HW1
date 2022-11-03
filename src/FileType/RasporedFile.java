package FileType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import models.Raspored;

public class RasporedFile extends FileType {
    private List<Raspored> list;

    public RasporedFile(List<String[]> fileContent) {
        List<Raspored> listOfObjects = new ArrayList<>();
        for (String[] line : fileContent) {
            try {
                List<DayOfWeek> daniUTjednuInt = new ArrayList<>();
                for (String dan : line[2].split("[.]")) {

                    int number = Integer.parseInt(dan);
                    if(number == 0)
                        number = 7;
                    daniUTjednuInt.add(DayOfWeek.of(number));
                }

                Raspored b = new Raspored(
                    Integer.parseInt(line[0]),
                    Integer.parseInt(line[1]),
                    daniUTjednuInt,
                    LocalTime.parse(line[3].trim(),DateTimeFormatter.ofPattern("H:mm")),
                    LocalTime.parse(line[4].trim(),DateTimeFormatter.ofPattern("H:mm"))
                );
                listOfObjects.add(b);
            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().increaseErrorCountForLine(line,e);
            }
        }
        list = listOfObjects;
    }

    @Override
	public List<Raspored> getList()
    {
        return list;
    }

}
