package virtualTime;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class VirtualTimeSingleton {
    private static VirtualTimeSingleton virtualTime;
	private LocalDateTime virtualtime;
	private LocalDateTime realTimeSinceLastChange;


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

	public void passTime()
	{
		LocalDateTime newRealTime = LocalDateTime.now();
		long gapInSeconds = ChronoUnit.SECONDS.between(realTimeSinceLastChange,newRealTime);
		virtualtime.plusSeconds(gapInSeconds);
	}

	public LocalDateTime getVirtualtime() {
		return virtualtime;
	}

	public void setVirtualtime(LocalDateTime virtualtime) {
		realTimeSinceLastChange = LocalDateTime.now();
		this.virtualtime = virtualtime;
	}

}
