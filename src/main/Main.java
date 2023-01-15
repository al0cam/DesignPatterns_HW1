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
	private static String lukaFile = null;
	private static String brodoviFile = null;
	private static String vezoviFile = null;
	private static String molFile = null;
	private static String rasporediFile = null;
	private static String kanaliFile = null;

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
			setFileName(matcher.group("arg1"),matcher.group("file1"));
			setFileName(matcher.group("arg2"),matcher.group("file2"));
			setFileName(matcher.group("arg3"),matcher.group("file3"));
			setFileName(matcher.group("arg4"),matcher.group("file4"));
			setFileName(matcher.group("arg5"),matcher.group("file5"));
			setFileName(matcher.group("arg6"),matcher.group("file6"));

			if(
				findFile("-l",lukaFile) &&
				findFile("-b",brodoviFile) &&
				findFile("-m",molFile) &&
				findFile("-v",molFile) &&
				findFile("-mv",molVezFile) &&
				findFile("-k",kanaliFile)
			)
			{
				CLISingleton.getInstance().commandInterpreter();
			}
		}
		else if((matcher.matches() && args.length == 14))
		{
			setFileName(matcher.group("arg1"),matcher.group("file1"));
			setFileName(matcher.group("arg2"),matcher.group("file2"));
			setFileName(matcher.group("arg3"),matcher.group("file3"));
			setFileName(matcher.group("arg4"),matcher.group("file4"));
			setFileName(matcher.group("arg5"),matcher.group("file5"));
			setFileName(matcher.group("arg6"),matcher.group("file6"));
			setFileName(matcher.group("arg7"),matcher.group("file7"));

			if(
				findFile("-l",lukaFile) &&
				findFile("-b",brodoviFile) &&
				findFile("-m",molFile) &&
				findFile("-v",molFile) &&
				findFile("-mv",molVezFile) &&
				findFile("-k",kanaliFile) &&
				findFile("-r",rasporediFile)
			)
			{
				CLISingleton.getInstance().commandInterpreter();
			}
		}
		
		System.out.println("END");
	}

	private static void setFileName(String arg, String fileName)
	{
		switch (arg) {
			case "-l":
				lukaFile = fileName;
				break;
			case "-b":
				brodoviFile = fileName;
				break;
			case "-v":
				vezoviFile = fileName;
				break;
			case "-m":
				molFile = fileName;
				break;
			case "-mv":
				molVezFile = fileName;
				break;
			case "-k":
				kanaliFile = fileName;
				break;
			case "-r":
				rasporediFile = fileName;
				break;
			default:
				break;
		}
	}

	private static boolean findFile(String arg, String fileName)
	{
		CSVReaderFactory csvReaderFactory  = new CSVReaderFactory();

		switch (arg) {
			case "-l":
				try {
					StoreSingleton.getInstance().setLuka(csvReaderFactory.readFromCSV(fileName));
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
					StoreSingleton.getInstance().setTempVezovi(csvReaderFactory.readFromCSV(fileName));
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
					StoreSingleton.getInstance().setMolovi(csvReaderFactory.readFromCSV(fileName));
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().catchGeneralError(e);
					return false;
				}
				return true;
			case "-k":
				try {
					StoreSingleton.getInstance().setKanali(csvReaderFactory.readFromCSV(fileName)); 
				} catch (Exception e) {
					ErrorCatcherSingleton.getInstance().catchGeneralError(e);
					return false;
				}
				return true;
			case "-mv":
				try {
					StoreSingleton.getInstance().loadMolVez(csvReaderFactory.readFromCSV(fileName));
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
