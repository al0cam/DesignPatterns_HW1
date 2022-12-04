package FileType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import models.Brod;
import models.Raspored;
import models.Vez;
import store.StoreSingleton;

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
                    StoreSingleton.getInstance().getVezById(Integer.parseInt(line[0])),
                    StoreSingleton.getInstance().getBrodById(Integer.parseInt(line[1])),
                    daniUTjednuInt,
                    LocalTime.parse(line[3].trim(),DateTimeFormatter.ofPattern("H:mm")),
                    LocalTime.parse(line[4].trim(),DateTimeFormatter.ofPattern("H:mm"))
                );
                if(!rasporedFaulty(b))
                    listOfObjects.add(b);
            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().catchLineError(line,e);
            }
        }
        list = listOfObjects;
    }

    boolean rasporedFaulty(Raspored b) throws Exception
    {   
        boolean brodExists = false;
        boolean vezExists = false;

        for (Brod brod : StoreSingleton.getInstance().getBrodovi()) 
            if(brod.getId().equals(b.getBrod().getId()))
            {
                brodExists = true;
                break;
            }

        for (Vez vez : StoreSingleton.getInstance().getVezovi()) 
            if(vez.getId().equals(b.getVez().getId()))
            {
                vezExists = true;
                break;
            }

        if(!brodExists && !vezExists)
            throw new Exception("Vez: "+b.getVez().getId()+" and brod: "+b.getBrod().getId()+" dont exist");
        else if(brodExists && !vezExists)
            throw new Exception("Vez: "+b.getVez().getId()+" doesnt exist");
        else if(vezExists && !brodExists)
            throw new Exception("Brod: "+b.getBrod().getId()+" doesnt exist");
        else return false;
    }

    @Override
	public List<Raspored> getList()
    {
        return list;
    }

}
