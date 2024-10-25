package dialogues.reviews;

import Books.AddBook;
import Books.Carte;
import interfaces.Reviewable;
import javafx.stage.Stage;
import mainpackage.Main;

import java.io.IOException;

public class NewBookReview implements Reviewable {
    private final Main mainController;
    private final Stage primaryStage;

    public NewBookReview(Main mainController, Stage primaryStage) {
        this.mainController = mainController;
        this.primaryStage = primaryStage;
    }

    @Override
    public void submitReview(Carte selectedBook, String review, String rating) {
        selectedBook.setReview(review);
        selectedBook.setRating(rating);
        selectedBook.setShelf("Read");

        try {
            mainController.createMainLayout(primaryStage);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
