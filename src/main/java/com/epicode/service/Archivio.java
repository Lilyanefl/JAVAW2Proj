package com.epicode.service;

import com.epicode.model.ElementoCatalogo;
import com.epicode.model.Libro;
import com.epicode.model.Rivista;

import java.util.*;
import java.util.stream.Collectors;

public class Archivio {
    private Map<String, ElementoCatalogo> catalogo = new HashMap<>();

    public void aggiungiElemento(ElementoCatalogo elemento) {
        if (catalogo.containsKey(elemento.getCodiceISBN()))
            throw new IllegalArgumentException("Elemento con ISBN già presente.");
        catalogo.put(elemento.getCodiceISBN(), elemento);
    }

    public void rimuoviElemento(String codiceISBN) {
        catalogo.remove(codiceISBN);
    }

    public ElementoCatalogo ricercaPerISBN(String codiceISBN) {
        return Optional.ofNullable(catalogo.get(codiceISBN))
                .orElseThrow(() -> new NoSuchElementException("Elemento non trovato."));
    }

    public List<ElementoCatalogo> ricercaPerAnno(int anno) {
        return catalogo.values().stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());
    }

    public List<ElementoCatalogo> ricercaPerAutore(String autore) {
        return catalogo.values().stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(libro -> libro.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());
    }

    public void stampaStatistiche() {
        long totaleLibri = catalogo.values().stream().filter(e -> e instanceof Libro).count();
        long totaleRiviste = catalogo.values().stream().filter(e -> e instanceof Rivista).count();
        int pagineMax = catalogo.values().stream().mapToInt(ElementoCatalogo::getNumeroPagine).max().orElse(0);
        double mediaPagine = catalogo.values().stream().mapToInt(ElementoCatalogo::getNumeroPagine).average().orElse(0);

        System.out.println("Statistiche Catalogo:");
        System.out.println("Totale libri: " + totaleLibri);
        System.out.println("Totale riviste: " + totaleRiviste);
        System.out.println("Pagine massime: " + pagineMax);
        System.out.println("Media pagine: " + mediaPagine);
    }
    public List<ElementoCatalogo> ricercaPerTitolo(String titolo) {
        return catalogo.values().stream()
                .filter(e -> e.getTitolo().equalsIgnoreCase(titolo))
                .collect(Collectors.toList());
    }
    public void aggiornaElemento(String codiceISBN, String nuovoTitolo, int nuovoAnno, int nuovoNumeroPagine) {
        if (!catalogo.containsKey(codiceISBN)) {
            throw new NoSuchElementException("Elemento con ISBN " + codiceISBN + " non trovato.");
        }
        ElementoCatalogo elemento = catalogo.get(codiceISBN);
        elemento.setTitolo(nuovoTitolo);
        elemento.setAnnoPubblicazione(nuovoAnno);
        elemento.setNumeroPagine(nuovoNumeroPagine);
        System.out.println("Elemento aggiornato con successo.");
    }



}
