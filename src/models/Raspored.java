package models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class Raspored {
    private Vez vez;
    private Brod brod;
    private List<DayOfWeek> daniUtjednu;
    private LocalTime vrijemeOd;
    private LocalTime vrijemeDo;

   
    
    
    public Raspored(Vez vez, Brod brod, List<DayOfWeek> daniUtjednu, LocalTime vrijemeOd, LocalTime vrijemeDo) {
        this.vez = vez;
        this.brod = brod;
        this.daniUtjednu = daniUtjednu;
        this.vrijemeOd = vrijemeOd;
        this.vrijemeDo = vrijemeDo;
    }
    public List<DayOfWeek> getDaniUtjednu() {
        return daniUtjednu;
    }
    public void setDaniUtjednu(List<DayOfWeek> daniUtjednu) {
        this.daniUtjednu = daniUtjednu;
    }
    public LocalTime getVrijemeOd() {
        return vrijemeOd;
    }
    public void setVrijemeOd(LocalTime vrijemeOd) {
        this.vrijemeOd = vrijemeOd;
    }
    public LocalTime getVrijemeDo() {
        return vrijemeDo;
    }
    public void setVrijemeDo(LocalTime vrijemeDo) {
        this.vrijemeDo = vrijemeDo;
    }
    public Vez getVez() {
        return vez;
    }
    public void setVez(Vez vez) {
        this.vez = vez;
    }
    public Brod getBrod() {
        return brod;
    }
    public void setBrod(Brod brod) {
        this.brod = brod;
    }





}
