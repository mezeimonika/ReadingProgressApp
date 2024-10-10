import java.util.Scanner;

public class ListaCarti {
    Carte[] lista;
    private int nr;
    public ListaCarti()
    {
        lista=new Carte[100];
        nr=0;
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
    public void alegeCarte()
    {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        System.out.println("Cartea aleasă: "+ lista[i-1]);

    }
    public void adaugaLog()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Număr de ore: ");

    }
    public void display()
    {
        for(int i=0; i<nr; i++)
            System.out.println(i+1+"."+lista[i]);
    }
}
