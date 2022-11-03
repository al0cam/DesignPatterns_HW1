package models;

public class Vez {
    private Integer id;
    private String oznaka;
    private String vrsta;
    private Integer cijenaVezaPoSatu;
    private Integer maksimalnaDuljina;
    private Integer maksimalnaSirina;
    private Float maksimalnaDubina;

    private boolean zauzet = false;

    @Override
    public String toString() {
        return "Vez [id=" + id + ", oznaka=" + oznaka + ", vrsta=" + vrsta + ", cijenaVezaPoSatu=" + cijenaVezaPoSatu
                + ", maksimalnaDuljina=" + maksimalnaDuljina + ", maksimalnaSirina=" + maksimalnaSirina
                + ", maksimalnaDubina=" + maksimalnaDubina + ", zauzet=" + zauzet + "]";
    }
    public Vez(Integer id, String oznaka, String vrsta, Integer cijenaVezaPoSatu, Integer maksimalnaDuljina,
            Integer maksimalnaSirina, Float maksimalnaDubina) {
        this.id = id;
        this.oznaka = oznaka;
        this.vrsta = vrsta;
        this.cijenaVezaPoSatu = cijenaVezaPoSatu;
        this.maksimalnaDuljina = maksimalnaDuljina;
        this.maksimalnaSirina = maksimalnaSirina;
        this.maksimalnaDubina = maksimalnaDubina;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getOznaka() {
        return oznaka;
    }
    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }
    public String getVrsta() {
        return vrsta;
    }
    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }
    public Integer getCijenaVezaPoSatu() {
        return cijenaVezaPoSatu;
    }
    public void setCijenaVezaPoSatu(Integer cijenaVezaPoSatu) {
        this.cijenaVezaPoSatu = cijenaVezaPoSatu;
    }
    public Integer getMaksimalnaDuljina() {
        return maksimalnaDuljina;
    }
    public void setMaksimalnaDuljina(Integer maksimalnaDuljina) {
        this.maksimalnaDuljina = maksimalnaDuljina;
    }
    public Integer getMaksimalnaSirina() {
        return maksimalnaSirina;
    }
    public void setMaksimalnaSirina(Integer maksimalnaSirina) {
        this.maksimalnaSirina = maksimalnaSirina;
    }
    public Float getMaksimalnaDubina() {
        return maksimalnaDubina;
    }
    public void setMaksimalnaDubina(Float maksimalnaDubina) {
        this.maksimalnaDubina = maksimalnaDubina;
    }


    public boolean isZauzet() {
        return zauzet;
    }
    public void setZauzet(boolean zauzet) {
        this.zauzet = zauzet;
    }
}
