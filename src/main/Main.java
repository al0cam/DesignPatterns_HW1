package main;

import java.util.List;

import FileType.BrodFile;
import FileType.FileType;
import csvReader.CSVReaderFactory;
import csvReader.CSVReaderSingleton;
import models.Brod;

public class Main {

	public static void main(String[] args) {
		// CSVReaderSingleton.getInstance().readFromCSV("./src/CSV/DZ_1_brod.csv");
		CSVReaderFactory csvReaderFactory = new CSVReaderFactory();
		List<Brod> brodovi = csvReaderFactory.readFromCSV("./src/CSV/DZ_1_brod.csv").getList();
		for (Brod brod : brodovi) {
			System.out.println(brod.getNaziv());
		}
	}

}
