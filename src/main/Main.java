package main;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CLI.CLISingleton;
import ErrorCatcher.ErrorCatcherSingleton;
import csvReader.CSVReaderFactory;
import store.StoreSingleton;

public class Main {

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(
			"((?<arg1>-[lbrv]) (?<file1>\\w+\\.csv) ?)((?<arg2>-[lbrv]) (?<file2>\\w+\\.csv) ?)((?<arg3>-[lbrv]) (?<file3>\\w+\\.csv) ?)((?<arg4>-[lbrv]) (?<file4>\\w+\\.csv) ?)?"
			);
		String joinedArgs = String.join(" ",args);
		if(args.length < 6)
		{
			System.out.println("ERROR not enough arguments provided");
			return;
		}
		else if(!joinedArgs.contains("-l") || !joinedArgs.contains("-b") || !joinedArgs.contains("-v"))
		{
			System.out.println("ERROR missing mandatory arguments");
			return;
		}

		Matcher matcher = pattern.matcher(joinedArgs);


		if(matcher.matches() && args.length == 6)
		{
			if(
				
				findFile(matcher.group("arg1"),matcher.group("file1")) &&
				findFile(matcher.group("arg2"),matcher.group("file2")) &&
				findFile(matcher.group("arg3"),matcher.group("file3")) 
			)
			{
				CLISingleton.getInstance().commandInterpreter();
			}
		}
		else if((matcher.matches() && args.length == 8))
		{
			if(
				
				findFile(matcher.group("arg1"),matcher.group("file1")) &&
				findFile(matcher.group("arg2"),matcher.group("file2")) &&
				findFile(matcher.group("arg3"),matcher.group("file3")) &&
				findFile(matcher.group("arg4"),matcher.group("file4")) 
			)
			{
				CLISingleton.getInstance().commandInterpreter();
			}
		}
		
		System.out.println("END");
	}

	private static boolean findFile(String arg, String fileName)
	{
		CSVReaderFactory csvReaderFactory = new CSVReaderFactory();

		switch (arg) {
			case "-l":
				try {
					StoreSingleton.getInstance().setLuke(csvReaderFactory.readFromCSV(fileName));
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().increaseErrorCountForGeneralError(e);
					return false;
				}
				return true;
			case "-b":
				try {
					StoreSingleton.getInstance().setBrodovi(csvReaderFactory.readFromCSV(fileName));
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().increaseErrorCountForGeneralError(e);
					return false;
				}
				return true;
			case "-v":
				try {
					StoreSingleton.getInstance().setVezovi(csvReaderFactory.readFromCSV(fileName));
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().increaseErrorCountForGeneralError(e);
					return false;
				}
				return true;
			case "-r":
				try {
					StoreSingleton.getInstance().rasporedi = csvReaderFactory.readFromCSV(fileName);
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().increaseErrorCountForGeneralError(e);
					return false;
				}
				return true;
			default:
				return false;
		}
	}

}
