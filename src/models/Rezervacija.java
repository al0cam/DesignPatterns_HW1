package models;

import java.time.LocalDateTime;

public class Rezervacija {
    private Brod brod;
    private Vez vez;
    private LocalDateTime datumVrijemeOd;
    private LocalDateTime datumVrijemeDo;
    
    public Rezervacija(Brod brod, Vez vez, LocalDateTime datumVrijemeOd, LocalDateTime datumVrijemeDo) {
        this.brod = brod;
        this.vez = vez;
        this.datumVrijemeOd = datumVrijemeOd;
        this.datumVrijemeDo = datumVrijemeDo;
    }
    public Brod getBrod() {
        return brod;
    }
    public void setBrod(Brod brod) {
        this.brod = brod;
    }
    public Vez getVez() {
        return vez;
    }
    public void setVez(Vez vez) {
        this.vez = vez;
    }
    public LocalDateTime getDatumVrijemeOd() {
        return datumVrijemeOd;
    }
    public void setDatumVrijemeOd(LocalDateTime datumVrijemeOd) {
        this.datumVrijemeOd = datumVrijemeOd;
    }
   
    public LocalDateTime getDatumVrijemeDo() {
        return datumVrijemeDo;
    }
    public void setDatumVrijemeDo(LocalDateTime datumVrijemeDo) {
        this.datumVrijemeDo = datumVrijemeDo;
    }

    
}
