package FileType;

import java.util.ArrayList;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import models.MolVez;

public class MolVezFile extends FileType {
    private List<MolVez> list;

    public MolVezFile(String path, List<String[]> fileContent) {
        List<MolVez> listOfObjects = new ArrayList<>();
        for (String[] line : fileContent) {
            try {
                List<Integer> vezovi = new ArrayList<>();
                for (String vez : line[1].split("[.]")) {
                    vezovi.add(Integer.parseInt(vez));
                }
                MolVez MolVez = new MolVez(
                    Integer.parseInt(line[0]),
                    vezovi
                );
                if(!molVezFaulty(MolVez, listOfObjects))
                    listOfObjects.add(MolVez);
               
            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().catchLineError(path,line,e);
            }
        }
        list = listOfObjects;
    }

    private boolean molVezFaulty(MolVez molVez, List<MolVez> listOfObjects) throws Exception
    {   
        for (MolVez molVez2 : listOfObjects) {
            if(molVez2.equals(molVez))
                throw new Exception("MolVez Id already exists");
            
            for (Integer vez : molVez2.getVezovi()) 
                if(molVez.getVezovi().contains(vez))
                    throw new Exception("Vez: "+vez+" cant be in multiple Mols at once");
        }

        return false;
    }

    @Override
	public List<MolVez> getList()
    {
        return list;
    }

}