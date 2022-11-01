package models;

import java.time.LocalDateTime;

public class Luka {
    private String naziv;
    private Float gpsSirina;
    private Float gpsVisina;
    private Float dubinaLuke;
    private Integer ukBrPutnickihVezova;
    private Integer ukBrPoslovnihVezova;
    private Integer ukBrOstalihhVezova;
    private LocalDateTime virtualnoVrijeme;
    
    public Luka(String naziv, Float gpsSirina, Float gpsVisina, Float dubinaLuke, Integer ukBrPutnickihVezova,
            Integer ukBrPoslovnihVezova, Integer ukBrOstalihhVezova, LocalDateTime virtualnoVrijeme) {
        this.naziv = naziv;
        this.gpsSirina = gpsSirina;
        this.gpsVisina = gpsVisina;
        this.dubinaLuke = dubinaLuke;
        this.ukBrPutnickihVezova = ukBrPutnickihVezova;
        this.ukBrPoslovnihVezova = ukBrPoslovnihVezova;
        this.ukBrOstalihhVezova = ukBrOstalihhVezova;
        this.virtualnoVrijeme = virtualnoVrijeme;
    }
    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public Float getGpsSirina() {
        return gpsSirina;
    }
    public void setGpsSirina(Float gpsSirina) {
        this.gpsSirina = gpsSirina;
    }
    public Float getGpsVisina() {
        return gpsVisina;
    }
    public void setGpsVisina(Float gpsVisina) {
        this.gpsVisina = gpsVisina;
    }
    public Float getDubinaLuke() {
        return dubinaLuke;
    }
    public void setDubinaLuke(Float dubinaLuke) {
        this.dubinaLuke = dubinaLuke;
    }
    public Integer getUkBrPutnickihVezova() {
        return ukBrPutnickihVezova;
    }
    public void setUkBrPutnickihVezova(Integer ukBrPutnickihVezova) {
        this.ukBrPutnickihVezova = ukBrPutnickihVezova;
    }
    public Integer getUkBrPoslovnihVezova() {
        return ukBrPoslovnihVezova;
    }
    public void setUkBrPoslovnihVezova(Integer ukBrPoslovnihVezova) {
        this.ukBrPoslovnihVezova = ukBrPoslovnihVezova;
    }
    public Integer getUkBrOstalihhVezova() {
        return ukBrOstalihhVezova;
    }
    public void setUkBrOstalihhVezova(Integer ukBrOstalihhVezova) {
        this.ukBrOstalihhVezova = ukBrOstalihhVezova;
    }
    public LocalDateTime getVirtualnoVrijeme() {
        return virtualnoVrijeme;
    }
    public void setVirtualnoVrijeme(LocalDateTime virtualnoVrijeme) {
        this.virtualnoVrijeme = virtualnoVrijeme;
    }

   
}
