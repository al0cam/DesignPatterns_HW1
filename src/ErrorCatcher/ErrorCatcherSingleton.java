package ErrorCatcher;

import java.util.Arrays;

public class ErrorCatcherSingleton {
    private static ErrorCatcherSingleton errorCatcher;
	private Integer errorCount = 0;
	
	private ErrorCatcherSingleton(){}
	
	public static ErrorCatcherSingleton getInstance()
	{
		if (errorCatcher == null)
		{
			errorCatcher = new ErrorCatcherSingleton();
		}
		return errorCatcher;
	}

	public void increaseErrorCount( String[] line, Exception e)
	{
		errorCount++;
		System.out.println("Error count: " + errorCount + " Line: "+Arrays.toString(line)+" Cause: "+e.getMessage());
	}

	public Integer getErrorCount()
	{
		return errorCount;
	}
}
