package CLI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLISingleton {
    private static CLISingleton cliSingleton;

	private CLISingleton(){}
	
	public static CLISingleton getInstance()
	{
		if (cliSingleton == null)
		{
			cliSingleton = new CLISingleton();
		}
		return cliSingleton;
	}

    public void commandInterpreter(String command)
    {
        Pattern pattern = Pattern.compile(
            "(?<I>I$)|"+
            "(?<VR>VR [0-3]?\\d\\.[0-1][0-9]\\.\\d{4} \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9]$)|"+
            "(?<V>V [A-Z]{2} [A-Z] [0-3]?\\d\\.[0-1][0-9]\\.\\d{4}. \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9] [0-3]?\\d\\.[0-1][0-9]\\.\\d{4}. \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9]$)|"+
            "(?<UR>UR \\w+\\.csv$)|"+
            "(?<ZD>ZD \\d+$)|"+
            "(?<ZP>ZP \\d+ \\d+$)|"+
            "(?<Q>Q$)");

        
        
     

    }
    

}
