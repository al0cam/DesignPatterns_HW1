package csvReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import FileType.BrodFile;
import FileType.FileType;

public class CSVReaderFactory {
    
    public <T> List<T> readFromCSV(String path)
    {
        FileType fileType = null;
        List<String[]> fileContent = null;
		try(
			BufferedReader fileReader = new BufferedReader(new FileReader(path))
        ){
			String line = "";
			fileContent = new ArrayList<>();
            boolean addToList = true;
			while ((line = fileReader.readLine()) != null)
			{
                addToList = true;
				line = line.replace(",", ".");
				String[] tokens = line.split(";");
                for (String string : tokens) 
                    if(string == null || string.equals(""))
                        addToList = false;
                if(addToList)
				    fileContent.add(tokens);
			}

            if(isBrodFile(fileContent.get(0)))
                fileType = new BrodFile(fileContent);
            else if(isLukaFile(fileContent.get(0)))
                fileType = new BrodFile(fileContent);
            else if(isRasporedFile(fileContent.get(0)))
                fileType = new BrodFile(fileContent);
            else if(isVezFile(fileContent.get(0)))
                fileType = new BrodFile(fileContent);   
            else if(isZahtjevRezervacijeFile(fileContent.get(0)))
                fileType = new BrodFile(fileContent);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("DEWIT");
        return fileType.getList();
    }



    private boolean isBrodFile(String[] firstRow)
    {
        return splitAndCompare(firstRow,"id;oznaka_broda;naziv;vrsta;duljina;sirina;gaz;maksimalna_brzina;kapacitet_putnika;kapacitet_osobnih_vozila;kapacitet_tereta");
    }

    private boolean isLukaFile(String[] firstRow)
    {
        return splitAndCompare(firstRow,"naziv;GPS_sirina;GPS_visina;dubina_luke;ukupni_broj_putnickih_vezova;ukupni_broj_poslovnih_vezova;ukupni_broj_ostalih_vezova;virtualno_vrijeme");
    }

    private boolean isRasporedFile(String[] firstRow)
    {
        return splitAndCompare(firstRow,"id_vez;id_brod;dani_u_tjednu;vrijeme_od;vrijeme_do");
    }

    private boolean isVezFile(String[] firstRow)
    {
        return splitAndCompare(firstRow,"id;oznaka_veza;vrsta;cijena_veza_po_satu;maksimalna_duljina;maksimalna_sirina;maksimalna_dubina");
    }

    private boolean isZahtjevRezervacijeFile(String[] firstRow)
    {
        return splitAndCompare(firstRow,"id_brod;datum_vrijeme_od;trajanje_priveza_u_h");
    }

    private boolean splitAndCompare(String[] firstRow, String idRow)
    {
        boolean equal = true;
        String[] idRowSplit = idRow.split(";");
        if(firstRow.length != idRowSplit.length)
        {
            equal = false;
        }
        else
        {
            for (int i=0; i<firstRow.length;i++) {
                if(!firstRow[i].equals(idRowSplit[i]))
                    equal = false;
            }
        }
        return equal;
    }
}
 