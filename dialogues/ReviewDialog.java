package dialogues;
import Books.Carte;
import Books.BookDetailsView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ReviewDialog {
    @FXML
    private TextArea reviewTextArea;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    private Carte selectedBook;
    BookDetailsView bookDetailsView;
    public ReviewDialog(Carte selectedBook, BookDetailsView bookDetailsView) {
        this.selectedBook = selectedBook;
        this.bookDetailsView = bookDetailsView;
    }
    @FXML
    public void initialize() {
        cancelButton.setOnAction(event -> closeWindow());
        submitButton.setOnAction(event -> {
            String review = reviewTextArea.getText();
            selectedBook.setShelf("Read");
            selectedBook.setReview(review);
            selectedBook.setPaginiCitite(selectedBook.getPagini()- selectedBook.getPaginiCitite());
            bookDetailsView.updateBookDetails();
            closeWindow();

        });
    }

    private void closeWindow() {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

}
