package models;

import java.util.ArrayList;
import java.util.List;

public class Mol extends ComponentAndIterator implements Component{
    private Integer id;
    private String naziv;

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
        this.children = new ArrayList<>();
    }
    public Mol(Integer id, String naziv) {
        this.id = id;
        this.naziv = naziv;
        this.children = new ArrayList<>();
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
        List<Vez> vezovi = new ArrayList<>();
        Iterator iterator = this.createIterator();
        while(iterator.hasNext())
        {
            Component component = (Component) iterator.next();
            if(component instanceof Vez)
            {
                Vez vez = (Vez) component;
                vezovi.add(vez);
            }
        }    
        return vezovi;
    }

    public void setVezovi(List<Vez> vezovi) {
        for (Vez vez : vezovi) {
            add(vez);
        }
    }
    public void addVez(Vez vez){
        add(vez);
    }
}
