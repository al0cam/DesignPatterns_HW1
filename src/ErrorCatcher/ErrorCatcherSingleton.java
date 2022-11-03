package ErrorCatcher;

import java.util.Arrays;

public class ErrorCatcherSingleton {
public static ErrorCatcherSingleton errorCatcher;
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

	public void increaseErrorCountForLine( String[] line, Exception e)
	{
		errorCount++;
		System.out.println("Error count: " + errorCount + " Line: "+Arrays.toString(line)+" Cause: "+e.getMessage());
	}

	public void increaseErrorCountForGeneralError(Exception e)
	{
		errorCount++;
		System.out.println("Error count: " + errorCount +" Cause: "+e.getMessage());
	}

	public Integer getErrorCount()
	{
		return errorCount;
	}
}
