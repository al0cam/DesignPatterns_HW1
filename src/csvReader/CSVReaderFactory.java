package csvReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
import FileType.BrodFile;
import FileType.FileType;
import FileType.KanalFile;
import FileType.LukaFile;
import FileType.MolFile;
import FileType.MolVezFile;
import FileType.RasporedFile;
import FileType.VezFile;
import FileType.ZahtjevRezervacijeFile;

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
            String[] firstLine = fileReader.readLine().split(";");
			while ((line = fileReader.readLine()) != null)
			{
                addToList = true;
				line = line.replace(",", ".");
				String[] tokens = line.split(";");
                for (String string : tokens)
                    if(string == null || string.equals(""))
                        addToList = false;
                if(addToList && tokens.length == firstLine.length)
				    fileContent.add(tokens);
			}

            if(isBrodFile(firstLine))
                fileType = new BrodFile(path, fileContent);
            else if(isLukaFile(firstLine))
                fileType = new LukaFile(path, fileContent);
            else if(isRasporedFile(firstLine))
                fileType = new RasporedFile(path, fileContent);
            else if(isVezFile(firstLine))
                fileType = new VezFile(path, fileContent);
            else if(isZahtjevRezervacijeFile(firstLine))
                fileType = new ZahtjevRezervacijeFile(path, fileContent);
            else if(isZahtjevMolFile(firstLine))
                fileType = new MolFile(path, fileContent);
            else if(isZahtjevKanalFile(firstLine))
                fileType = new KanalFile(path, fileContent);
            else if(isZahtjevMolVezFile(firstLine))
                fileType = new MolVezFile(path, fileContent);
		}
		catch (IOException e) {
            ErrorCatcherSingleton.getInstance().catchGeneralError(e);
        }
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

    private boolean isZahtjevMolFile(String[] firstRow)
    {
        return splitAndCompare(firstRow,"id_mol;naziv");
    }

    private boolean isZahtjevKanalFile(String[] firstRow)
    {
        return splitAndCompare(firstRow,"idKanal;frekvencija;maksimalanBroj");
    }

    private boolean isZahtjevMolVezFile(String[] firstRow)
    {
        return splitAndCompare(firstRow,"id_mol;id_vezovi");
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
