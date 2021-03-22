package domain.model;

public class Dier {
    private String naam;
    private String  soort;
    private int voedsel;

    public Dier(String naam) {
        this.setNaam(naam);
    }

    public Dier(String naam, String soort, int voedsel) {
        this.setNaam(naam);
        this.setSoort(soort);
        this.setVoedsel(voedsel);
    }

    public Dier() {

    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        if (naam.isBlank()) {
            throw new IllegalArgumentException("Geen geldige naam");
        }
        this.naam = naam;
    }

    public String getSoort() {
        return soort;
    }

    public void setSoort(String soort) {
        if (soort.isBlank()) {
            throw new IllegalArgumentException("Geen geldige soort");
        }
        this.soort = soort;
    }

    public int getVoedsel() {
        return voedsel;
    }

    public void setVoedsel(int voedsel) {
        if (voedsel < 0) {
            throw new IllegalArgumentException("Geen geldige hoeveelheid voor voedsel");
        }
        this.voedsel = voedsel;
    }


}
