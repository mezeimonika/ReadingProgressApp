import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddBook {
    @FXML
    private Button addBtn1, backToLibraryButton;
    @FXML
    TextField titleField, authorField, nrPages;
    @FXML
    ChoiceBox<String> shelfChoiceBox;

    private Main mainController=new Main();
    private ListaCarti listaCarti;

    public AddBook( ListaCarti listaCarti, Main mainController) {
        this.listaCarti = listaCarti;
        this.mainController = mainController;
    }

    public void addBookField(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-book.fxml"));
        loader.setController(this);
        Parent root1 = loader.load();

        shelfChoiceBox.setItems(FXCollections.observableArrayList("Want to Read", "Currently Reading", "Read"));
        shelfChoiceBox.getSelectionModel().selectFirst();

        addBtn1.setOnAction(e -> {
            Carte carte = new Carte();
            String titlu = titleField.getText();
            String autor = authorField.getText();
            String shelf = shelfChoiceBox.getValue();
            String pagini= nrPages.getText();
            if (titlu.isEmpty() || autor.isEmpty() || shelf==null || pagini.isEmpty()) {
                System.out.println("Please enter all fields.");
                return;
            }

            carte.setTitlu(titlu);
            carte.setAutor(autor);
            carte.setShelf(shelf);
            carte.setPagini(Integer.parseInt(pagini));

            listaCarti.adaugaCarte(carte);

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

        Scene scene = new Scene(root1);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
