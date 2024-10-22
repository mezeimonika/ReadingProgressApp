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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.IOException;

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
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {

        this.primaryStage=primaryStage;
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

        updateListView(bookList);
        inputSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterBooksBySearch(newValue);
            }
        });
        filterBtn.setOnAction(e -> filterBooksBySearch());

        addBtn.setOnAction(e -> {
            try {
                addBook.addBookField(primaryStage);
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
                    BookDetailsView bookDetailsView = new BookDetailsView(listaCarti, selectedCarte, bookList, listView, this);
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

    private void updateListView(ObservableList<String> books) {
        listView.setItems(books);
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
        updateListView(filteredList);
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
        bookList.add(carte.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}