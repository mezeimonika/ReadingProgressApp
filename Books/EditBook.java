package Books;

import dialogues.reviews.EditBookReview;
import dialogues.reviews.ReviewDialog;
import interfaces.BookHandler;
import interfaces.Reviewable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainpackage.Main;

import java.io.IOException;
import java.util.Objects;

public class EditBook implements BookHandler {
    @FXML
    private Button addBtn1, backToLibraryButton;
    @FXML
    TextField titleField, authorField, nrPages;
    @FXML
    ChoiceBox<String> shelfChoiceBox;
    ListaCarti listaCarti;
    Carte selectedBook;
    BookDetailsView bookDetailsView;
    public Main mainController;
    public EditBook(ListaCarti listaCarti, Main mainController, Carte selectedBook, BookDetailsView bookDetailsView) {
        this.mainController = mainController;
        this.listaCarti=listaCarti;
        this.selectedBook=selectedBook;
        this.bookDetailsView=bookDetailsView;
    }
    @Override
    public void bookField(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Books/add-book.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        shelfChoiceBox.setItems(FXCollections.observableArrayList("Want to Read", "Currently Reading", "Read"));

        titleField.setText(selectedBook.getTitlu());
        authorField.setText(selectedBook.getAutor());
        nrPages.setText(String.valueOf(selectedBook.getPagini()));
        shelfChoiceBox.setValue(selectedBook.getShelf());

        addBtn1.setText("Save");
        addBtn1.setOnAction(e -> {

            String titlu_edit = titleField.getText();
            String autor_edit = authorField.getText();
            String shelf_edit = shelfChoiceBox.getValue();
            String pagini_edit = nrPages.getText();


            if (titlu_edit.isEmpty() || autor_edit.isEmpty() || shelf_edit==null || pagini_edit.isEmpty()) {
                System.out.println("Please enter all fields.");
                return;
            }
            try {
                int nrPagini = Integer.parseInt(pagini_edit);
                selectedBook.setPagini(nrPagini);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number format for pages.");
                return;
            }
            selectedBook.setTitlu(titlu_edit);
            selectedBook.setAutor(autor_edit);
            selectedBook.setShelf(shelf_edit);
            if (Objects.equals(shelf_edit, "Read")) {
                selectedBook.setPaginiCitite(selectedBook.getPagini() - selectedBook.getPaginiCitite());

                Reviewable reviewHandler = new EditBookReview(bookDetailsView, primaryStage);
                try {
                    openReviewDialog(primaryStage, selectedBook, reviewHandler);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            bookDetailsView.updateBookDetails(selectedBook);

            titleField.clear();
            authorField.clear();
            nrPages.clear();
            shelfChoiceBox.getSelectionModel().clearSelection();

            try {
                bookDetailsView.showDetails(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        backToLibraryButton.setOnAction(e -> {
            try {
                bookDetailsView.showDetails(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void openReviewDialog(Stage primaryStage, Carte carte, Reviewable reviewHandler) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogues/reviews/review-dialog.fxml"));

        ReviewDialog reviewDialog = new ReviewDialog(carte, reviewHandler);
        loader.setController(reviewDialog);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/dialogues/reviews/stars.css")).toExternalForm());

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Submit Review");

        dialogStage.setScene(scene);
        dialogStage.initOwner(primaryStage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.showAndWait();
    }
}
