package main;

import CLI.CLISingleton;
import ErrorCatcher.ErrorCatcherSingleton;
import csvReader.CSVReaderFactory;
import store.StoreSingleton;

public class Main {

	public static void main(String[] args) {
		CSVReaderFactory csvReaderFactory = new CSVReaderFactory();

		if(args.length == 1 && args[0].contains(".csv"))
		{
			try {
				StoreSingleton.getInstance().rasporedi = csvReaderFactory.readFromCSV(args[0]);
			} catch (Exception e) {
				ErrorCatcherSingleton.getInstance().increaseErrorCountForGeneralError(e);
			}
		}

		StoreSingleton.getInstance().setBrodovi(csvReaderFactory.readFromCSV("DZ_1_brod.csv"));
		StoreSingleton.getInstance().setLuke(csvReaderFactory.readFromCSV("DZ_1_luka.csv"));
		StoreSingleton.getInstance().setVezovi(csvReaderFactory.readFromCSV("DZ_1_vez.csv"));

		CLISingleton.getInstance().commandInterpreter();
	}

}
