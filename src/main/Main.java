package main;

import csvReader.CSVReaderSingleton;

public class Main {

	public static void main(String[] args) {
		CSVReaderSingleton.getInstance().readFromCSV("./src/CSV/DZ_1_brod.csv");

	}

}
