package com.epicode.model;

public class Rivista extends ElementoCatalogo {
    public enum Periodicita { SETTIMANALE, MENSILE, SEMESTRALE }
    private Periodicita periodicita;

    public Rivista(String codiceISBN, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(codiceISBN, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() { return periodicita; }
}
