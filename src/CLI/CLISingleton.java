package CLI;

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
        switch (command) {
            case value:
                
                break;
        
            default:
                break;
        }

    }
    

}
