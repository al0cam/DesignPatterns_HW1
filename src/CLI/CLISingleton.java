package CLI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ErrorCatcher.ErrorCatcherSingleton;
import Vrsta.Trajekt;
import Vrsta.VrstaHandler;
import csvReader.CSVReaderFactory;
import models.Rezervacija;
import models.Vez;
import store.StoreSingleton;
import virtualTime.VirtualTimeSingleton;

public class CLISingleton {
public static CLISingleton cliSingleton;
    private boolean header = false;
    private boolean footer = false;
    private boolean ordinalNumbers = false;



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
        // Pattern pattern = Pattern.compile(
        //     "(?<bigGroup>(I$)|"+
        //     "(VR (?<datumIVrijeme>[0-3]\\d\\.[0-1][0-9]\\.\\d{4}. \\d\\d\\:[0-5][0-9]\\:[0-6][0-9])$)|"+
        //     "(V (?<vrstaVeza>[A-Z]{2}) (?<status>[A-Z]) (?<datumVrijemeOd>[0-3]\\d\\.[0-1][0-9]\\.\\d{4}. \\d\\d\\:[0-5][0-9]\\:[0-6][0-9]) (?<datumVrijemeDo>[0-3]\\d\\.[0-1][0-9]\\.\\d{4}. \\d\\d\\:[0-5][0-9]\\:[0-6][0-9])$)|"+
        //     "(UR (?<nazivDatoteke>\\w+\\.csv)$)|"+
        //     "(ZD (?<idBrodZD>\\d+)$)|"+
        //     "(ZP (?<idBrodZP>\\d+) (?<trajanjeUSatima>\\d+)$)|"+
        //     "(Q$))"
        //     );
        Pattern pattern = Pattern.compile(
            "(?<bigGroup>(I$)|"+
            "(VR (?<datumIVrijeme>[0-3]\\d\\.[0-1][0-9]\\.\\d{4}. \\d\\d\\:[0-5][0-9]\\:[0-6][0-9])$)|"+
            "(V (?<vrstaVeza>[A-Z]{2}) (?<status>[A-Z]) (?<datumVrijemeOd>[0-3]\\d\\.[0-1][0-9]\\.\\d{4}. \\d\\d\\:[0-5][0-9]\\:[0-6][0-9]) (?<datumVrijemeDo>[0-3]\\d\\.[0-1][0-9]\\.\\d{4}. \\d\\d\\:[0-5][0-9]\\:[0-6][0-9])$)|"+
            "(UR (?<nazivDatoteke>\\w+\\.csv)$)|"+
            "(ZD (?<idBrodZD>\\d+)$)|"+
            "(ZP (?<idBrodZP>\\d+) (?<trajanjeUSatima>\\d+)$)|"+
            "(F (?<idBrodF>\\d+) (?<kanal>\\d+)$)|"+
            "(T(?<option1> [ZPRB]{1,2})?(?<option2> [ZPRB]{1,2})?(?<option3> [ZPRB]{12})?$)|"+
            "(ZA (?<datumVrijmeZA>[0-3]\\d\\.[0-1][0-9]\\.\\d{4}. [0-2]\\d\\:[0-5][0-9])$)|"+
            "(C (?<idBrodC>\\d+)$)|"+
            "(Q$))"
            );

            // TODO: slozit chain of responsibility koji crta brodove prema vrsti a uzima id broda kao arg tj objekt broda
        boolean work = true;
        while(work)
        {
            System.out.println("\nVirtual time: " + VirtualTimeSingleton.getInstance().virtualTimeToString() +" Day: "+VirtualTimeSingleton.getInstance().getVirtualtime().getDayOfWeek());
            System.out.print("Command: ");
            String command = System.console().readLine();

            Matcher matcher = pattern.matcher(command);

            if (matcher.matches()) {
                VirtualTimeSingleton.getInstance().passTime();
                System.out.println();
                work = executeCommand(matcher);
            }
            else {
                ErrorCatcherSingleton.getInstance().catchCustomError("ERROR parametri su krivo uneseni");
            }
        }
    }


    private boolean executeCommand(Matcher command)
    {
        switch(command.group("bigGroup").split(" ")[0])
        {
            case "I":{
                statusVezova();
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
                priveziNaRezervirano(Integer.parseInt(command.group("idBrodZD")));
            }
            break;

            case "ZP":{
                stvoriZahtjev(Integer.parseInt(command.group("idBrodZP")),Integer.parseInt(command.group("trajanjeUSatima")));
            }
            break;

            case "F":{
            }
            break;

            case "T":{
            }
            break;

            case "ZA":{
            }
            break;

            case "C":{
                crtajBrod(Integer.parseInt(command.group("idBrodC")));
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
        System.out.println(String.format("%130s","").replace(" ","_"));
        System.out.println("\nTablica - Ispis statusa vezova u trenutno vrijeme");
        System.out.println(String.format("%130s","").replace(" ","_"));
        System.out.println(
            String.format(
                "%1$5s | %2$-7s |  %3$-5s |  %4$20s |  %5$20s | %6$20s | %7$20s | %8$-10s",
                "id","oznaka","vrsta","cijenaVezaPoSatu","maksimalnaDuljina","maksimalnaSirina","maksimalnaDubina","zauzet"
            )
        );
        for (Vez vez : StoreSingleton.getInstance().getVezovi()) {
            System.out.println(
            String.format(
                "%1$5s | %2$-7s |  %3$-5s |  %4$20s |  %5$20s | %6$20s | %7$20s | %8$-10s",
                vez.getId(),vez.getOznaka(),vez.getVrsta(),vez.getCijenaVezaPoSatu(),
                vez.getMaksimalnaDuljina(),vez.getMaksimalnaSirina(),vez.getMaksimalnaDubina(),
                StoreSingleton.getInstance().vezZauzetUVrijeme(vez, VirtualTimeSingleton.getInstance().getVirtualtime())
            )
        );
        }
        System.out.println(String.format("%130s","").replace(" ","_"));
        System.out.println("\nTotal: "+StoreSingleton.getInstance().getVezovi().size());
        System.out.println(String.format("%130s","").replace(" ","_"));
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
            StoreSingleton.getInstance().zahtjeviURezervacije();
        } catch (Exception e) {
            ErrorCatcherSingleton.getInstance().catchGeneralError(e);
        }

        System.out.println("Ucitani zapisi");
        for (Rezervacija rezervacija : StoreSingleton.getInstance().getRezervacije()) {
            System.out.println("Brod: "+rezervacija.getBrod().getId() +" Vez:" + rezervacija.getVez().getId() +" " + StoreSingleton.getInstance().timeToString(rezervacija.getDatumVrijemeOd()) +" " + StoreSingleton.getInstance().timeToString(rezervacija.getDatumVrijemeDo()));
        }
    }


    private void stvoriZahtjev(Integer idBrod, Integer trajanjeUSatima)
    {
        StoreSingleton.getInstance().novaRezervacija(idBrod, trajanjeUSatima);
    }

    private void priveziNaRezervirano(Integer idBrod)
    {
        StoreSingleton.getInstance().priveziBrodSRezervacijom(idBrod);
    }

    private void crtajBrod(Integer idBrod)
    {
        StoreSingleton.getInstance().crtajBrod(idBrod);
    }
}
