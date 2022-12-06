package models;

import java.util.List;

public class MolVez {
    private Integer mol;
    private List<Integer> vezovi;
    public Integer getMol() {
        return mol;
    }
    public void setMol(Integer mol) {
        this.mol = mol;
    }
    public List<Integer> getVezovi() {
        return vezovi;
    }
    public void setVezovi(List<Integer> vezovi) {
        this.vezovi = vezovi;
    }
    public MolVez(Integer mol, List<Integer> vezovi) {
        this.mol = mol;
        this.vezovi = vezovi;
    }
  
    
    
    
    
}
