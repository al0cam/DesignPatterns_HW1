package csvReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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

	public List<String> readFromCSV(String path)
	{
		
		System.out.println(System.getProperty("user.dir"));
		try(BufferedReader fileReader = new BufferedReader(new FileReader(path)))
		{
		String line = "";

		//Read the file line by line
		while ((line = fileReader.readLine()) != null)
		{
			//Get all tokens available in line
			String[] tokens = line.split(",");

			//Verify tokens
			System.out.println(Arrays.toString(tokens));
		}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		
		return null;
	}
}
