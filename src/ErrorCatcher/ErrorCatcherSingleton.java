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

	public void catchLineError(String fileName, String[] line, Exception e)
	{
		errorCount++;
		System.out.println("\nError count: " + errorCount+"\n  | File: "+fileName + "\n  | Line: "+Arrays.toString(line)+"\n  | Cause: "+e.getMessage()+"\n");

	}

	public void catchCustomError( String error)
	{
		errorCount++;
		System.out.println("\nError count: " + errorCount + "\n  | Cause: "+error+"\n");
	}

	public void catchGeneralError(Exception e)
	{
		errorCount++;
		System.out.println("\nError count: " + errorCount +"\n  | Cause: "+e.getMessage()+"\n");
	}

	public Integer getErrorCount()
	{
		return errorCount;
	}
}
