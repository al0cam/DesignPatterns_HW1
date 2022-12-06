package FileType;

import java.util.ArrayList;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import models.Brod;

public class BrodFile extends FileType {
    private List<Brod> list;

    public BrodFile(String path, List<String[]> fileContent) {
        List<Brod> listOfObjects = new ArrayList<>();
        for (String[] line : fileContent) {
            try {
                Brod b = new Brod(
                    Integer.parseInt(line[0]),
                    line[1],
                    line[2],
                    line[3],
                    Float.parseFloat(line[4]),
                    Float.parseFloat(line[5]),
                    Float.parseFloat(line[6]),
                    Float.parseFloat(line[7]),
                    Float.parseFloat(line[8]),
                    Float.parseFloat(line[9]),
                    Float.parseFloat(line[10])
                );
                
                
                if(!brodFaulty(b, listOfObjects))
                    listOfObjects.add(b);

            } catch (Exception e) {
                ErrorCatcherSingleton.getInstance().catchLineError("Brod", line,e);
            }
        }
        list = listOfObjects;
    }

    boolean brodFaulty(Brod b, List<Brod> listOfObjects) throws Exception
    {   
        if(
			b.getVrsta().equals("TR") || 
			b.getVrsta().equals("KA") || 
			b.getVrsta().equals("KL") || 
			b.getVrsta().equals("KR") || 
            b.getVrsta().equals("RI") || 
			b.getVrsta().equals("TE") ||
			b.getVrsta().equals("JA") || 
			b.getVrsta().equals("RO") || 
			b.getVrsta().equals("BR")
        ){
            for (Brod brod : listOfObjects) 
                if(brod.getId().equals(b.getId()))
                    throw new Exception("Brod Id already exists");
        }
        else throw new Exception("Brod vrsta is false");

        return false;
    }

    @Override
	public List<Brod> getList()
    {
        return list;
    }

}
