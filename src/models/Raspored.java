package models;

import java.time.LocalTime;
import java.util.List;

public class Raspored {
    private Integer idVez;
    private Integer idBrod;
    private List<Integer> daniUtjednu;
    private LocalTime vrijemeOd;
    private LocalTime vrijemeDo;
    
    public Raspored(Integer idVez, Integer idBrod, List<Integer> daniUtjednu, LocalTime vrijemeOd,
            LocalTime vrijemeDo) {
        this.idVez = idVez;
        this.idBrod = idBrod;
        this.daniUtjednu = daniUtjednu;
        this.vrijemeOd = vrijemeOd;
        this.vrijemeDo = vrijemeDo;
    }
    public Integer getIdVez() {
        return idVez;
    }
    public void setIdVez(Integer idVez) {
        this.idVez = idVez;
    }
    public Integer getIdBrod() {
        return idBrod;
    }
    public void setIdBrod(Integer idBrod) {
        this.idBrod = idBrod;
    }
    public List<Integer> getDaniUtjednu() {
        return daniUtjednu;
    }
    public void setDaniUtjednu(List<Integer> daniUtjednu) {
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
    
}
