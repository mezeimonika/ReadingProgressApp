import java.util.Scanner;

public class ListaCarti {
    Carte[] lista;
    private int nr;
    int i;
    public ListaCarti()
    {
        lista=new Carte[100];
        nr=0;
    }
    public int getNrCarti() {
        return nr;
    }

    public void adaugaCarte(Carte carte)
    {
        if (nr < lista.length) {
            lista[nr] = carte;
            nr++;
        }
    }

    public void removeCarte(Carte carte) {
        for (i = 0; i <nr; i++) {
            if (lista[i] == carte) {
                for (int j=i; j <nr-1; j++) {
                    lista[j] = lista[j + 1];
                }
                lista[nr-1] = null;
                nr--;
                return;
            }
        }
        System.out.println("Book not found in the list.");
    }
    public Carte findCarte(String selectedBook) {
        for (int i = 0; i < nr; i++) {
            Carte carte = lista[i];
            if (carte != null && carte.toString().equals(selectedBook)) {
                return carte;
            }
        }
        System.out.println("Book not found: " + selectedBook);
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nr; i++) {
            sb.append(i + 1).append(". ").append(lista[i].toString()).append("\n");
        }
        return sb.toString();
    }
}
