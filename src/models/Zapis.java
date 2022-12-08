package models;

import java.time.LocalDateTime;

public class Zapis {
    // vrste: prijava, odjava, privez odjava je za odvez i odjavu zajedno
    private String vrsta;
    private Kanal kanal;
    private Brod brod;
    private LocalDateTime time;
    private Rezervacija rezervacija;
    private boolean prihvacenaKomunikacija;
    private boolean prihvacenPrivez;

    
    public Zapis() {}

    private Zapis(Builder builder){
        this.kanal = builder.kanal;
        this.rezervacija = builder.rezervacija;
        this.prihvacenaKomunikacija = builder.prihvacenaKomunikacija;
        this.prihvacenPrivez = builder.prihvacenPrivez;
        this.time = builder.time;
        this.vrsta = builder.vrsta;
        this.brod = builder.brod;
    }

    public static class Builder{
        private String vrsta;
        private Kanal kanal;
        private Brod brod;
        private LocalDateTime time;
        private Rezervacija rezervacija;
        private boolean prihvacenaKomunikacija;
        private boolean prihvacenPrivez;

        public Builder(){}

        public Builder setKanal(Kanal kanal) {
            this.kanal = kanal;
            return this;
        }

        public Builder setRezervacija(Rezervacija rezervacija) {
            this.rezervacija = rezervacija;
            return this;
        }
    
        public Builder setPrihvacenaKomunikacija(boolean prihvacenaKomunikacija) {
            this.prihvacenaKomunikacija = prihvacenaKomunikacija;
            return this;
        }
    
        public Builder setPrihvacenPrivez(boolean prihvacenPrivez) {
            this.prihvacenPrivez = prihvacenPrivez;
            return this;
        }

        public Builder setTime(LocalDateTime time) {
            this.time = time;
            return this;
        }

        public Builder setVrsta(String vrsta) {
            this.vrsta = vrsta;
            return this;
        }


        public Builder setBrod(Brod brod) {
            this.brod = brod;
            return this;

        }

        public Zapis build()
        {
            return new Zapis(this);
        }
    }

    public Kanal getKanal() {
        return kanal;
    }

    public void setKanal(Kanal kanal) {
        this.kanal = kanal;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public boolean isPrihvacenaKomunikacija() {
        return prihvacenaKomunikacija;
    }

    public void setPrihvacenaKomunikacija(boolean prihvacenaKomunikacija) {
        this.prihvacenaKomunikacija = prihvacenaKomunikacija;
    }

    public boolean isPrihvacenPrivez() {
        return prihvacenPrivez;
    }

    public void setPrihvacenPrivez(boolean prihvacenPrivez) {
        this.prihvacenPrivez = prihvacenPrivez;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public Brod getBrod() {
        return brod;
    }

    public void setBrod(Brod brod) {
        this.brod = brod;
    }

    
}
