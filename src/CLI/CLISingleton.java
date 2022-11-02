package CLI;

import java.util.Arrays;
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

    public void commandInterpreter()
    {
        Pattern pattern = Pattern.compile(
            "(?<bigGroup>(?<I>I$)|"+
            "(?<VR>VR [0-3]?\\d\\.[0-1][0-9]\\.\\d{4}. \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9]$)|"+
            "(?<V>V [A-Z]{2} [A-Z] [0-3]?\\d\\.[0-1][0-9]\\.\\d{4}. \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9] [0-3]?\\d\\.[0-1][0-9]\\.\\d{4}. \\d?\\d\\:[0-5][0-9]\\:[0-6][0-9]$)|"+
            "(?<UR>UR \\w+\\.csv$)|"+
            "(?<ZD>ZD \\d+$)|"+
            "(?<ZP>ZP \\d+ \\d+$)|"+
            "(?<Q>Q$))");

        while(true)
        {
            String command = System.console().readLine();

            Matcher matcher = pattern.matcher(command);

            if (matcher.matches()) {
                System.out.println(matcher.group("bigGroup"));
                String[] bigGroup = matcher.group("bigGroup").split(" ");
                executeCommand(bigGroup, command);

            } 
            else {
                System.out.println("ERROR parametri su krivo uneseni");
            }
        }
    }
    

    private void executeCommand(String[] bigGroup, String command)
    {
        System.out.println(Arrays.toString(bigGroup));
        // switch(bigGroup)
        // {

        // }

    } 

}
