package com.epicode;

import com.epicode.model.*;
import com.epicode.model.Rivista.Periodicita;
import com.epicode.service.Archivio;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Archivio archivio = new Archivio();
        boolean esci = false;

        while (!esci) {
            System.out.println("\n=== Menu Catalogo Bibliografico ===");
            System.out.println("1. Aggiungi un elemento");
            System.out.println("2. Rimuovi un elemento per ISBN");
            System.out.println("3. Ricerca per ISBN");
            System.out.println("4. Ricerca per anno di pubblicazione");
            System.out.println("5. Ricerca per autore");
            System.out.println("6. Stampa statistiche catalogo");
            System.out.println("7. Ricerca per titolo");
            System.out.println("8. Aggiorna un elemento per ISBN");
            System.out.println("9. Esci");
            System.out.print("Seleziona un'opzione: ");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (scelta) {
                    case 1:
                        System.out.println("Tipo di elemento (1. Libro, 2. Rivista): ");
                        int tipo = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("ISBN: ");
                        String isbn = scanner.nextLine();
                        System.out.print("Titolo: ");
                        String titolo = scanner.nextLine();
                        System.out.print("Anno di pubblicazione: ");
                        int anno = scanner.nextInt();
                        System.out.print("Numero pagine: ");
                        int pagine = scanner.nextInt();
                        scanner.nextLine();

                        if (tipo == 1) {
                            System.out.print("Autore: ");
                            String autore = scanner.nextLine();
                            System.out.print("Genere: ");
                            String genere = scanner.nextLine();
                            archivio.aggiungiElemento(new Libro(isbn, titolo, anno, pagine, autore, genere));
                        } else {
                            System.out.print("Periodicità (1. SETTIMANALE, 2. MENSILE, 3. SEMESTRALE): ");
                            int periodicita = scanner.nextInt();
                            scanner.nextLine();
                            Periodicita p = periodicita == 1 ? Periodicita.SETTIMANALE
                                    : periodicita == 2 ? Periodicita.MENSILE : Periodicita.SEMESTRALE;
                            archivio.aggiungiElemento(new Rivista(isbn, titolo, anno, pagine, p));
                        }
                        System.out.println("Elemento aggiunto con successo!");
                        break;

                    case 2:
                        System.out.print("Inserisci ISBN dell'elemento da rimuovere: ");
                        String isbnRimuovi = scanner.nextLine();
                        archivio.rimuoviElemento(isbnRimuovi);
                        System.out.println("Elemento rimosso.");
                        break;

                    case 3:
                        System.out.print("Inserisci ISBN: ");
                        String isbnCerca = scanner.nextLine();
                        System.out.println("Elemento trovato: " + archivio.ricercaPerISBN(isbnCerca).getTitolo());
                        break;

                    case 4:
                        System.out.print("Inserisci anno di pubblicazione: ");
                        int annoCerca = scanner.nextInt();
                        List<ElementoCatalogo> perAnno = archivio.ricercaPerAnno(annoCerca);
                        perAnno.forEach(e -> System.out.println("Titolo: " + e.getTitolo()));
                        break;

                    case 5:
                        System.out.print("Inserisci autore: ");
                        String autoreCerca = scanner.nextLine();
                        List<ElementoCatalogo> perAutore = archivio.ricercaPerAutore(autoreCerca);
                        perAutore.forEach(e -> System.out.println("Titolo: " + e.getTitolo()));
                        break;

                    case 6:
                        archivio.stampaStatistiche();
                        break;
                    case 7:
                        System.out.print("Inserisci il titolo da cercare: ");
                        String titoloCerca = scanner.nextLine();
                        List<ElementoCatalogo> perTitolo = archivio.ricercaPerTitolo(titoloCerca);
                        if (perTitolo.isEmpty()) {
                            System.out.println("Nessun elemento trovato con il titolo specificato.");
                        } else {
                            System.out.println("Elementi trovati:");
                            perTitolo.forEach(e -> System.out.println("Titolo: " + e.getTitolo() + ", ISBN: " + e.getCodiceISBN()));
                        }
                        break;
                    case 8:
                        System.out.print("Inserisci ISBN dell'elemento da aggiornare: ");
                        String isbnAggiorna = scanner.nextLine();
                        System.out.print("Nuovo Titolo: ");
                        String nuovoTitolo = scanner.nextLine();
                        System.out.print("Nuovo Anno di pubblicazione: ");
                        int nuovoAnno = scanner.nextInt();
                        System.out.print("Nuovo Numero di pagine: ");
                        int nuovoNumeroPagine = scanner.nextInt();
                        scanner.nextLine();

                        try {
                            archivio.aggiornaElemento(isbnAggiorna, nuovoTitolo, nuovoAnno, nuovoNumeroPagine);
                        } catch (Exception e) {
                            System.out.println("Errore durante l'aggiornamento: " + e.getMessage());
                        }
                        break;
                    case 9:
                        esci = true;
                        System.out.println("Arrivederci!");
                        break;
                    default:
                        System.out.println("Opzione non valida.");
                }
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
