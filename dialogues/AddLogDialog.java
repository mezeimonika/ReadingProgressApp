package dialogues;
import Books.Carte;
import Books.BookDetailsView;
import javafx.scene.control.DatePicker;
import Books.Log;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.time.LocalDate;

public class AddLogDialog {

    @FXML
    private TextField hoursField, minutesField, pagesField;
    @FXML
    private Button submitLogBtn, cancelBtn;
    @FXML
    private DatePicker datePicker;

    private Carte selectedBook;
    private BookDetailsView bookDetailsView;

    public AddLogDialog(Carte selectedBook, BookDetailsView bookDetailsView) {
        this.selectedBook = selectedBook;
        this.bookDetailsView = bookDetailsView;
    }

    @FXML
    public void initialize() {
        submitLogBtn.setOnAction(event -> {
            String hoursText = hoursField.getText();
            String minutesText = minutesField.getText();
            String pagesText = pagesField.getText();
            LocalDate logDate = datePicker.getValue();
            int hours = 0, minutes = 0, pages = 0;

            try {
                if (!hoursText.isEmpty()) {
                    hours = Integer.parseInt(hoursText);
                }
                if (!minutesText.isEmpty()) {
                    minutes = Integer.parseInt(minutesText);
                }
                if (!pagesText.isEmpty()) {

                    pages = Integer.parseInt(pagesText);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers.");
                return;
            }
            if (pages <= 0) {
                System.out.println("Invalid number of pages. Please enter a number between 1 and " + selectedBook.getPagini());
                return;
            }

            int totalPagesRead = selectedBook.getPaginiCitite() + pages;
            if (totalPagesRead > selectedBook.getPagini()) {
                System.out.println("Total pages read cannot exceed total pages in the book.");
                return;
            }
            selectedBook.adaugaLog(new Log(minutes, hours, 0,  pages, logDate));
            selectedBook.setPaginiCitite(pages);
            bookDetailsView.updateBookDetails();
            closeWindow();
        });

        cancelBtn.setOnAction(event -> closeWindow());
    }

    private void closeWindow() {
        Stage stage = (Stage) submitLogBtn.getScene().getWindow();
        stage.close();
    }
}
