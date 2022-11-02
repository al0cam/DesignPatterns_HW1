package ErrorCatcher;

public class ErrorCatcherSingleton {
    private static ErrorCatcherSingleton errorCatcher;
	
	private ErrorCatcherSingleton()
	{
	}
	
	public static ErrorCatcherSingleton getInstance()
	{
		if (errorCatcher == null)
		{
			errorCatcher = new ErrorCatcherSingleton();
		}
		return errorCatcher;
	}

    
}
