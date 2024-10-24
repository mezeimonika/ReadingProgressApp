package Books;

import sessions.Log;

import java.util.ArrayList;

public class Carte
{
    private String autor;
    private String titlu;
    private String rating;
    private final ArrayList<Log> loguri;
    private String shelf;
    private int pagini;
    private int paginiCitite;
    private String review;
    public Carte()
    {
        autor="";
        titlu="";
        loguri = new ArrayList<>();
        rating="";
        shelf="";
        pagini=0;
        paginiCitite=0;
        review="";
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
    public void setPaginiCitite(int paginiCitite)
    {
        if(paginiCitite!=0)
        {
            this.paginiCitite+=paginiCitite;
        }
    }

    public int getPaginiCitite() {
        return paginiCitite;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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

    public String toString() {
        String s = getTitlu() + " by " + getAutor() +"                    "+"("+getShelf()+")";

        if ("Read".equals(getShelf()) && getRating() != null && !getRating().isEmpty()) {
            s += "                    " + convertRatingToStars(getRating());
        }
        return s;
    }

    private String convertRatingToStars(String rating) {
        int ratingValue;

        try {
            ratingValue = Integer.parseInt(rating);
        } catch (NumberFormatException e) {
            ratingValue = 0;
        }

        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < ratingValue) {
                stars.append("★");
            } else {
                stars.append("☆");
            }
        }

        return stars.toString();
    }



}
