import java.util.ArrayList;

public class Carte
{
    private String autor;
    private String titlu;
    private double rating;
    private ArrayList<Log> loguri;
    public Carte()
    {
        loguri = new ArrayList<>();
        rating=0.0;
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
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

            return getTitlu() + " by " + getAutor();

    }

}
