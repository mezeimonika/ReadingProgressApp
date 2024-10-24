package mainpackage;

import Books.AddBook;
import Books.BookDetailsView;
import Books.Carte;
import Books.ListaCarti;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @FXML
    private Button addBtn, backBtn, filterBtn;
    @FXML
    ListView<String> listView;
    @FXML
    private ChoiceBox<String> filterChoiceBox;
    @FXML
    private TextField inputSearch;

    public ListaCarti listaCarti;
    public ObservableList<String> bookList;
    private static final Carte[] allBooks = new Carte[1000];
    private static int bookCount = 0;
    AddBook addBook;

    @Override
    public void start(Stage primaryStage) throws IOException {

        bookList=FXCollections.observableArrayList();
        listaCarti = new ListaCarti();
        createMainLayout(primaryStage);
        primaryStage.show();
    }

    public void createMainLayout(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainpackage/main-page.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        listView.setStyle("-fx-font-size: 14px; -fx-border-color:  #7C7E73; -fx-border-width:2; -fx-background-color: #F6F4F0;");

        addBook = new AddBook(listaCarti,this);
        filterChoiceBox.setItems(FXCollections.observableArrayList( "All", "Want to Read", "Currently Reading", "Read"));
        filterChoiceBox.getSelectionModel().selectFirst();

        listView.setItems(bookList);
        inputSearch.textProperty().addListener((observable, oldValue, newValue) -> filterBooksBySearch(newValue));
        filterBtn.setOnAction(e -> filterBooksBySearch());

        addBtn.setOnAction(e -> {
            try {
                addBook.bookField(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        listView.setItems(bookList);
        listView.setOnMouseClicked(event -> {
            String selectedBook = listView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                Carte selectedCarte = listaCarti.findCarte(selectedBook);
                if (selectedCarte != null) {
                    BookDetailsView bookDetailsView = new BookDetailsView(listaCarti, selectedCarte, this);
                    try {
                        bookDetailsView.showDetails(primaryStage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }
    private void filterBooksBySearch(String searchQuery) {
        String selectedShelf = filterChoiceBox.getValue();
        ObservableList<String> filteredList = FXCollections.observableArrayList();

        String normalizedQuery = searchQuery.toLowerCase();


        for (int i = 0; i < bookCount; i++) {
            Carte carte = allBooks[i];
            boolean matchesSearch = carte.getTitlu().toLowerCase().contains(normalizedQuery) || carte.getAutor().toLowerCase().contains(normalizedQuery);

            if ("All".equals(selectedShelf)) {
                if (matchesSearch) {
                    filteredList.add(carte.toString());
                }
            } else {
                if (carte.getShelf().equals(selectedShelf) && matchesSearch) {
                    filteredList.add(carte.toString());
                }
            }
        }
        listView.setItems(filteredList);
    }
    private void filterBooksBySearch() {
        filterBooksBySearch(inputSearch.getText());
    }

    public void updateBookInList(Carte updatedBook) {
        for (int i = 0; i < bookCount; i++) {
            if (allBooks[i].getTitlu().equals(updatedBook.getTitlu())) {
                allBooks[i] = updatedBook;
                break;
            }
        }
        bookList.clear();
        for (int i = 0; i < bookCount; i++) {
            bookList.add(allBooks[i].toString());
        }

        listView.setItems(bookList);
    }

    public void addBookToList(Carte carte) {
        if (carte != null && bookCount < allBooks.length) {
            allBooks[bookCount] = carte;
            bookCount++;
        }
        listaCarti.adaugaCarte(carte);
        bookList.add(Objects.requireNonNull(carte).toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}