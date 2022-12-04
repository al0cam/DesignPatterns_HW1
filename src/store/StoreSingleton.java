package store;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

	
	// provjera je li vez zauzet u odredjeno doba >> DONE 
	// TODO: stvaranje rezervacije ako je vez slobodan u zahtjevano vrijeme >> DONE ALI TREBA TEST
	// TODO: vezanje broda za vez koji je rezervirao >> VALDJA DONE JER
	//  NEMA NIGDJE ZAPRAVO OPCIJA DA JE BROD ZAVEZAN OSIM DA JE VEZ ZAUZET
	// TODO: zahtjev za privez ako je brod rezervirao vez >>> rezultat je samo notification koji kaze jel privezano il ne
	// TODO: zahtjev za privez ako brod nema rezervaciju, stvaranje rezervacije za vez 


	public String timeToString(LocalDateTime time)
	{
		return DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss").format(time);
	}

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
		{
			System.out.println("ALL EMPTY");
			return false;
		}
		else if(rezervacijeEmpty())
		{
			// System.out.println("2");
			for (Raspored raspored : rasporedi) {
				if(raspored.getVez().equals(vez))
				{
					if(zauzetoURasporedu(vez,time,raspored))
						return true;
				}
			}
		}
		else if(rasporedEmpty())
		{
			// System.out.println("3");
			for (Rezervacija rezervacija : rezervacije) {
				if(rezervacija.getVez().equals(vez))
				{
					if(isTimeBetween(rezervacija.getDatumVrijemeOd(), rezervacija.getDatumVrijemeDo(), time))
						return true;
				}
			}
		}
		else
		{
			// System.out.println("else 1");
			for (Raspored raspored : rasporedi) 
				if(raspored.getVez().equals(vez))
				{
					if(zauzetoURasporedu(vez,time,raspored))
						return true;
				}
			

			// System.out.println("else 2");
			for (Rezervacija rezervacija : rezervacije) 
				if(rezervacija.getVez().equals(vez))
				{
					// System.out.println("else 2 vez == vez");
					if(isTimeBetween(rezervacija.getDatumVrijemeOd(), rezervacija.getDatumVrijemeDo(), time))
					{
						// System.out.println("else 2 if");
						return true;
					}
				}
		}
		return false;
	}

	// TODO: vez zauzet od do uz ispis do kad je zauzet

	// public boolean vezZauzetOdDo(Vez vez, LocalDateTime vrijemeOd, LocalDateTime vrijemeDo)
	// {
	// 	if(rezervacijeEmpty() && rasporedEmpty())
	// 	{
	// 		return false;
	// 	}
	// 	else if(rezervacijeEmpty())
	// 	{
	// 		// System.out.println("2");
	// 		for (Raspored raspored : rasporedi) {
	// 			if(raspored.getIdVez().equals(vez.getId()))
	// 			{
	// 				if(zauzetoURasporedu(vez,time,raspored))
	// 					return true;
	// 			}
	// 		}
	// 	}
	// 	else if(rasporedEmpty())
	// 	{
	// 		// System.out.println("3");
	// 		for (Rezervacija rezervacija : rezervacije) {
	// 			if(rezervacija.getVez().equals(vez))
	// 			{
	// 				if(isTimeBetween(rezervacija.getDatumVrijemeOd(), rezervacija.getDatumVrijemeDo(), time))
	// 					return true;
	// 			}
	// 		}
	// 	}
	// 	else
	// 	{
	// 		// System.out.println("else 1");
	// 		for (Raspored raspored : rasporedi) 
	// 			if(raspored.getIdVez().equals(vez.getId()))
	// 			{
	// 				if(zauzetoURasporedu(vez,time,raspored))
	// 					return true;
	// 			}
			

	// 		// System.out.println("else 2");
	// 		for (Rezervacija rezervacija : rezervacije) 
	// 			if(rezervacija.getVez().equals(vez))
	// 			{
	// 				// System.out.println("else 2 vez == vez");
	// 				if(isTimeBetween(rezervacija.getDatumVrijemeOd(), rezervacija.getDatumVrijemeDo(), time))
	// 				{
	// 					// System.out.println("else 2 if");
	// 					return true;
	// 				}
	// 			}
	// 	}
	// 	return false;
	// }

	public List<Vez> pronadjiSlobodneVezove(LocalDateTime time)
	{
		List<Vez> slobodniVezovi = new ArrayList<>();
		for (Vez vez : vezovi) 
			if(!vezZauzetUVrijeme(vez, time))
				slobodniVezovi.add(vez);

		return slobodniVezovi;
	}

	public List<Vez> pronadjiZauzeteVezove(LocalDateTime time)
	{
		List<Vez> zauzetiVezovi = new ArrayList<>();
		for (Vez vez : vezovi) 
			if(vezZauzetUVrijeme(vez, time))
				zauzetiVezovi.add(vez);

		return zauzetiVezovi;
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

	public Vez getVezById(Integer id) throws Exception
	{
		Vez brod = null;
		for (Vez bro : vezovi) {
			if(bro.getId().equals(id))
				brod = bro;
		}

		if(brod == null)
			throw new Exception("No vez with id "+id);
		
		return brod;
	}

	public Rezervacija pretvoriZahtjevURezervaciju(ZahtjevRezervacije zahtjevRezervacije)
	{
		try {
			Brod brod = getBrodById(zahtjevRezervacije.getIdBrod());
			List<Vez> slobodniVezovi = pronadjiSlobodneVezove(zahtjevRezervacije.getDatumVrijemeOd());
			if(slobodniVezovi.size() == 0)
			{
				System.out.println("Nema slobodnih vezova u trazeno vrijeme");
			}
			else
			{
				for (Vez vez : slobodniVezovi) {
					if(paseBrodVezu(brod, vez))
					{
						return new Rezervacija(brod, vez, 
						zahtjevRezervacije.getDatumVrijemeOd(), 
						zahtjevRezervacije.getDatumVrijemeOd().plusHours(zahtjevRezervacije.getTrajanjePrivezaUSatima()), 
						zahtjevRezervacije.getTrajanjePrivezaUSatima());
					}
				}
				System.out.println("Brod ne odgovara niti jednom slobodnom vezu");
			}
		} catch (Exception e) {
			ErrorCatcherSingleton.getInstance().catchGeneralError(e);
		}
		return null;
	}

	public void zahtjeviURezervacije()
	{
		Rezervacija rezervacija;
		for (ZahtjevRezervacije zahtjevRezervacije : zahtjeviRezervacija) {
			rezervacija = pretvoriZahtjevURezervaciju(zahtjevRezervacije);
			if(rezervacija != null)
				rezervacije.add(rezervacija);
		}
		zahtjeviRezervacija.clear();
	}

	public void novaRezervacija(Integer idBrod, Integer trajanjeUSatima)
	{
		Rezervacija rezervacija = pretvoriZahtjevURezervaciju(
			new ZahtjevRezervacije(idBrod, VirtualTimeSingleton.getInstance().getVirtualtime(), trajanjeUSatima)
		);
		
		if(rezervacija != null)
		{
			rezervacije.add(rezervacija);
			System.out.println("Kreiran je zahtjev za brod: "+rezervacija.getBrod().getId()+ " za vez: "+rezervacija.getVez().getId()+ " od: "+timeToString(rezervacija.getDatumVrijemeOd()) + " do: "+timeToString(rezervacija.getDatumVrijemeDo()));
		}
		else
			System.out.println("Nije kreiran zahtjev za brod: "+idBrod);
	}

	public void priveziBrodSRezervacijom(Integer idBrod)
	{
		try {
			Brod brod = getBrodById(idBrod);
			for (Raspored raspored : rasporedi) {
				if(raspored.getBrod().equals(brod) && zauzetoURasporedu(raspored.getVez(), VirtualTimeSingleton.getInstance().getVirtualtime(), raspored))
				{
					System.out.println("Brod: "+ brod.getId()+" privezan za vez: "+raspored.getVez().getId());
					return;
				}
			}
			for (Rezervacija rezervacija : rezervacije) {
				if(rezervacija.getBrod().equals(brod) &&
					isTimeBetween(rezervacija.getDatumVrijemeOd(), rezervacija.getDatumVrijemeDo(), VirtualTimeSingleton.getInstance().getVirtualtime()) 
				){
					System.out.println("Brod: "+ brod.getId()+" privezan za vez: "+rezervacija.getVez().getId());
					return;
				}
			}
		} catch (Exception e) {
			ErrorCatcherSingleton.getInstance().catchGeneralError(e);
			return;
		}
		System.out.println("Brod: "+ idBrod+" nema rezervaciju");
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

	public List<Rezervacija> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(List<Rezervacija> rezervacije) {
		this.rezervacije = rezervacije;
	}

	
}
