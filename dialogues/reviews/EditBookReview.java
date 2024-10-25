package dialogues.reviews;

import Books.BookDetailsView;
import Books.Carte;
import interfaces.Reviewable;
import javafx.stage.Stage;

import java.io.IOException;

public class EditBookReview implements Reviewable {
    private final BookDetailsView bookDetailsView;
    private final Stage primaryStage;

    public EditBookReview(BookDetailsView bookDetailsView, Stage primaryStage) {
        this.bookDetailsView = bookDetailsView;
        this.primaryStage = primaryStage;
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
        try {
            bookDetailsView.showDetails(primaryStage);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
