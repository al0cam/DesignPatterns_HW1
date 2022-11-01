package models;

import java.time.LocalDateTime;

public class ZahtjevRezervacije {
    private Integer idBrod;
    private LocalDateTime datumVrijemeOd;
    private Integer trajanjePrivezaUSatima;
    
    public ZahtjevRezervacije(Integer idBrod, LocalDateTime datumVrijemeOd, Integer trajanjePrivezaUSatima) {
        this.idBrod = idBrod;
        this.datumVrijemeOd = datumVrijemeOd;
        this.trajanjePrivezaUSatima = trajanjePrivezaUSatima;
    }
    public Integer getIdBrod() {
        return idBrod;
    }
    public void setIdBrod(Integer idBrod) {
        this.idBrod = idBrod;
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
    
}
