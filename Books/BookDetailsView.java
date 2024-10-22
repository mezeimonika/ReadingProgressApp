package Books;
import mainpackage.Main;
import dialogues.AddLogDialog;
import dialogues.ReviewDialog;
import dialogues.TimerDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class BookDetailsView {
    @FXML
    private TextField titleField, authorField;
    @FXML
    private Label titleLabel, authorLabel, shelfLabel, pagesLabel, timeRead, percent;
    @FXML
    private Button backBtn, startReading, addLogBtn, finishedBtn;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableView<Log> logTable;
    @FXML
    private TableColumn<Log, String> timeColumn;
    @FXML
    private TableColumn<Log, Integer> pageColumn;

    private ObservableList<String> bookList;
    private Carte selectedBook;
    private Main mainController;
    private ListView<String> listView;
    private ListaCarti listaCarti;


    public BookDetailsView(ListaCarti listaCarti, Carte selectedBook, ObservableList<String> bookList, ListView<String> listView, Main mainController) {
        this.selectedBook = selectedBook;
        this.bookList = bookList;
        this.listView = listView;
        this.mainController = mainController;
        this.listaCarti = listaCarti;
    }

    public void showDetails(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Books/book-details.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        titleLabel.setText(selectedBook.getTitlu());
        authorLabel.setText(selectedBook.getAutor());
        shelfLabel.setText("("+selectedBook.getShelf()+")");

        updateBookDetails();

        String paginiTotale= String.valueOf(selectedBook.getPagini());
        String paginiCitite=String.valueOf(selectedBook.getPaginiCitite());

        backBtn.setOnAction(e -> {
            try {
                mainController.createMainLayout(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        addLogBtn.setOnAction(e -> {
            try {
                showAddLogDialog(primaryStage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        finishedBtn.setOnAction(event -> {
            try {
                openReviewDialog(primaryStage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        startReading.setOnAction(event -> {
            try {
                openTimerDialog(primaryStage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        setupLogTable();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private String getTotalReadingTime(Carte selectedBook) {
        int totalMinutes = 0;
        int totalHours=0;
        for (Log log : selectedBook.afiseazaLoguri()) {
            totalMinutes += log.getMinute();
            totalHours+=log.getOre();
        }

        if (totalHours==0)
            return totalMinutes + "m";
        else
            if(totalMinutes==0)
                return totalHours+"h ";
            else if(totalMinutes>0 && totalHours>0)
            {
                if(totalMinutes>60)
                {
                    int remaining=totalMinutes%60;
                    totalHours+=totalMinutes/60;
                    return totalHours+"h "+remaining+"m";
                }
                else return totalHours+"h "+totalMinutes+"m";
            }


        return "00h 00m";
    }
    private void setupLogTable() {
        ObservableList<Log> logs = FXCollections.observableArrayList(selectedBook.afiseazaLoguri());
        logTable.setItems(logs);

        timeColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getFormattedTime());
        });

        pageColumn.setCellValueFactory(new PropertyValueFactory<>("pagini"));
    }


    public void updateBookDetails() {
        pagesLabel.setText("Pages read\n" + selectedBook.getPaginiCitite() + "/" + selectedBook.getPagini());
        timeRead.setText("Read time\n" + getTotalReadingTime(selectedBook));
        if (selectedBook.getPaginiCitite() > 0 && selectedBook.getPaginiCitite() < selectedBook.getPagini()) {
            selectedBook.setShelf("Currently Reading");
            startReading.setText("Continue");
        } else if (selectedBook.getPaginiCitite() == selectedBook.getPagini()) {
            selectedBook.setShelf("Read");
            startReading.setText("Finish");
        } else {
            selectedBook.setShelf("Want to read");
            startReading.setText("Start");
        }

        shelfLabel.setText("(" + selectedBook.getShelf() + ")");

        double progress = (double) selectedBook.getPaginiCitite() / selectedBook.getPagini();
        progressBar.setProgress(progress);
        int percent1 = (int) ((progress) * 100);
        percent.setText(percent1+"%");

        ObservableList<Log> logs = FXCollections.observableArrayList(selectedBook.afiseazaLoguri());
        logTable.setItems(logs);

        mainController.updateBookInList(selectedBook);
    }

    private void openTimerDialog(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Books/timer.fxml"));

        TimerDialog timerDialog = new TimerDialog(selectedBook, this);
        loader.setController(timerDialog);

        Parent root = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Timer");

        dialogStage.setScene(new Scene(root));
        dialogStage.initOwner(primaryStage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.showAndWait();
    }


    private void showAddLogDialog(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Books/add-log.fxml"));

        AddLogDialog addLog = new AddLogDialog(selectedBook, this);
        loader.setController(addLog);

        Parent root = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Reading Session");

        dialogStage.setScene(new Scene(root));
        dialogStage.initOwner(primaryStage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.showAndWait();
    }

    private void openReviewDialog(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Books/review-dialog.fxml"));

        ReviewDialog reviewDialog=new ReviewDialog(selectedBook, this);
        loader.setController(reviewDialog);

        Parent root = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Submit Review");

        dialogStage.setScene(new Scene(root));
        dialogStage.initOwner(primaryStage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.showAndWait();
    }

}
