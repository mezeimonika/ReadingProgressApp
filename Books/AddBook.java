package Books;
import dialogues.reviews.NewBookReview;
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

public class AddBook implements BookHandler {
    @FXML
    private Button addBtn1, backToLibraryButton;
    @FXML
    TextField titleField, authorField, nrPages;
    @FXML
    ChoiceBox<String> shelfChoiceBox;
    ListaCarti listaCarti;
    public Main mainController;

    public AddBook(ListaCarti listaCarti, Main mainController) {
        this.mainController = mainController;
        this.listaCarti=listaCarti;
    }

    public void bookField(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-book.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        shelfChoiceBox.setItems(FXCollections.observableArrayList("Want to Read", "Currently Reading", "Read"));
        shelfChoiceBox.getSelectionModel().selectFirst();

        addBtn1.setOnAction(e -> {

            String titlu = titleField.getText();
            String autor = authorField.getText();
            String shelf = shelfChoiceBox.getValue();
            String pagini = nrPages.getText();
            if (titlu.isEmpty() || autor.isEmpty() || shelf == null || pagini.isEmpty()) {
                System.out.println("Please enter all fields.");
                return;
            }
            Carte carte=new Carte();
            try {
                int nrPagini = Integer.parseInt(pagini);
                carte.setPagini(nrPagini);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number format for pages.");
                return;
            }


            carte.setTitlu(titlu);
            carte.setAutor(autor);
            carte.setShelf(shelf);
            if (Objects.equals(shelf, "Read")) {
                carte.setPaginiCitite(carte.getPagini() - carte.getPaginiCitite());

                Reviewable reviewHandler = new NewBookReview(mainController, primaryStage);
                try {
                    openReviewDialog(primaryStage, carte, reviewHandler);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            mainController.addBookToList(carte);

            titleField.clear();
            authorField.clear();
            nrPages.clear();
            shelfChoiceBox.getSelectionModel().clearSelection();

            try {
                mainController.createMainLayout(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        backToLibraryButton.setOnAction(e -> {
            try {
                mainController.createMainLayout(primaryStage);
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