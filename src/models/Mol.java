package models;

import java.util.ArrayList;
import java.util.List;

public class Mol extends ComponentAndIterator implements Component{
    private Integer id;
    private String naziv;
    private List<Vez> vezovi;

    @Override
    public void execute()
    {
        for(Component component : children)
        {
            component.execute();
        }
    }
    
    public Mol(Integer id, String naziv, List<Vez> vezovi) {
        this.id = id;
        this.naziv = naziv;
        this.vezovi = vezovi;
    }
    public Mol(Integer id, String naziv) {
        this.id = id;
        this.naziv = naziv;
        this.vezovi = new ArrayList<>();
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public List<Vez> getVezovi() {
        return vezovi;
    }
    public void setVezovi(List<Vez> vezovi) {
        this.vezovi = vezovi;
    }
    public void addVez(Vez vez){
        if(vezovi == null)
            vezovi = new ArrayList<>();
        vezovi.add(vez);
    }
}
