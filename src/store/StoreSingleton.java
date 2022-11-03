package store;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import models.Brod;
import models.Luka;
import models.Raspored;
import models.Vez;
import models.ZahtjevRezervacije;
import virtualTime.VirtualTimeSingleton;

public class StoreSingleton {
public static StoreSingleton store;
    public List<Brod> brodovi;
    public List<Luka> luke;
    public List<Raspored> rasporedi;
    public List<Vez> vezovi;
    public List<ZahtjevRezervacije> zahtjeviRezervacija;

	private StoreSingleton(){}

	public static StoreSingleton getInstance()
	{
		if (store == null)
		{
			store = new StoreSingleton();
		}
		return store;
	}

	public void updateVezovi(){
		DayOfWeek day = VirtualTimeSingleton.getInstance().getVirtualtime().getDayOfWeek();
		int hours = VirtualTimeSingleton.getInstance().getVirtualtime().getHour();
		int minutes = VirtualTimeSingleton.getInstance().getVirtualtime().getMinute();
		LocalTime virtualLocalTime = LocalTime.of(hours, minutes);

		for (Raspored raspored : rasporedi) {
			if(
				raspored.getDaniUtjednu().contains(day) &&
			 	virtualLocalTime.compareTo(raspored.getVrijemeOd()) > 1 &&
			 	virtualLocalTime.compareTo(raspored.getVrijemeDo()) < 1
			){
				for (Vez vez : vezovi) {
					if(vez.getId() == raspored.getIdVez())
						vez.setZauzet(true);
				}
			}
		}
	}

	public List<Brod> getBrodovi() {
		return brodovi;
	}

	public void setBrodovi(List<Brod> brodovi) {
		this.brodovi = brodovi;
	}

	public List<Luka> getLuke() {
		return luke;
	}

	public void setLuke(List<Luka> luke) {
		this.luke = luke;
		VirtualTimeSingleton.getInstance().setVirtualtime(luke.get(0).getVirtualnoVrijeme());
	}

	public List<Raspored> getRasporedi() {
		return rasporedi;
	}

	public void setRasporedi(List<Raspored> rasporedi) {
		this.rasporedi = rasporedi;
	}

	public List<Vez> getVezovi() {
		return vezovi;
	}

	public void setVezovi(List<Vez> vezovi) {
		this.vezovi = vezovi;
	}

	public List<ZahtjevRezervacije> getZahtjeviRezervacija() {
		return zahtjeviRezervacija;
	}

	public void setZahtjeviRezervacija(List<ZahtjevRezervacije> zahtjeviRezervacija) {
		this.zahtjeviRezervacija = zahtjeviRezervacija;
	}

}
