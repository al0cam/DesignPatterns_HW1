package models;

public class Kanal {
    private Integer id;
    private Integer frekvencija;
    private Integer maksimalanBroj;

    public Kanal(Integer id, Integer frekvencija, Integer maksimalanBroj) {
        this.id = id;
        this.frekvencija = frekvencija;
        this.maksimalanBroj = maksimalanBroj;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getFrekvencija() {
        return frekvencija;
    }
    public void setFrekvencija(Integer frekvencija) {
        this.frekvencija = frekvencija;
    }
    public Integer getMaksimalanBroj() {
        return maksimalanBroj;
    }
    public void setMaksimalanBroj(Integer maksimalanBroj) {
        this.maksimalanBroj = maksimalanBroj;
    }
}
