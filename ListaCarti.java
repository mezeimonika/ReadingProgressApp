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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Titlul cărții: ");
        carte.setTitlu(scanner.nextLine());
        System.out.print("Autorul cărții: ");
        carte.setAutor(scanner.nextLine());
        if (nr < 100) {
            lista[nr] = carte;
            nr++;
        }
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
}
