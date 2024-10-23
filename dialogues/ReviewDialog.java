package dialogues;

import Books.Carte;
import Books.BookDetailsView;
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

    private Carte selectedBook;
    BookDetailsView bookDetailsView;
    private String review;
    private String rating;
    public ReviewDialog(Carte selectedBook, BookDetailsView bookDetailsView) {
        this.selectedBook = selectedBook;
        this.bookDetailsView = bookDetailsView;
    }
    public String getReview() {
        return review;
    }

    public String getRating() {
        return rating;
    }

    @FXML
    public void initialize() {
        cancelButton.setOnAction(event -> closeWindow());
        submitButton.setOnAction(event -> {
            review = reviewTextArea.getText();
            rating = getSelectedRating();
            selectedBook.setShelf("Read");
            selectedBook.setReview(review);
            selectedBook.setRating(rating);
            selectedBook.setPaginiCitite(selectedBook.getPagini() - selectedBook.getPaginiCitite());
            bookDetailsView.updateBookDetails();
            bookDetailsView.updateReviewAndRating(review, rating);

            closeWindow();
        });


    }

    private int getStarIndex(RadioButton radioButton) {
        switch (radioButton.getId()) {
            case "star1": return 1;
            case "star2": return 2;
            case "star3": return 3;
            case "star4": return 4;
            case "star5": return 5;
            default: return 0;
        }
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
