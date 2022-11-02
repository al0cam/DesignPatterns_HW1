package virtualTime;

public class VirtualTimeSingleton {
    private static VirtualTimeSingleton virtualTime;
	
	private VirtualTimeSingleton()
	{
	}
	
	public static VirtualTimeSingleton getInstance()
	{
		if (virtualTime == null)
		{
			virtualTime = new VirtualTimeSingleton();
		}
		return virtualTime;
	}

}
