import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private ListaCarti listaCarti;
    private ObservableList<String> bookList;
    private ListView<String> listView;
    @Override
    public void start(Stage primaryStage) {
        listaCarti = new ListaCarti();
        bookList = FXCollections.observableArrayList();
        primaryStage.setTitle("My library");

        listView = new ListView<>();
        listView.setItems(bookList);

        listView.setOnMouseClicked(event -> {
            String selectedBook = listView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                Carte selectedCarte = findCarte(selectedBook);;
                if (selectedCarte != null) {
                    new BookDetailsView(selectedCarte, primaryStage, listaCarti, bookList);
                }
            }
        });

        Button addBtn = new Button("Add");

        addBtn.setOnAction(e -> addBookField(primaryStage));

        VBox layout = new VBox(20);
        layout.getChildren().addAll(
                new Label("List of Books:"), listView,
                addBtn
        );
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addBookField(Stage primaryStage)
    {
        TextField titleField = new TextField();
        titleField.setPromptText("Enter book title");
        TextField authorField = new TextField();
        authorField.setPromptText("Enter book author");
        Button addBtn1 = new Button("Add");
        addBtn1.setOnAction(e -> {
            Carte carte = new Carte();
            String titlu = titleField.getText();
            String autor = authorField.getText();

            if (titlu.isEmpty() || autor.isEmpty()) {
                System.out.println("Please enter both title and author.");
                return;
            }
            carte.setTitlu(titlu);
            carte.setAutor(autor);
            listaCarti.adaugaCarte(carte);
            bookList.add(carte.toString());
            titleField.clear();
            authorField.clear();
            primaryStage.getScene().setRoot(createMainLayout());
        });

        VBox inputLayout = new VBox(10);
        inputLayout.getChildren().addAll(
                new Label("Book Title:"), titleField,
                new Label("Book Author:"), authorField,
                addBtn1
        );
        primaryStage.getScene().setRoot(inputLayout);
    }

    private VBox createMainLayout() {
        listView.setItems(bookList);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
              addBookField((Stage) addButton.getScene().getWindow());
        });

        return new VBox(20, new Label("List of Books:"), listView, addButton);
    }

    private Carte findCarte(String title) {
        for (Carte carte : listaCarti.lista) {
            if (carte.toString().equals(title)) {
                return carte;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
