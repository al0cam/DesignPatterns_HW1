package CLI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ErrorCatcher.ErrorCatcherSingleton;
import csvReader.CSVReaderFactory;
import store.StoreSingleton;
import virtualTime.VirtualTimeSingleton;

public class CLISingleton {
    private static CLISingleton cliSingleton;

	private CLISingleton(){}
	
	public static CLISingleton getInstance()
	{
		if (cliSingleton == null)
		{
			cliSingleton = new CLISingleton();
		}
		return cliSingleton;
	}

    public void commandInterpreter()
    {
        Pattern pattern = Pattern.compile(
            "(?<bigGroup>(I$)|"+
            "(VR (?<datumIVrijeme>[0-3]?\\d\\.[0-1][0-9]\\.\\d{4}. \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9])$)|"+
            "(V (?<vrstaVeza>[A-Z]{2}) (?<status>[A-Z]) (?<datumVrijemeOd>[0-3]?\\d\\.[0-1][0-9]\\.\\d{4}. \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9]) (?<datumVrijemeDo>[0-3]?\\d\\.[0-1][0-9]\\.\\d{4}. \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9])$)|"+
            "(UR (?<nazivDatoteke>\\w+\\.csv)$)|"+
            "(ZD (?<idBrodZD>\\d+)$)|"+
            "(ZP (?<idBrodZP>\\d+) (?<trajanjeUSatima>\\d+)$)|"+
            "(Q$))"
            );

        boolean work = true;
        while(work)
        {
            String command = System.console().readLine();

            Matcher matcher = pattern.matcher(command);

            if (matcher.matches()) {
                VirtualTimeSingleton.getInstance().passTime();
                System.out.println("Virtual time: " + VirtualTimeSingleton.getInstance().virtualTimeToString());
                work = executeCommand(matcher);
            } 
            else {
                System.out.println("ERROR parametri su krivo uneseni");
            }
        }
    }
    

    private boolean executeCommand(Matcher command)
    {
        switch(command.group("bigGroup").split(" ")[0])
        {
            case "I":{
                
            }
            break;

            case "VR":{
                postaviVirtualnoVrijeme(command.group("datumIVrijeme"));
            }
            break;

            case "V":{
            }
            break;

            case "UR":{
		        ucitajDatoteku(command.group("nazivDatoteke"));
            }
            break;
            
            case "ZD":{
            }
            break;

            case "ZP":{
            }
            break;

            case "Q":{
                return false;
            }
        }
        return true;
    } 

    private void statusVezova()
    {
       
    }

    private void postaviVirtualnoVrijeme(String vrijemeIDatum)
    {
        VirtualTimeSingleton.getInstance().setVirtualtime(
            LocalDateTime.parse(vrijemeIDatum, DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss"))
        );
        System.out.println("Virtual time set; new virtual time: "+ VirtualTimeSingleton.getInstance().virtualTimeToString());
    }

    private void ucitajDatoteku(String naziv)
    {
        CSVReaderFactory csvReaderFactory = new CSVReaderFactory();

        try {
            StoreSingleton.getInstance().zahtjeviRezervacija = csvReaderFactory.readFromCSV(naziv);
        } catch (Exception e) {
            ErrorCatcherSingleton.getInstance().increaseErrorCountForGeneralError(e);
        }
    }



}
