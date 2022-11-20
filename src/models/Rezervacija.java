package models;

import java.time.LocalDateTime;

public class Rezervacija {
    private Brod brod;
    private Vez vez;
    private LocalDateTime datumVrijemeOd;
    private LocalDateTime datumVrijemeDo;
    private Integer trajanjePrivezaUSatima;

    

    
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
    public Integer getTrajanjePrivezaUSatima() {
        return trajanjePrivezaUSatima;
    }
    public void setTrajanjePrivezaUSatima(Integer trajanjePrivezaUSatima) {
        this.trajanjePrivezaUSatima = trajanjePrivezaUSatima;
    }
    public LocalDateTime getDatumVrijemeDo() {
        return datumVrijemeDo;
    }
    public void setDatumVrijemeDo(LocalDateTime datumVrijemeDo) {
        this.datumVrijemeDo = datumVrijemeDo;
    }

    
}
