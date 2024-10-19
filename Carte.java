import java.util.ArrayList;

public class Carte
{
    private String autor;
    private String titlu;
    private String rating;
    private ArrayList<Log> loguri;
    private String shelf;
    private int pagini;
    public Carte()
    {
        loguri = new ArrayList<>();
        rating="";
        shelf="";
        pagini=0;
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
    public void setPagini(int pagini)
    {
        if(pagini!=0)
            this.pagini=pagini;
    }

    public int getPagini() {
        return pagini;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setShelf(String shelf) {
        this.shelf=shelf;
    }

    public String getShelf()
    {
        return shelf;
    }
    public void adaugaLog(Log log)
    {
        loguri.add(log);
    }
    public ArrayList<Log> afiseazaLoguri() {
        return loguri;
    }
    public void deleteLastLog() {
        if (!loguri.isEmpty()) {
            loguri.remove(loguri.size() - 1);
        } else {
            System.out.println("No logs to delete.");
        }
    }
    public String toString() {
if(rating==null)
            return getTitlu() + " by " + getAutor();
else return getTitlu() + " by " + getAutor()+ " "+getRating();

    }


}
