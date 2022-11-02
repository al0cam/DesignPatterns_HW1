package virtualTime;

import java.time.LocalDateTime;

public class VirtualTimeSingleton {
    private static VirtualTimeSingleton virtualTime;
	private LocalDateTime time;

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

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}


}
