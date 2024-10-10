import java.util.Scanner;

public class Main
{
    static ListaCarti listaCarti;
    static Carte carte;
    private static int meniu;
    public static void main(String[] args)
    {
        listaCarti=new ListaCarti();

        do {
            System.out.println("\n0.Ieșire din program.\n1.Adaugă o carte.\n2.Lista ta de cărți.\n3.Adaugă un log.\n4.Șterge o carte.\n5.Șterge un log.");
            Scanner scanner=new Scanner(System.in);
            meniu=scanner.nextInt();
            switch (meniu) {
                case 0: {
                    break;
                }
                case 1: {
                    Scanner scanner1=new Scanner(System.in);
                    System.out.println("Câte cărți adaugi?");
                    int j=scanner1.nextInt();
                            for(int i=0; i<j; i++)
                            {
                                carte=new Carte();
                                listaCarti.adaugaCarte(carte);
                            }
                    break;
                }
                case 2: {
                    listaCarti.display();
                    break;
                }
                case 3: {
                    System.out.println("Alege cartea din listă: ");
                    listaCarti.display();
                    listaCarti.alegeCarte();
                    break;
                }
            }
        }while(meniu!=0);
    }
}