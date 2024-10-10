public class Carte
{
    private String autor;
    private String titlu;
    Log[] loguri;
    int nr;
    public Carte()
    {
        loguri=new Log[300];
        nr=0;
    }
    public String getAutor()
    {
        return autor;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setAutor(String autor)
    {
        if(autor!=null)
            this.autor = autor;
    }

    public void setTitlu(String titlu)
    {
       if(titlu!=null)
        this.titlu = titlu;
    }

    public void adaugaLog(Log log)
    {
        loguri[nr]=log;
        nr++;
    }
    public void afiseazaLoguri()
    {
        if(loguri==null)
            System.out.println("Nu există loguri pentru această carte.");
        else
        {
            System.out.println("Loguri pentru " + titlu + ": ");
            for(int i=0; i<nr; i++)
                System.out.println("Logul numarul "+i +": "+loguri[i]);
        }

    }
    public String toString() {

            return getTitlu() + " de " + getAutor();

    }

}
