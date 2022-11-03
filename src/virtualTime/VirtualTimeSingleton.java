package virtualTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class VirtualTimeSingleton {
    private static VirtualTimeSingleton virtualTimeSingleton;
	private LocalDateTime virtualTime;
	private LocalDateTime realTimeSinceLastChange;


	private VirtualTimeSingleton()
	{
	}

	public static VirtualTimeSingleton getInstance()
	{
		if (virtualTimeSingleton == null)
		{
			virtualTimeSingleton = new VirtualTimeSingleton();
		}
		return virtualTimeSingleton;
	}

	public void passTime()
	{
		LocalDateTime newRealTime = LocalDateTime.now();
		long gapInSeconds = ChronoUnit.SECONDS.between(realTimeSinceLastChange,newRealTime);
		virtualTime = virtualTime.plusSeconds(gapInSeconds);
	}

	public String virtualTimeToString()
	{
		return DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss").
			format(VirtualTimeSingleton.getInstance().getVirtualtime());
	}

	public LocalDateTime getVirtualtime() {
		return virtualTime;
	}

	public void setVirtualtime(LocalDateTime virtualtime) {
		realTimeSinceLastChange = LocalDateTime.now();
		this.virtualTime = virtualtime;
	}

}
