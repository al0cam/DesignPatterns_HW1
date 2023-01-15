package models;

// Leaf
public class Brod implements Component {
    private Integer id;
    private String oznakaBroda;
    private String naziv;
    private String vrsta;
    private Float duljina;
    private Float sirina;
    private Float gaz;
    private Float maksimalnaBrzina;
    private Float kapacitetPutnika;
    private Float kapacitetOsobnihVozila;
    private Float kapacitetTereta;

    public Brod() {
    }

    public Brod(Integer id, String oznakaBroda, String naziv, String vrsta, Float duljina, Float sirina, Float gaz,
    Float maksimalnaBrzina, Float kapacitetPutnika, Float kapacitetOsobnihVozila, Float kapacitetTereta) {
        this.id = id;
        this.oznakaBroda = oznakaBroda;
        this.naziv = naziv;
        this.vrsta = vrsta;
        this.duljina = duljina;
        this.sirina = sirina;
        this.gaz = gaz;
        this.maksimalnaBrzina = maksimalnaBrzina;
        this.kapacitetPutnika = kapacitetPutnika;
        this.kapacitetOsobnihVozila = kapacitetOsobnihVozila;
        this.kapacitetTereta = kapacitetTereta;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getOznakaBroda() {
        return oznakaBroda;
    }
    public void setOznakaBroda(String oznakaBroda) {
        this.oznakaBroda = oznakaBroda;
    }
    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public String getVrsta() {
        return vrsta;
    }
    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }
    public Float getDuljina() {
        return duljina;
    }
    public void setDuljina(Float duljina) {
        this.duljina = duljina;
    }
    public Float getSirina() {
        return sirina;
    }
    public void setSirina(Float sirina) {
        this.sirina = sirina;
    }
    public Float getGaz() {
        return gaz;
    }
    public void setGaz(Float gaz) {
        this.gaz = gaz;
    }
    public Float getMaksimalnaBrzina() {
        return maksimalnaBrzina;
    }
    public void setMaksimalnaBrzina(Float maksimalnaBrzina) {
        this.maksimalnaBrzina = maksimalnaBrzina;
    }
    public Float getKapacitetPutnika() {
        return kapacitetPutnika;
    }
    public void setKapacitetPutnika(Float kapacitetPutnika) {
        this.kapacitetPutnika = kapacitetPutnika;
    }
    public Float getKapacitetOsobnihVozila() {
        return kapacitetOsobnihVozila;
    }
    public void setKapacitetOsobnihVozila(Float kapacitetOsobnihVozila) {
        this.kapacitetOsobnihVozila = kapacitetOsobnihVozila;
    }
    public Float getKapacitetTereta() {
        return kapacitetTereta;
    }
    public void setKapacitetTereta(Float kapacitetTereta) {
        this.kapacitetTereta = kapacitetTereta;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }


}
