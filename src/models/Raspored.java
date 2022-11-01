package models;

import java.util.List;

public class Raspored {
    private Integer idVez;
    private Integer idBrod;
    private List<Integer> daniUtjednu;
    private Integer vrijemeOd;
    private Integer vrijemeDo;
    
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
    public Integer getVrijemeOd() {
        return vrijemeOd;
    }
    public void setVrijemeOd(Integer vrijemeOd) {
        this.vrijemeOd = vrijemeOd;
    }
    public Integer getVrijemeDo() {
        return vrijemeDo;
    }
    public void setVrijemeDo(Integer vrijemeDo) {
        this.vrijemeDo = vrijemeDo;
    }

}
