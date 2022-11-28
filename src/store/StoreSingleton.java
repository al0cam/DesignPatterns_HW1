package store;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import ErrorCatcher.ErrorCatcherSingleton;
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

	
	// TODO: provjera je li vez zauzet u odredjeno doba >> DONE ALI TREBA TEST
	// TODO: stvaranje rezervacije ako je vez slobodan u zahtjevano vrijeme >> DONE ALI TREBA TEST
	// TODO: vezanje broda za vez koji je rezervirao >> VALDJA DONE JER
	//  NEMA NIGDJE ZAPRAVO OPCIJA DA JE BROD ZAVEZAN OSIM DA JE VEZ ZAUZET
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

	public boolean isTimeBetween(LocalDateTime time1, LocalDateTime time2, LocalDateTime between)
	{
		return time1.isBefore(between) && time2.isAfter(between);
	}

	public boolean isTimeBetweenHours(LocalTime time1, LocalTime time2, LocalTime between)
	{
		return time1.isBefore(between) && time2.isAfter(between);
	}

	public boolean zauzetoURasporedu(Vez vez, LocalDateTime time, Raspored raspored)
	{		
		if(raspored.getDaniUtjednu().contains(time.getDayOfWeek()))
		{
			return isTimeBetweenHours(raspored.getVrijemeOd(), raspored.getVrijemeDo(), time.toLocalTime());
		}
		return false;
	}

	public boolean vezZauzetUVrijeme(Vez vez, LocalDateTime time)
	{
		if(rezervacijeEmpty() && rasporedEmpty())
			return false;
		else if(rezervacijeEmpty())
		{
			for (Raspored raspored : rasporedi) 
				return zauzetoURasporedu(vez,time,raspored);
		}
		else if(rasporedEmpty())
		{
			for (Rezervacija rezervacija : rezervacije) {
				if(rezervacija.getVez().equals(vez))
				{
					// TODO: uzet ovu liniju i ubacit u transformaciju zahtjeva u pravu rezervaciju
					// LocalDateTime vrijemeDo = rezersvacija.getDatumVrijemeOd().plusHours(rezervacija.getTrajanjePrivezaUSatima());
					return isTimeBetween(rezervacija.getDatumVrijemeOd(), rezervacija.getDatumVrijemeDo(), time);
				}
			}
		}
		else
		{
			for (Raspored raspored : rasporedi) 
				return zauzetoURasporedu(vez,time,raspored);
			
			for (Rezervacija rezervacija : rezervacije) 
				if(rezervacija.getVez().equals(vez))
					return isTimeBetween(rezervacija.getDatumVrijemeOd(), rezervacija.getDatumVrijemeDo(), time);
		}
		return false;
	}

	public List<Vez> pronadjiSlobodneVezove(LocalDateTime time)
	{
		List<Vez> slobodniVezovi = new ArrayList<>();
		for (Vez vez : vezovi) 
			if(vezZauzetUVrijeme(vez, time))
				slobodniVezovi.add(vez);

		return slobodniVezovi;
	}

	private boolean vrstaBrodaOdgovaraVrstiVeza(Brod brod, Vez vez)
	{
		if(
			(brod.getVrsta().equals("TR") || 
			brod.getVrsta().equals("KA") || 
			brod.getVrsta().equals("KL") || 
			brod.getVrsta().equals("KR")) &&
			vez.getVrsta().equals("PU")
		) return true;
		else if(
			(brod.getVrsta().equals("RI") || 
			brod.getVrsta().equals("TE")) &&
			vez.getVrsta().equals("PO")
		) return true;
		else if(
			(brod.getVrsta().equals("JA") || 
			brod.getVrsta().equals("RO") || 
			brod.getVrsta().equals("BR")) &&
			vez.getVrsta().equals("OS")
		) return true;
		else return false;
	}

	public boolean paseBrodVezu(Brod brod, Vez vez)
	{
		return vrstaBrodaOdgovaraVrstiVeza(brod, vez) &&
			brod.getGaz() <= vez.getMaksimalnaDubina() &&
			brod.getDuljina() <= vez.getMaksimalnaDuljina() && 
			brod.getSirina() <= vez.getMaksimalnaSirina();
	}

	public Brod getBrodById(Integer id) throws Exception
	{
		Brod brod = null;
		for (Brod bro : brodovi) {
			if(bro.getId().equals(id))
				brod = bro;
		}

		if(brod == null)
			throw new Exception("No brod with id "+id);
		
		return brod;
	}

	public Rezervacija pretvoriZahtjevURezervaciju(ZahtjevRezervacije zahtjevRezervacije)
	{
		Rezervacija rezervacija = null;
		try {
			Brod brod = getBrodById(zahtjevRezervacije.getIdBrod());
			List<Vez> slobodniVezovi = pronadjiSlobodneVezove(zahtjevRezervacije.getDatumVrijemeOd());
			if(slobodniVezovi.size() == 0)
			{
				System.out.println("Nema slobodnih vezova");
			}
			else
			{
				for (Vez vez : slobodniVezovi) {
					if(paseBrodVezu(brod, vez))
					{
						rezervacija = new Rezervacija(brod, vez, 
						zahtjevRezervacije.getDatumVrijemeOd(), 
						zahtjevRezervacije.getDatumVrijemeOd().plusHours(zahtjevRezervacije.getTrajanjePrivezaUSatima()), 
						zahtjevRezervacije.getTrajanjePrivezaUSatima());
					}
				}
				System.out.println("Brod ne odgovara niti jednom slobodnom vezu");
			}
		} catch (Exception e) {
			ErrorCatcherSingleton.getInstance().increaseErrorCountForGeneralError(e);
		}
		return rezervacija;
	}

	public void zahtjeviURezervacije()
	{
		Rezervacija rezervacija;
		for (ZahtjevRezervacije zahtjevRezervacije : zahtjeviRezervacija) {
			rezervacija = pretvoriZahtjevURezervaciju(zahtjevRezervacije);
			if(rezervacija != null)
				rezervacije.add(rezervacija);
		}
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
