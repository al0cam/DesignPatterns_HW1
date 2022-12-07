package models;

import java.util.Map;

import Visitor.VezVisitor;

public class Vez {
    private Integer id;
    private String oznaka;
    private String vrsta;
    private Integer cijenaVezaPoSatu;
    private Integer maksimalnaDuljina;
    private Integer maksimalnaSirina;
    private Float maksimalnaDubina;

    public void accept(VezVisitor visitor) throws Exception
    {
        switch (vrsta) {
            case "PU":
                visitor.visitPUVez(this);
                break;
            case "PO":
                visitor.visitPOVez(this);
                break;
            case "OS":
                visitor.visitOSVez(this);
                break;
            default:
                throw new Exception("Vez vrsta: "+vrsta+" doesn't exist");
        }
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

}
