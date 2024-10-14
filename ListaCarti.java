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
    public int alegeCarte()
    {
        Scanner scanner = new Scanner(System.in);
        i = scanner.nextInt();
        System.out.println("Cartea aleasă: "+ lista[i-1]);
        return i;
    }
    public void adaugaLog(int j)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Număr de minute: ");
        Carte carte=lista[j-1];
        int minute=scanner.nextInt();
        Log log = new Log(minute); // Creează un nou log
        carte.adaugaLog(log);
        System.out.println("Log adăugat pentru: " + carte);
    }
    public void afiseazaLog(int j)
    {
        if(j>0 && j<=nr)
        {
            Carte carte=lista[j-1];
            carte.afiseazaLoguri();
        }
    }
    public void display()
    {
        for(i=0; i<nr; i++)
            System.out.println(i+1+"."+lista[i]);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nr; i++) {
            sb.append(i + 1).append(". ").append(lista[i].toString()).append("\n");
        }
        return sb.toString();
    }
}
