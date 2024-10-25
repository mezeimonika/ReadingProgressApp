package Books;

import sessions.Log;

public class ExtendedBook extends Carte {

    public ExtendedBook() {
        super();

    }
    public void copyFrom(Carte book)
    {
        this.setAutor(book.getAutor());
        this.setTitlu(book.getTitlu());
        this.setRating(book.getRating());
        this.setShelf(book.getShelf());
        this.setPagini(book.getPagini());
        this.setPaginiCitite(book.getPaginiCitite());
        this.setReview(book.getReview());
        this.getLoguri().addAll(book.afiseazaLoguri());
    }

    public String predictReadingTime() {
        int totalPages = getPagini();
        int pagesRead = getPaginiCitite();
        int pagesLeft = totalPages - pagesRead;

        int totalMinutes = 0;
        int totalHours = 0;
        for (Log log : afiseazaLoguri()) {
            totalHours += log.getOre();
            totalMinutes += log.getMinute();
        }
        totalMinutes+=totalHours*60;
        if (pagesRead > 0 && (totalHours > 0 || totalMinutes > 0)) {
            double readingSpeed = (double) pagesRead / totalMinutes;
            int predictedTimeInMinutes = (int) Math.ceil(pagesLeft / readingSpeed);

            int predictedHours = predictedTimeInMinutes / 60;
            int predictedMinutes = predictedTimeInMinutes % 60;

            return String.format("%dh %dm", predictedHours, predictedMinutes);
        } else {
            return "Not enough data to predict remaining reading time.";
        }
    }

}
