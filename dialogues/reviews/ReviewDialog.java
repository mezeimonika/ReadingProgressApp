package dialogues.reviews;

import Books.AddBook;
import Books.Carte;
import Books.BookDetailsView;
import interfaces.Reviewable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ReviewDialog {
    @FXML
    private TextArea reviewTextArea;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private RadioButton star1, star2, star3, star4, star5;
    @FXML
    private ToggleGroup toggleGroup;

    private final Carte selectedBook;
    BookDetailsView bookDetailsView;
    AddBook addBook;
    private String review;
    private String rating;
    private final Reviewable reviewHandler;


    public ReviewDialog(Carte selectedBook, Reviewable reviewHandler) {
        this.selectedBook = selectedBook;
        this.reviewHandler = reviewHandler;
    }

    @FXML
    public void initialize() {
        reviewTextArea.setText(selectedBook.getReview());
        cancelButton.setOnAction(event -> closeWindow());
        submitButton.setOnAction(event -> {

            review = reviewTextArea.getText();
            rating = getSelectedRating();
            reviewHandler.submitReview(selectedBook, review, rating);
            closeWindow();
        });


    }

    private int getStarIndex(RadioButton radioButton) {
        return switch (radioButton.getId()) {
            case "star1" -> 1;
            case "star2" -> 2;
            case "star3" -> 3;
            case "star4" -> 4;
            case "star5" -> 5;
            default -> 0;
        };
    }

    private String getSelectedRating() {
        if (toggleGroup.getSelectedToggle() != null) {
            return getStarIndex((RadioButton) toggleGroup.getSelectedToggle()) + "";
        }
        return "1";
    }

    private void closeWindow() {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
}
