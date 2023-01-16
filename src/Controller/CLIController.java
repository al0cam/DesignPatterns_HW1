package Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ErrorCatcher.ErrorCatcherSingleton;
import Tablica.TablicaState;
import csvReader.CSVReaderFactory;
import models.Rezervacija;
import models.Vez;
import store.StoreSingleton;
import virtualTime.VirtualTimeSingleton;

// Singleton
public class CLIController {
    private static CLIController cliSingleton;

	private CLIController(){}

	public static CLIController getInstance()
	{
		if (cliSingleton == null)
		{
			cliSingleton = new CLIController();
		}
		return cliSingleton;
	}
    public void commandInterpreter()
    {
        Pattern pattern = Pattern.compile(
            "(?<bigGroup>(I$)|"+
            "(VR (?<datumIVrijeme>[0-3]\\d\\.[0-1][0-9]\\.\\d{4}. \\d\\d\\:[0-5][0-9]\\:[0-6][0-9])$)|"+
            "(V (?<vrstaVeza>[A-Z]{2}) (?<status>[A-Z]) (?<datumVrijemeOd>[0-3]\\d\\.[0-1][0-9]\\.\\d{4}. \\d\\d\\:[0-5][0-9]\\:[0-6][0-9]) (?<datumVrijemeDo>[0-3]\\d\\.[0-1][0-9]\\.\\d{4}. \\d\\d\\:[0-5][0-9]\\:[0-6][0-9])$)|"+
            "(UR (?<nazivDatoteke>\\w+\\.csv)$)|"+
            "(ZD (?<idBrodZD>\\d+)$)|"+
            "(ZP (?<idBrodZP>\\d+) (?<trajanjeUSatima>\\d+)$)|"+
            "(F (?<idBrodF>\\d+) (?<kanal>\\d+)(?<izlaz> Q)?$)|"+
            "(T(?<option1> [ZPRB]{1,2})?(?<option2> [ZPRB]{1,2})?(?<option3> [ZPRB]{1,2})?$)|"+
            "(ZA (?<datumVrijmeZA>[0-3]\\d\\.[0-1][0-9]\\.\\d{4}. [0-2]\\d\\:[0-5][0-9])$)|"+
            "(C (?<idBrodC>\\d+)$)|"+
            "(SPS \"(?<SPSNaziv>[a-zA-Z0-9\\s:]+)\"$)|"+
            "(VPS \"(?<VPSNaziv>[a-zA-Z0-9\s:]+)\"$)|"+
            "(Q$))"
            );

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
                radioKanali(
                    Integer.parseInt(command.group("idBrodF")),
                    Integer.parseInt(command.group("kanal")),
                    command.group("izlaz") != null
                );
            }
            break;

            case "T":{
                tablica(command.group("option1"),command.group("option2"),command.group("option3"));
            }
            break;

            case "ZA":{
                zauzetiVezoviPremaVrsti(command.group("datumVrijmeZA"));
            }
            break;

            case "C":{
                crtajBrod(Integer.parseInt(command.group("idBrodC")));
            }
            break;

            case "SPS":{
                spremiStanje(command.group("SPSNaziv"));
            }
            break;

            case "VPS":{
                vratiStanje(command.group("VPSNaziv"));
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
        List<String> content = new ArrayList<>();
        for (Vez vez : StoreSingleton.getInstance().getVezovi()) {
            content.add(
                String.format(
                    "%1$5s | %2$-7s |  %3$-5s |  %4$20s |  %5$20s | %6$20s | %7$20s | %8$-10s",
                    vez.getId(),vez.getOznaka(),vez.getVrsta(),vez.getCijenaVezaPoSatu(),
                    vez.getMaksimalnaDuljina(),vez.getMaksimalnaSirina(),vez.getMaksimalnaDubina(),
                    StoreSingleton.getInstance().vezZauzetUVrijeme(vez, VirtualTimeSingleton.getInstance().getVirtualtime())
                )    
            );
        }

        TablicaState.getInstance().print(
            "Ispis statusa vezova u trenutno vrijeme",
            String.format(
                    "%1$5s | %2$-7s |  %3$-5s |  %4$20s |  %5$20s | %6$20s | %7$20s | %8$-10s",
                    "id","oznaka","vrsta","cijenaVezaPoSatu","maksimalnaDuljina","maksimalnaSirina","maksimalnaDubina","zauzet"
                ),
            content
        );
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

    private void radioKanali(Integer idBrod, Integer frekvencija, boolean izlaz)
    {
        if(izlaz)
            StoreSingleton.getInstance().odSpajanjeSKanala(idBrod,frekvencija);
        else
            StoreSingleton.getInstance().spajanjeNaKanal(idBrod,frekvencija);
    }

    private void tablica(String option1, String option2, String option3)
    {
        TablicaState.getInstance().alterState(option1, option2, option3);
    }

    private void zauzetiVezoviPremaVrsti(String dateTime)
    {
        Map<String, Integer> map = StoreSingleton.getInstance().zauzetiVezoviPremaVrsti(dateTime);
        
        List<String> content = new ArrayList<>();
        for (Map.Entry<String,Integer> set : map.entrySet()) {
            content.add(
                String.format(
                    "%1$-5s |  %2$20s ",
                    set.getKey(),set.getValue()
                )    
            );
        }

        TablicaState.getInstance().print(
            "Ispis ukupnog broja zauzetih vezova prema vrsti",
            String.format(
                    "%1$-5s |  %2$20s ",
                    "vrsta","brojZauzetihVezova"
                ),
            content
        );
    }

    private void crtajBrod(Integer idBrod)
    {
        StoreSingleton.getInstance().crtajBrod(idBrod);
    }

    private void spremiStanje(String naziv)
    {
        StoreSingleton.getInstance().spremiStanje(naziv);
    }

    private void vratiStanje(String naziv)
    {
        StoreSingleton.getInstance().vratiStanje(naziv);
    }

}
