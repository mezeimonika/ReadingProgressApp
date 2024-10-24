package Books;

public class ListaCarti {
    Carte[] lista;
    private int nr;
    public ListaCarti()
    {
        lista=new Carte[1000];
        nr=0;
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

}
