package main;

import java.util.Arrays;

import CLI.CLISingleton;
import ErrorCatcher.ErrorCatcherSingleton;
import csvReader.CSVReaderFactory;
import store.StoreSingleton;

public class Main {

	public static void main(String[] args) {
		System.out.println(args.length);
		System.out.println(Arrays.toString(args));

		CSVReaderFactory csvReaderFactory = new CSVReaderFactory();

		if(args.length == 1 && args[0].contains(".csv"))
		{
			try {
				StoreSingleton.getInstance().rasporedi = csvReaderFactory.readFromCSV(args[0]);
			} catch (Exception e) {
				ErrorCatcherSingleton.getInstance().increaseErrorCountForGeneralError(e);
			}
		}

		StoreSingleton.getInstance().setBrodovi(csvReaderFactory.readFromCSV("./src/CSV/DZ_1_brod.csv"));
		StoreSingleton.getInstance().setLuke(csvReaderFactory.readFromCSV("./src/CSV/DZ_1_luka.csv"));
		StoreSingleton.getInstance().setVezovi(csvReaderFactory.readFromCSV("./src/CSV/DZ_1_vez.csv"));

		// ne ucitava se pri pokretanju
		// StoreSingleton.getInstance().zahtjeviRezervacija = csvReaderFactory.readFromCSV("./src/CSV/DZ_1_zahtjev_rezervacije.csv");

		CLISingleton.getInstance().commandInterpreter();
	}

}
