package CLI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            "(?<bigGroup>(?<I>I$)|"+
            "(?<VR>VR [0-3]?\\d\\.[0-1][0-9]\\.\\d{4}. \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9]$)|"+
            "(?<V>V [A-Z]{2} [A-Z] [0-3]?\\d\\.[0-1][0-9]\\.\\d{4}. \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9] [0-3]?\\d\\.[0-1][0-9]\\.\\d{4}. \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9]$)|"+
            "(?<UR>UR \\w+\\.csv$)|"+
            "(?<ZD>ZD \\d+$)|"+
            "(?<ZP>ZP \\d+ \\d+$)|"+
            "(?<Q>Q$))");

        boolean work = true;
        while(work)
        {
            String command = System.console().readLine();

            Matcher matcher = pattern.matcher(command);

            if (matcher.matches()) {
                System.out.println(matcher.group("bigGroup"));
                VirtualTimeSingleton.getInstance().passTime();
                System.out.println(VirtualTimeSingleton.getInstance().getVirtualtime().toString());
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
        System.out.println("Virtual time set; new virtual time: "+ VirtualTimeSingleton.getInstance().getVirtualtime().toString());
    }



}
