package ErrorCatcher;

import java.util.Arrays;

import view.VT99;

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

	public void catchLineError(String fileName, String[] line, Exception e)
	{
		errorCount++;
		VT99.getInstance().writeError("\nError count: " + errorCount+"\n  | File: "+fileName + "\n  | Line: "+Arrays.toString(line)+"\n  | Cause: "+e.getMessage()+"\n");
	}

	public void catchCustomError( String error)
	{
		errorCount++;
		VT99.getInstance().writeError("\nError count: " + errorCount + "\n  | Cause: "+error+"\n");
	}

	public void catchGeneralError(Exception e)
	{
		errorCount++;
		VT99.getInstance().writeError("\nError count: " + errorCount +"\n  | Cause: "+e.getMessage()+"\n");
	}

	public void catchNonVTError(Exception e)
	{
		errorCount++;
		System.out.println("\nError count: " + errorCount +"\n  | Cause: "+e.getMessage()+"\n");
		// System.out.println("\nError count: " + errorCount +"\n  | Cause: "+e.getMessage()+"\n | Stack trace: "+Arrays.toString(e.getStackTrace())+"\n");
	}


	public Integer getErrorCount()
	{
		return errorCount;
	}
}
