package store;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import models.Brod;
import models.Luka;
import models.Raspored;
import models.Rezervacija;
import models.Vez;
import models.ZahtjevRezervacije;
import virtualTime.VirtualTimeSingleton;

public class StoreSingleton {
	private static StoreSingleton store;
    public List<Brod> brodovi;
    public List<Luka> luke;
    public List<Raspored> rasporedi;
    public List<Vez> vezovi;
    public List<ZahtjevRezervacije> zahtjeviRezervacija = new ArrayList<>();
    public List<Rezervacija> rezervacije = new ArrayList<>();

	private StoreSingleton(){}

	public static StoreSingleton getInstance()
	{
		if (store == null)
		{
			store = new StoreSingleton();
		}
		return store;
	}

	
	// TODO: provjera je li vez zauzet u odredjeno doba
	// TODO: stvaranje rezervacije ako je vez slobodan u zahtjevano vrijeme
	// TODO: vezanje broda za vez koji je rezervirao
	// TODO: zahtjev za privez, stvaranje rezervacije za vez

	public boolean rezervacijeEmpty()
	{
		return rezervacije.size() == 0;
	}

	public boolean zahtjeviRezervacijaEmpty()
	{
		return zahtjeviRezervacija.size() == 0;
	}
	public boolean rasporedEmpty()
	{
		return rasporedi == null || rasporedi.size() == 0;
	}

	public boolean zauzetoURasporedu(Vez vez, LocalDateTime time, Raspored raspored)
	{		

		if(raspored.getDaniUtjednu().contains(time.getDayOfWeek()))
		{
			return raspored.getVrijemeOd().isBefore(time.toLocalTime()) && raspored.getVrijemeDo().isAfter(time.toLocalTime()) ;
		}
		else
			System.out.println("is false");
		
		return false;
	}

	public boolean vezZauzetUVrijeme(Vez vez, LocalDateTime time)
	{
		if(rezervacijeEmpty() && rasporedEmpty())
			return false;
		else if(rezervacijeEmpty())
		{
			for (Raspored raspored : rasporedi) {
				if(zauzetoURasporedu(vez,time,raspored))
				{

				}
				
			}
		}
		else if(rasporedEmpty())
		{

		}

		return true;
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
