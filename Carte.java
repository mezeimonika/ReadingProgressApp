public class Carte
{
    private String autor;
    private String titlu;
    private int ore;
    public Carte()
    {
        ore=0;
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
    public void setOre(int ore)
    {
        if(ore!=0)
            this.ore=ore;
    }

    public int getOre() {
        return ore;
    }

    public String toString()
    {
        if(ore!=0)
        return getTitlu()+ " de "+ getAutor()+" timp citit" +getOre();
    }

}
