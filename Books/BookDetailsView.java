package Books;
import dialogues.EditBookReview;
import interfaces.Reviewable;
import javafx.scene.layout.VBox;
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
import sessions.Log;

import java.io.IOException;
import java.util.Objects;

public class BookDetailsView {
    @FXML
    private Label titleLabel, authorLabel, shelfLabel, pagesLabel, timeRead, percent, reviewLabel, ratingLabel, predictionLabel;
    @FXML
    private Button backBtn, startReading, addLogBtn, finishedBtn, editBtn;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableView<Log> logTable;
    @FXML
    private TableColumn<Log, String> timeColumn;
    @FXML
    private TableColumn<Log, Integer> pageColumn;
    @FXML
    private VBox reviewVBox;

    private final Carte selectedBook;
    private final Main mainController;
    private final ListaCarti listaCarti;
    EditBook editBook;

    public BookDetailsView(ListaCarti listaCarti, Carte selectedBook, Main mainController) {
        this.selectedBook = selectedBook;
        this.mainController = mainController;
        this.listaCarti = listaCarti;
    }

    public void showDetails(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Books/book-details.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        reviewVBox.setVisible(false);
        titleLabel.setText(selectedBook.getTitlu());
        authorLabel.setText(selectedBook.getAutor());
        shelfLabel.setText("("+selectedBook.getShelf()+")");

        updateBookDetails(selectedBook);

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
        editBook=new EditBook(listaCarti, mainController, selectedBook, this);
        editBtn.setOnAction(e->
        {
            try {
                editBook.bookField(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
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
        int totalSeconds=0;
        for (Log log : selectedBook.afiseazaLoguri()) {
            totalMinutes += log.getMinute();
            totalHours+=log.getOre();
            totalSeconds+=log.getSecunde();
        }

        if (totalSeconds >= 60) {
            totalMinutes += totalSeconds / 60;
            totalSeconds = totalSeconds % 60;
        }

        if (totalMinutes >= 60) {
            totalHours += totalMinutes / 60;
            totalMinutes = totalMinutes % 60;
        }

        if (totalHours == 0) {
            if (totalMinutes == 0) {
                if(totalSeconds==0)
                {
                    return "00h 00m 00s";
                }
                else return totalSeconds + "s";
            }
            else return totalMinutes + "m " + totalSeconds + "s";
        }else if (totalMinutes == 0) {
            if(totalSeconds==0)
            {
                return totalHours + "h ";
            }
            else return totalHours+"h "+totalSeconds+"s";
        }else if(totalSeconds==0)
        {
            return totalHours+"h "+totalMinutes+"m";
        }
        else {
            return totalHours+"h "+totalMinutes+"m "+totalSeconds+"s";
        }

    }

    private void setupLogTable() {
        ObservableList<Log> logs = FXCollections.observableArrayList(selectedBook.afiseazaLoguri());
        logTable.setItems(logs);

        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFormattedTime()));

        pageColumn.setCellValueFactory(new PropertyValueFactory<>("pagini"));
    }


    public void updateBookDetails(Carte selectedBook) {
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
        if(Objects.equals(selectedBook.getShelf(), "Read"))
        {
            updateReviewAndRating(selectedBook.getReview(), selectedBook.getRating());
            setVisibility();
        }
        predictReadingTime(selectedBook);

        mainController.updateBookInList(selectedBook);
    }
    private void predictReadingTime(Carte selectedBook) {
        int totalPages = selectedBook.getPagini();
        int pagesRead = selectedBook.getPaginiCitite();
        int pagesLeft = totalPages - pagesRead;

        int totalHours = 0;
        int totalMinutes = 0;
        int totalSeconds = 0;
        for (Log log : selectedBook.afiseazaLoguri()) {
            totalHours += log.getOre();
            totalMinutes += log.getMinute();
            totalSeconds += log.getSecunde();
        }
        totalMinutes += totalSeconds / 60;
        totalSeconds = totalSeconds % 60;
        totalHours += totalMinutes / 60;
        totalMinutes = totalMinutes % 60;
        if (pagesRead > 0 && (totalHours > 0 || totalMinutes > 0)) {
            int totalTimeInMinutes = (totalHours * 60) + totalMinutes;
            double readingSpeed = (double) pagesRead / totalTimeInMinutes;
            int predictedTimeInMinutes = (int) Math.ceil(pagesLeft / readingSpeed);
            int predictedHours = predictedTimeInMinutes / 60;
            int predictedMinutes = predictedTimeInMinutes % 60;
            predictionLabel.setText(String.format("Estimated time to finish: %dh %dm", predictedHours, predictedMinutes));
        } else {
            predictionLabel.setText("Not enough data to predict remaining reading time.");
        }
    }

    private void openTimerDialog(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogues/timer.fxml"));

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogues/add-log.fxml"));

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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogues/review-dialog.fxml"));

        Reviewable reviewHandler = new EditBookReview(this, selectedBook);
        ReviewDialog reviewDialog=new ReviewDialog(selectedBook, reviewHandler);
        loader.setController(reviewDialog);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/dialogues/stars.css")).toExternalForm());

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Submit Review");

        dialogStage.setScene(scene);
        dialogStage.initOwner(primaryStage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.showAndWait();
    }
    public void updateReviewAndRating(String review, String rating) {
        reviewLabel.setText(selectedBook.getPaginiCitite()+"/"+ selectedBook.getPagini()+"\n"+review);
        int ratingValue;
        try {
            ratingValue = Integer.parseInt(rating);
        } catch (NumberFormatException e) {
            ratingValue = 0;
        }
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < ratingValue) {
                stars.append("★");
            } else {
                stars.append("☆");
            }
        }
        ratingLabel.setText("Rating: " + stars);
    }
    public void setVisibility()
    {
        reviewVBox.setVisible(true);

        pagesLabel.setVisible(false);
        timeRead.setVisible(false);
        startReading.setVisible(false);
        addLogBtn.setVisible(false);
        finishedBtn.setVisible(false);
        predictionLabel.setVisible(false);
    }


}
