package main;

import java.util.List;

import csvReader.CSVReaderFactory;
import models.Brod;
import models.Luka;
import models.Raspored;
import models.Vez;
import models.ZahtjevRezervacije;

public class Main {

	public static void main(String[] args) {
		// CSVReaderSingleton.getInstance().readFromCSV("./src/CSV/DZ_1_brod.csv");

		int i = 0;

		CSVReaderFactory csvReaderFactory = new CSVReaderFactory();
		List<Brod> brodovi = csvReaderFactory.readFromCSV("./src/CSV/DZ_1_brod.csv");
		for (Brod brod : brodovi) {
			// System.out.println(brod.getNaziv());
			i++;
		}
		System.out.println("BRODOVI: "+ i);
		i=0;
		
		List<Luka> luke = csvReaderFactory.readFromCSV("./src/CSV/DZ_1_luka.csv");
		for (Luka brod : luke) {
			// System.out.println(brod.getNaziv());
			i++;
		}
		System.out.println("luke: "+ i);
		i=0;
		
		List<Raspored> rasporedi = csvReaderFactory.readFromCSV("./src/CSV/DZ_1_raspored.csv");
		for (Raspored brod : rasporedi) {
			e.printStackTrace();
			// System.out.println(brod.getDaniUtjednu());
			i++;
		}
		System.out.println("rasporedi: "+ i);
		i=0;
		
		List<Vez> vezovi = csvReaderFactory.readFromCSV("./src/CSV/DZ_1_vez.csv");
		for (Vez brod : vezovi) {
			// System.out.println(brod.getOznaka());
			i++;
		}
		System.out.println("vezovi: "+ i);
		i=0;
		

		List<ZahtjevRezervacije> zahtjeviRezervacija = csvReaderFactory.readFromCSV("./src/CSV/DZ_1_zahtjev_rezervacije.csv");
		for (ZahtjevRezervacije brod : zahtjeviRezervacija) {
			// System.out.println(brod.getIdBrod());
			i++;
		}
		System.out.println("zahtjevi: "+ i);
		i=0;
		

	}

}
