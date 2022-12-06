package FileType;

import java.util.ArrayList;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import models.Kanal;

public class KanalFile extends FileType {
    private List<Kanal> list;

    public KanalFile(String path, List<String[]> fileContent) {
        List<Kanal> listOfObjects = new ArrayList<>();
        for (String[] line : fileContent) {
            try {
                Kanal kanal = new Kanal(
                    Integer.parseInt(line[0]),
                    Integer.parseInt(line[1]),
                    Integer.parseInt(line[2])
                );
                if(!kanalFaulty(kanal, listOfObjects))
                    listOfObjects.add(kanal);
               
            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().catchLineError(path,line,e);
            }
        }
        list = listOfObjects;
    }

    boolean kanalFaulty(Kanal kanal, List<Kanal> listOfObjects) throws Exception
    {   
        if(listOfObjects.contains(kanal))
            throw new Exception("Kanal Id already exists");
        
        for (Kanal kanal2 : listOfObjects) 
            if(kanal2.getFrekvencija().equals(kanal.getFrekvencija()))
                throw new Exception("Kanal frekvencija already in use");
        
        return false;
    }

    @Override
	public List<Kanal> getList()
    {
        return list;
    }

}
