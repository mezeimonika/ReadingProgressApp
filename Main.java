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
import java.util.Collections;
import java.util.Comparator;

public class Main extends Application {
    @FXML
    private Button addBtn, backBtn, filterBtn, sortButton;
    @FXML
    ListView<String> listView;
    @FXML
    private ChoiceBox<String> filterChoiceBox;
    @FXML
    private TextField inputSearch;

    private ListaCarti listaCarti;
    public ObservableList<String> bookList;
    private ObservableList<Carte> allBooks;
    AddBook addBook;
    private Stage primaryStage;
    private boolean isSortedAscending = true;

    @Override
    public void start(Stage primaryStage) throws IOException {

        this.primaryStage=primaryStage;
        bookList=FXCollections.observableArrayList();
        allBooks = FXCollections.observableArrayList();
        createMainLayout(primaryStage);
        primaryStage.show();
    }

    public Parent createMainLayout(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        listaCarti = new ListaCarti();
        listView.setItems(bookList);
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

        sortButton.setOnAction(e->
        {
            sortBooks();
            rotateButton();
        });

        addBtn.setOnAction(e -> {
            try {
                addBook.addBookField(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        return root;
    }

    private void updateListView(ObservableList<String> books) {
        listView.setItems(books);
    }
    private void filterBooksBySearch(String searchQuery) {
        String selectedShelf = filterChoiceBox.getValue();
        ObservableList<String> filteredList = FXCollections.observableArrayList();

        String normalizedQuery = searchQuery.toLowerCase();

        for (Carte carte : allBooks) {
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

    private void sortBooks() {
        if (isSortedAscending) {
            Collections.sort(allBooks, Comparator.comparingInt(Carte::getPagini));
        } else {
            Collections.sort(allBooks, Comparator.comparingInt(Carte::getPagini).reversed());
        }
        updateBookList();
        isSortedAscending = !isSortedAscending;
    }
    private void updateBookList() {
        bookList.clear();
        for (Carte carte : allBooks) {
            bookList.add(carte.toString());
        }
        listView.setItems(bookList);
    }

    private void rotateButton() {
        sortButton.setRotate(sortButton.getRotate() + 180);
    }

    public void addBookToList(Carte carte) {
        allBooks.add(carte);
        bookList.add(carte.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
