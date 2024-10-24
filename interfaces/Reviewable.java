package interfaces;

import Books.Carte;

public interface Reviewable {
    void submitReview(Carte selectedBook, String review, String rating);
}
