package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controller.CLIController;
import ErrorCatcher.ErrorCatcherSingleton;
import csvReader.CSVReaderFactory;
import store.StoreSingleton;
import view.VT99;

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
	private static Integer brojLinija = null;
	private static String podjela = null;
	private static String omjer = null;


	public static void main(String[] args) {
		String patternPart = "((?<argNum>-[lbrkmvtpd]{1,2}) ((?<omjerNum>\\d{2}:\\d{2})|(?<podjelaNum>[RP]:[RP])|(?<fileNum>\\w+\\.csv)|(?<brojLinijaNum>\\d{2})) ?)";
		String patternList ="";
		for(int i = 1; i <= 9; i++)
		{
			patternList += patternPart.replace("Num", i+"");
		}
		patternList+=patternPart.replace("Num", "10")+"?";

		Pattern pattern = Pattern.compile(patternList);
		String joinedArgs = String.join(" ",args);
		if(args.length < 18)
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
			!joinedArgs.contains("-br") ||
			!joinedArgs.contains("-vt") ||
			!joinedArgs.contains("-pd") ||
			!joinedArgs.contains("-mv")
		)
		{
			ErrorCatcherSingleton.getInstance().catchCustomError("ERROR missing mandatory arguments");
			return;
		}

		Matcher matcher = pattern.matcher(joinedArgs);

		if(matcher.matches() && args.length == 18)
		{
			setSettings(matcher,args.length);
			if(
				findFile("-l",lukaFile) &&
				findFile("-b",brodoviFile) &&
				findFile("-m",molFile) &&
				findFile("-v",vezoviFile) &&
				findFile("-mv",molVezFile) &&
				findFile("-k",kanaliFile)
			)
			{
				VT99.setInstance(brojLinija, omjer, podjela);
				VT99.getInstance().getUserInput();
				// CLIController.getInstance().commandInterpreter();
			}
		}
		else if((matcher.matches() && args.length == 20))
		{
			setSettings(matcher, args.length);
			if(
				findFile("-l",lukaFile) &&
				findFile("-b",brodoviFile) &&
				findFile("-m",molFile) &&
				findFile("-v",vezoviFile) &&
				findFile("-mv",molVezFile) &&
				findFile("-k",kanaliFile) &&
				findFile("-r",rasporediFile)
			)
			{
				VT99.setInstance(brojLinija, omjer, podjela);
				VT99.getInstance().getUserInput();
				// CLIController.getInstance().commandInterpreter();
			}
		}
		
		System.out.println("END");
	}

	private static void setSettings(Matcher matcher, Integer argsLength)
	{
		for(int i = 1; i <= argsLength/2; i++)
		{
			String arg = matcher.group("arg"+(i));
			switch (arg) {
				case "-l":
					lukaFile = matcher.group("file"+(i));
					break;
				case "-b":
					brodoviFile = matcher.group("file"+(i));
					break;
				case "-v":
					vezoviFile = matcher.group("file"+(i));
					break;
				case "-m":
					molFile = matcher.group("file"+(i));
					break;
				case "-mv":
					molVezFile = matcher.group("file"+(i));
					break;
				case "-k":
					kanaliFile = matcher.group("file"+(i));
					break;
				case "-r":
					rasporediFile = matcher.group("file"+(i));
					break;
				case "-br":
					brojLinija = Integer.parseInt(matcher.group("brojLinija"+(i)));
					break;
				case "-vt":
					omjer = matcher.group("omjer"+(i));
					break;
				case "-pd":
					podjela = matcher.group("podjela"+(i));
					break;
				default:
					break;
			}
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
