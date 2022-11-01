package csvReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderSingleton {
	
	private static CSVReaderSingleton csvReaderSingleton;
	
	private CSVReaderSingleton()
	{
		
	}
	
	public static CSVReaderSingleton getInstance()
	{
		if (csvReaderSingleton == null)
		{
			csvReaderSingleton = new CSVReaderSingleton();
		}
		return csvReaderSingleton;
	}

	public List<String[]> readFromCSV(String path)
	{
		List<String[]> fileContent = null;
		try(
			BufferedReader fileReader = new BufferedReader(new FileReader(path))
		){
			String line = "";
			fileContent = new ArrayList<>();

			while ((line = fileReader.readLine()) != null)
			{
				String[] tokens = line.split(";");
				fileContent.add(tokens);

				// print of read lines in format
				// for (String string : tokens) {
					
				// 	System.out.print(String.format("%15s\t",string));
				// }
				// System.out.println();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}
}
