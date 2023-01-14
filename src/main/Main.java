package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CLI.CLISingleton;
import ErrorCatcher.ErrorCatcherSingleton;
import csvReader.CSVReaderFactory;
import store.StoreSingleton;

public class Main {
	static boolean molLoaded = false;
	static boolean vezLoaded = false;
	static String molVezFile = null;

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(
			"((?<arg1>-[lbrkmv]{1,2}) (?<file1>\\w+\\.csv) ?)((?<arg2>-[lbrkmv]{1,2}) (?<file2>\\w+\\.csv) ?)((?<arg3>-[lbrkmv]{1,2}) (?<file3>\\w+\\.csv) ?)((?<arg4>-[lbrkmv]{1,2}) (?<file4>\\w+\\.csv) ?)((?<arg5>-[lbrkmv]{1,2}) (?<file5>\\w+\\.csv) ?)((?<arg6>-[lbrkmv]{1,2}) (?<file6>\\w+\\.csv) ?)((?<arg7>-[lbrkmv]{1,2}) (?<file7>\\w+\\.csv) ?)?"
			);
		String joinedArgs = String.join(" ",args);
		if(args.length < 12)
		{
			ErrorCatcherSingleton.getInstance().catchCustomError("ERROR not enough arguments provided");
			return;
		}
		else if(
			!joinedArgs.contains("-l") || 
			!joinedArgs.contains("-b") || 
			!joinedArgs.contains("-v") ||
			!joinedArgs.contains("-k") ||
			!joinedArgs.contains("-m") ||
			!joinedArgs.contains("-mv")
		)
		{
			ErrorCatcherSingleton.getInstance().catchCustomError("ERROR missing mandatory arguments");
			return;
		}

		Matcher matcher = pattern.matcher(joinedArgs);

		if(matcher.matches() && args.length == 12)
		{
			if(
				findFile(matcher.group("arg1"),matcher.group("file1")) &&
				findFile(matcher.group("arg2"),matcher.group("file2")) &&
				findFile(matcher.group("arg3"),matcher.group("file3")) &&
				findFile(matcher.group("arg4"),matcher.group("file4")) &&
				findFile(matcher.group("arg5"),matcher.group("file5")) &&
				findFile(matcher.group("arg6"),matcher.group("file6")) 
			)
			{
				CLISingleton.getInstance().commandInterpreter();
			}
		}
		else if((matcher.matches() && args.length == 14))
		{
			if(
				findFile(matcher.group("arg1"),matcher.group("file1")) &&
				findFile(matcher.group("arg2"),matcher.group("file2")) &&
				findFile(matcher.group("arg3"),matcher.group("file3")) &&
				findFile(matcher.group("arg4"),matcher.group("file4")) &&
				findFile(matcher.group("arg5"),matcher.group("file5")) &&
				findFile(matcher.group("arg6"),matcher.group("file6")) &&
				findFile(matcher.group("arg7"),matcher.group("file7"))
			)
			{
				CLISingleton.getInstance().commandInterpreter();
			}
		}
		
		System.out.println("END");
	}

	private static boolean findFile(String arg, String fileName)
	{
		CSVReaderFactory csvReaderFactory  = new CSVReaderFactory();

		switch (arg) {
			case "-l":
				try {
					StoreSingleton.getInstance().setLuke(csvReaderFactory.readFromCSV(fileName));
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().catchGeneralError(e);
					return false;
				}
				return true;
			case "-b":
				try {
					StoreSingleton.getInstance().setBrodovi(csvReaderFactory.readFromCSV(fileName));
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().catchGeneralError(e);
					return false;
				}
				return true;
			case "-v":
				try {
					StoreSingleton.getInstance().setVezovi(csvReaderFactory.readFromCSV(fileName));
					vezLoaded = true;
					if(molLoaded && molVezFile != null)
						findFile("-mv", molVezFile);
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().catchGeneralError(e);
					return false;
				}
				return true;
			case "-r":
				try {
					StoreSingleton.getInstance().rasporedi = csvReaderFactory.readFromCSV(fileName);
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().catchGeneralError(e);
				}
				return true;
			case "-m":
				try {
					StoreSingleton.getInstance().molovi = csvReaderFactory.readFromCSV(fileName);
					molLoaded = true;
					if(vezLoaded && molVezFile != null)
						findFile("-mv", molVezFile);
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().catchGeneralError(e);
					return false;
				}
				return true;
			case "-k":
				try {
					StoreSingleton.getInstance().kanali = csvReaderFactory.readFromCSV(fileName);
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().catchGeneralError(e);
					return false;
				}
				return true;
			case "-mv":
				try {
					if(molLoaded && vezLoaded)
					{
						StoreSingleton.getInstance().loadMolVez(csvReaderFactory.readFromCSV(fileName));
					}
					else
						molVezFile = fileName;
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().catchGeneralError(e);
					return false;
				}
				return true;
			default:
				return false;
		}
	}

}
