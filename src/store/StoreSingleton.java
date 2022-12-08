package store;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ErrorCatcher.ErrorCatcherSingleton;
import Visitor.VezVisitor;
import Vrsta.Trajekt;
import Vrsta.VrstaHandler;
import models.Brod;
import models.Kanal;
import models.Luka;
import models.Mol;
import models.MolVez;
import models.Raspored;
import models.Rezervacija;
import models.Vez;
import models.ZahtjevRezervacije;
import models.Zapis;
import virtualTime.VirtualTimeSingleton;

public class StoreSingleton {
	private static StoreSingleton store;

    public List<Brod> brodovi;
    public List<Luka> luke;
    public List<Raspored> rasporedi;
    public List<Vez> vezovi;
    public List<Mol> molovi;
    public List<Kanal> kanali;
    public List<ZahtjevRezervacije> zahtjeviRezervacija = new ArrayList<>();
    public List<Rezervacija> rezervacije = new ArrayList<>();
	// TODO: ubacit svaku akciju u dnevnik
	public List<Zapis> dnevnik = new ArrayList<>();

	private StoreSingleton(){}

	public static StoreSingleton getInstance()
	{
		if (store == null)
		{
			store = new StoreSingleton();
		}
		return store;
	}

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

	public LocalDateTime localTimeToLocalDateTime(LocalTime localTime, LocalDate localDate)
	{
		return localTime.atDate(localDate);
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
			return false;
		}
		else if(rezervacijeEmpty())
		{
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
			for (Raspored raspored : rasporedi) 
				if(raspored.getVez().equals(vez))
				{
					if(zauzetoURasporedu(vez,time,raspored))
						return true;
				}
			

			for (Rezervacija rezervacija : rezervacije) 
				if(rezervacija.getVez().equals(vez))
				{
					if(isTimeBetween(rezervacija.getDatumVrijemeOd(), rezervacija.getDatumVrijemeDo(), time))
					{
						return true;
					}
				}
		}
		return false;
	}

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
		for (Brod bro : brodovi) 
			if(bro.getId().equals(id))
				return bro;

		throw new Exception("No brod with id: "+id);
	}

	public Vez getVezById(Integer id) throws Exception
	{
		for (Vez vez : vezovi)
			if(vez.getId().equals(id))
				return vez;

		throw new Exception("No vez with id: "+id);
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
				Vez najboljiVez = null;
				for (Vez vez : slobodniVezovi) {
					if(paseBrodVezu(brod, vez))
					{
						if(
							najboljiVez == null ||
							(
								najboljiVez.getCijenaVezaPoSatu() >= vez.getCijenaVezaPoSatu() &&
								najboljiVez.getMaksimalnaDubina() >= vez.getMaksimalnaDubina() &&
								najboljiVez.getMaksimalnaDuljina() >= vez.getMaksimalnaDuljina() &&
								najboljiVez.getMaksimalnaSirina() >= vez.getMaksimalnaSirina()
							)
						)
							najboljiVez = vez;
					}
				}
				
				if(najboljiVez == null)
					System.out.println("Brod ne odgovara niti jednom slobodnom vezu");
				else 
					return new Rezervacija(
						brod, najboljiVez, 
						zahtjevRezervacije.getDatumVrijemeOd(), 
						zahtjevRezervacije.getDatumVrijemeOd().plusHours(zahtjevRezervacije.getTrajanjePrivezaUSatima())
					);
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
		Brod brod;
		try {
			brod = getBrodById(idBrod);
			if(!isBrodNaAnyKanalu(brod, VirtualTimeSingleton.getInstance().getVirtualtime()))
			{
				System.out.println("Brod nije ni na jednom kanalu");
				return;
			}
			Rezervacija rezervacija = pretvoriZahtjevURezervaciju(
				new ZahtjevRezervacije(idBrod, VirtualTimeSingleton.getInstance().getVirtualtime(), trajanjeUSatima)
			);
			
			Kanal kanal = dohvatiKanalNaKojemJeBrod(brod, VirtualTimeSingleton.getInstance().getVirtualtime());
			Zapis zapis = new Zapis.Builder().setBrod(brod).setKanal(kanal).setVrsta("privez").setPrihvacenaKomunikacija(true).setTime(VirtualTimeSingleton.getInstance().getVirtualtime()).build();

			if(rezervacija != null)
			{
				rezervacije.add(rezervacija);
				System.out.println("Kreiran je zahtjev za brod: "+rezervacija.getBrod().getId()+ " za vez: "+rezervacija.getVez().getId()+ " od: "+timeToString(rezervacija.getDatumVrijemeOd()) + " do: "+timeToString(rezervacija.getDatumVrijemeDo()));
				zapis.setPrihvacenPrivez(true);
				zapis.setRezervacija(rezervacija);
				dnevnik.add(zapis);
				obavijestiPrisutneBrodove(
					kanal,
					VirtualTimeSingleton.getInstance().getVirtualtime(),
					"Stvorena je nova rezervacija za brod: "+idBrod+" na vezu: "+ rezervacija.getVez().getId()
				);
			}
			else
			{
				System.out.println("Nije kreiran zahtjev za brod: "+idBrod);
				zapis.setPrihvacenPrivez(false);
				dnevnik.add(zapis);
				obavijestiPrisutneBrodove(
					kanal,
					VirtualTimeSingleton.getInstance().getVirtualtime(),
					"Odbijen je zahtjev za rezervacija za brod: "+idBrod
				);
			}
		} catch (Exception e) {
			ErrorCatcherSingleton.getInstance().catchGeneralError(e);
		}
	}

	public void priveziBrodSRezervacijom(Integer idBrod)
	{
		try {
			Brod brod = getBrodById(idBrod);
			if(!isBrodNaAnyKanalu(brod, VirtualTimeSingleton.getInstance().getVirtualtime()))
			{
				System.out.println("Brod nije ni na jednom kanalu");
				return;
			}
			Kanal kanal = dohvatiKanalNaKojemJeBrod(brod, VirtualTimeSingleton.getInstance().getVirtualtime());
			Zapis zapis = new Zapis.Builder().setBrod(brod).setKanal(kanal).setVrsta("privez").setPrihvacenaKomunikacija(true).setTime(VirtualTimeSingleton.getInstance().getVirtualtime()).build();
			for (Raspored raspored : rasporedi) {
				if(raspored.getBrod().equals(brod) && zauzetoURasporedu(raspored.getVez(), VirtualTimeSingleton.getInstance().getVirtualtime(), raspored))
				{
					System.out.println("Brod: "+ brod.getId()+" privezan za vez: "+raspored.getVez().getId());
					zapis.setPrihvacenPrivez(true);
					zapis.setRezervacija(
						new Rezervacija(
							brod,
							raspored.getVez(),
							localTimeToLocalDateTime(raspored.getVrijemeOd(), VirtualTimeSingleton.getInstance().getVirtualtime().toLocalDate()),
							localTimeToLocalDateTime(raspored.getVrijemeDo(), VirtualTimeSingleton.getInstance().getVirtualtime().toLocalDate())
						)
					);
					dnevnik.add(zapis);
					obavijestiPrisutneBrodove(
						kanal,
						VirtualTimeSingleton.getInstance().getVirtualtime(),
						"Privezan je brod: "+idBrod+" s rezervacija na vez: "+ raspored.getVez().getId()
					);
					return;
				}
			}
			for (Rezervacija rezervacija : rezervacije) {
				if(rezervacija.getBrod().equals(brod) &&
					isTimeBetween(rezervacija.getDatumVrijemeOd(), rezervacija.getDatumVrijemeDo(), VirtualTimeSingleton.getInstance().getVirtualtime()) 
				){
					System.out.println("Brod: "+ brod.getId()+" privezan za vez: "+rezervacija.getVez().getId());
					zapis.setPrihvacenPrivez(true);
					zapis.setRezervacija(rezervacija);
					dnevnik.add(zapis);
					obavijestiPrisutneBrodove(
						kanal,
						VirtualTimeSingleton.getInstance().getVirtualtime(),
						"Privezan je brod: "+idBrod+" s rezervacija na vez: "+ rezervacija.getVez().getId()
					);
					return;
				}
			}
			zapis.setPrihvacenPrivez(false);
			dnevnik.add(zapis);
			System.out.println("Brod: "+ idBrod+" nema rezervaciju");
			obavijestiPrisutneBrodove(
				kanal,
				VirtualTimeSingleton.getInstance().getVirtualtime(),
				"Odbijen je zahtjev za privez broda: "+idBrod
			);
		} catch (Exception e) {
			ErrorCatcherSingleton.getInstance().catchGeneralError(e);
			return;
		}
	}

	private Mol getMolById(Integer id) throws Exception
	{
		for (Mol mol : molovi) 
			if(mol.getId().equals(id))
				return mol;

		throw new Exception("No mol with id: "+id);
	}

	public void loadMolVez(List<MolVez> molVezovi)
	{
		for (MolVez molVez : molVezovi) {
			try {
				Mol mol = getMolById(molVez.getMol());
				for (Integer idVez : molVez.getVezovi()) {
					mol.addVez(getVezById(idVez));
				}
			} catch (Exception e) {
				ErrorCatcherSingleton.getInstance().catchGeneralError(e);
			}
		}
	}

	private Kanal getKanalByFrekvencija(Integer id) throws Exception
	{
		for (Kanal kanal : kanali) 
			if(kanal.getFrekvencija().equals(id))
				return kanal;

		throw new Exception("No kanal with id: "+id);
	}

	private Zapis zadnjiZapisZaBrodIKanal(Brod brod, Kanal kanal, LocalDateTime time)
	{
		Zapis zadnjiZapisZaBrodIKanal = null;
		for (Zapis zapis : dnevnik) {
			if(zapis.getBrod().equals(brod) && zapis.getKanal().equals(kanal))
			{
				if(zadnjiZapisZaBrodIKanal == null)
				{
					zadnjiZapisZaBrodIKanal = zapis;
				}
				else if(isTimeBetween(zadnjiZapisZaBrodIKanal.getTime(), time, zapis.getTime()))
					zadnjiZapisZaBrodIKanal = zapis;
			}
		}
		return zadnjiZapisZaBrodIKanal;
	}

	private boolean isBrodNaKanalu(Brod brod, Kanal kanal, LocalDateTime time)
	{
		Zapis zadnjiZapis = zadnjiZapisZaBrodIKanal(brod, kanal, time);
		if(zadnjiZapis == null)
			return false;
		else if(zadnjiZapis.getVrsta().equals("prijava") || zadnjiZapis.getVrsta().equals("privez") && zadnjiZapis.isPrihvacenaKomunikacija())
			return true;
		else if(
			zadnjiZapis.getVrsta().equals("privez") &&
			zadnjiZapis.isPrihvacenPrivez() &&
		 	isTimeBetween(zadnjiZapis.getRezervacija().getDatumVrijemeOd(), zadnjiZapis.getRezervacija().getDatumVrijemeDo(), time)
		)
			return true;
		else 
			return false;
	}

	private boolean isBrodNaAnyKanalu(Brod brod, LocalDateTime time)
	{
		for (Kanal kanal : kanali) {
			Zapis zadnjiZapis = zadnjiZapisZaBrodIKanal(brod, kanal, time);
			if(zadnjiZapis == null)
				break;
			else if(zadnjiZapis.getVrsta().equals("prijava") || zadnjiZapis.getVrsta().equals("privez") && zadnjiZapis.isPrihvacenaKomunikacija())
				return true;
			else if(
				zadnjiZapis.getVrsta().equals("privez") &&
				zadnjiZapis.isPrihvacenPrivez() &&
				isTimeBetween(zadnjiZapis.getRezervacija().getDatumVrijemeOd(), zadnjiZapis.getRezervacija().getDatumVrijemeDo(), time)
			)
				return true;
		}
		return false;
	}

	private Kanal dohvatiKanalNaKojemJeBrod(Brod brod, LocalDateTime time)
	{
		for (Kanal kanal : kanali) {
			Zapis zadnjiZapis = zadnjiZapisZaBrodIKanal(brod, kanal, time);
			if(zadnjiZapis == null)
				break;
			else if(zadnjiZapis.getVrsta().equals("prijava") || zadnjiZapis.getVrsta().equals("privez") && zadnjiZapis.isPrihvacenaKomunikacija())
				return kanal;
			else if(
				zadnjiZapis.getVrsta().equals("privez") &&
				zadnjiZapis.isPrihvacenPrivez() &&
				isTimeBetween(zadnjiZapis.getRezervacija().getDatumVrijemeOd(), zadnjiZapis.getRezervacija().getDatumVrijemeDo(), time)
			)
				return kanal;
		}
		return null;
	}
	
	private boolean kanalPunUVrijeme(Kanal kanal, LocalDateTime time)
	{
		Integer counter = 0;
		for (Brod brod : brodovi) {
			if(isBrodNaKanalu(brod, kanal, time))
				counter++;
		}

		if(kanal.getMaksimalanBroj() > counter)
			return false;
		else 
			return true;
	}

	private void obavijestiPrisutneBrodove(Kanal kanal, LocalDateTime time, String message)
	{
		for (Brod brod : brodovi) {
			if(isBrodNaKanalu(brod, kanal, time))
				System.out.println("Brod: "+brod.getId()+" je dobio obavijest \n  | Obavijest: "+message);
		}
	}

	public void spajanjeNaKanal(Integer idBrod, Integer frekvencija)
	{
		try {
			Kanal kanal = getKanalByFrekvencija(frekvencija);
			Brod brod = getBrodById(idBrod);
			Zapis zapis;
			String message;
			if(
				!kanalPunUVrijeme(kanal, VirtualTimeSingleton.getInstance().getVirtualtime()) &&
			 	!isBrodNaAnyKanalu(brod, VirtualTimeSingleton.getInstance().getVirtualtime())
			)
			{
				zapis = new Zapis.Builder().
					setKanal(kanal).setBrod(brod).
					setPrihvacenaKomunikacija(true).
					setTime(VirtualTimeSingleton.getInstance().getVirtualtime()).
					setVrsta("prijava").
					build();
				message = "Brod: "+idBrod+" se spojio na kanal frekvencije: "+frekvencija;
			}
			else
			{
				zapis = new Zapis.Builder().
					setKanal(kanal).setBrod(brod).
					setPrihvacenaKomunikacija(false).
					setTime(VirtualTimeSingleton.getInstance().getVirtualtime()).
					setVrsta("prijava").
					build();
				message = "Brodu: "+idBrod+" je odbijeno spajanje na kanal frekvencije: "+frekvencija;
				System.out.println("Kanal sa frekvencijom: "+frekvencija+" je pun");
			}

			dnevnik.add(zapis);
			obavijestiPrisutneBrodove(kanal, VirtualTimeSingleton.getInstance().getVirtualtime(), message);
			return;
		} catch (Exception e) {
			ErrorCatcherSingleton.getInstance().catchGeneralError(e);
		}
	}

	public void odSpajanjeSKanala(Integer idBrod, Integer frekvencija)
	{
		try {
			Kanal kanal = getKanalByFrekvencija(frekvencija);
			Brod brod = getBrodById(idBrod);
			Zapis zapis = new Zapis.Builder().
					setKanal(kanal).setBrod(brod).
					setPrihvacenaKomunikacija(false).
					setTime(VirtualTimeSingleton.getInstance().getVirtualtime()).
					setVrsta("odjava").
					build();
			dnevnik.add(zapis);
			obavijestiPrisutneBrodove(kanal, VirtualTimeSingleton.getInstance().getVirtualtime(), "Brod: "+idBrod+" se odspojio sa kanala frekvencije: "+frekvencija + " i odlazi iz luke");
			return;
		} catch (Exception e) {
			ErrorCatcherSingleton.getInstance().catchGeneralError(e);
		}
	}

	public Map<String, Integer> zauzetiVezoviPremaVrsti(String dateTime)
	{
        VezVisitor.getInstance().clearMap();
		VezVisitor.getInstance().setTime(LocalDateTime.parse(dateTime.trim(),DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm")));

		for (Vez vez : vezovi) 
			try {
				vez.accept(VezVisitor.getInstance());
			} catch (Exception e) {
				ErrorCatcherSingleton.getInstance().catchGeneralError(e);
			}
		return VezVisitor.getInstance().getMap();
	}

	public void crtajBrod(Integer idBrod)
	{
		try {
			Brod brod = getBrodById(idBrod);
			VrstaHandler handler = Trajekt.getInstance();
			handler.handle(brod);
		} catch (Exception e) {
			ErrorCatcherSingleton.getInstance().catchGeneralError(e);
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

	public List<Rezervacija> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(List<Rezervacija> rezervacije) {
		this.rezervacije = rezervacije;
	}

	public List<Mol> getMolovi() {
		return molovi;
	}

	public void setMolovi(List<Mol> molovi) {
		this.molovi = molovi;
	}

	public List<Kanal> getKanali() {
		return kanali;
	}

	public void setKanali(List<Kanal> kanali) {
		this.kanali = kanali;
	}

	public List<Zapis> getDnevnik() {
		return dnevnik;
	}

	public void setDnevnik(List<Zapis> dnevnik) {
		this.dnevnik = dnevnik;
	}

	
}
