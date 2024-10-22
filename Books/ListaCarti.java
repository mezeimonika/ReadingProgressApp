package Books;

public class ListaCarti {
    Carte[] lista;
    private int nr;
    int i;
    public ListaCarti()
    {
        lista=new Carte[1000];
        nr=0;
    }
    public int getNrCarti() {
        return nr;
    }

    public void adaugaCarte(Carte carte) {
        if (carte == null) {
            System.out.println("Cannot add a null book to the list.");
            return;
        }
        if (nr < lista.length) {
            lista[nr] = carte;
            nr++;
        } else {
            System.out.println("The list is full. Cannot add more books.");
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
            if (lista[i] != null) {
                if (lista[i].toString().equals(selectedBook)) {
                    return lista[i];
                }
            }
        }
        System.out.println("Book not found: " + selectedBook);
        return null;
    }
    public void printBooks() {
        System.out.println("Current books in Books.ListaCarti:");
        for (int i = 0; i < nr; i++) {
            if (lista[i] != null) {
                System.out.println(lista[i].toString()+" end");
            }
        }
    }


}
