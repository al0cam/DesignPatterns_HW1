package FileType;

import java.util.ArrayList;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import models.Luka;
import models.Vez;
import store.StoreSingleton;

public class VezFile extends FileType {
    private List<Vez> list;

    public VezFile(String path, List<String[]> fileContent) {
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

                if(!vezFaulty(b, listOfObjects))
                    listOfObjects.add(b);
               
            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().catchNonVTError(e);;
            }
        }
        list = listOfObjects;
    }

    boolean vezFaulty(Vez v, List<Vez> listOfObjects) throws Exception
    {   
        if(
			v.getVrsta().equals("PU") || 
			v.getVrsta().equals("PO") || 
			v.getVrsta().equals("OS")
        ){
            for (Vez vez : listOfObjects) 
                if(vez.getId().equals(v.getId()))
                    throw new Exception("Vez Id already exists");
        }
        else throw new Exception("Vez vrsta is false");


        Luka luka = StoreSingleton.getInstance().getLuka();
        if(luka.getDubinaLuke() < v.getMaksimalnaDubina())
            throw new Exception("Vez dubina: "+v.getMaksimalnaDubina()+"  is bigger than Luka dubina:"+luka.getDubinaLuke());
            
        return false;
    }

    @Override
	public List<Vez> getList()
    {
        return list;
    }

}
