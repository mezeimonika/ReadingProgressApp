package dialogues;

import Books.BookDetailsView;
import Books.Carte;
import interfaces.Reviewable;

public class EditBookReview implements Reviewable {
    private final BookDetailsView bookDetailsView;
    private final Carte selectedBook;

    public EditBookReview(BookDetailsView bookDetailsView, Carte selectedBook) {
        this.bookDetailsView = bookDetailsView;
        this.selectedBook = selectedBook;
    }

    @Override
    public void submitReview(Carte selectedBook, String review, String rating) {
        selectedBook.setReview(review);
        selectedBook.setRating(rating);
        selectedBook.setShelf("Read");

        selectedBook.setPaginiCitite(selectedBook.getPagini() - selectedBook.getPaginiCitite());
        bookDetailsView.updateBookDetails(selectedBook);
        bookDetailsView.updateReviewAndRating(review, rating);
        bookDetailsView.setVisibility();
    }
}
