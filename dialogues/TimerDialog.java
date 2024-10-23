package dialogues;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import Books.BookDetailsView;
import Books.Log;
import Books.Carte;

public class TimerDialog {
    @FXML
    private Label timeLabel;
    @FXML
    private Button startStopButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button resetButton;

    private Timer timer;
    private TimerTask timerTask;
    private long elapsedTime;
    private boolean running;
    public Carte selectedBook;
    private BookDetailsView bookDetailsView;

    public TimerDialog(Carte selectedBook, BookDetailsView bookDetailsView) {
        this.selectedBook=selectedBook;
        this.bookDetailsView=bookDetailsView;
        elapsedTime = 0;
        running = false;
    }

    @FXML
    public void initialize() {
        startStopButton.setOnAction(event -> toggleTimer());
        stopButton.setOnAction(event -> stopTimer());
        resetButton.setOnAction(event -> resetTimer());
        bookDetailsView.updateBookDetails();
    }

    private void toggleTimer() {
        if (running) {
            pauseTimer();
        } else {
            startTimer();
        }
    }

    private void startTimer() {
        running = true;
        startStopButton.setText("Pause");
        timer = new Timer();
        timerTask = new TimerTask() {
            private long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                elapsedTime += System.currentTimeMillis() - startTime;
                startTime = System.currentTimeMillis();
                long seconds = (elapsedTime / 1000) % 60;
                long minutes = (elapsedTime / (1000 * 60)) % 60;
                long hours = (elapsedTime / (1000 * 3600));
                String timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                Platform.runLater(() -> {
                    updateLabel(timeText);
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void pauseTimer()
    {
        if(running)
        {
            timer.cancel();
            running = false;
            startStopButton.setText("Resume");
        }
    }
    private void stopTimer() {
        if (running) {
            timer.cancel();
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Pages Read");
            dialog.setHeaderText("How many pages did you read?");
            dialog.setContentText("Please enter the number of pages:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(pagesStr -> {
                try {
                    int pages = Integer.parseInt(pagesStr);
                    saveElapsedTime(pages);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number of pages entered.");
                }
            });
            bookDetailsView.updateBookDetails();
            closeDialog();
        }
    }

    private void resetTimer() {
        stopTimer();
        elapsedTime = 0;
        updateLabel("00:00:00");
    }

    private void updateLabel(String timeText) {
        timeLabel.setText(timeText);
    }

    private void saveElapsedTime(int pages) {
        int seconds = (int) ((elapsedTime / 1000) % 60);
        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
        int hours = (int) (elapsedTime / (1000 * 3600));
        LocalDate date = LocalDate.now();
        Log logEntry = new Log(minutes, hours, seconds, pages, date);

        selectedBook.adaugaLog(logEntry);
        selectedBook.setPaginiCitite(pages);
        bookDetailsView.updateBookDetails();
    }

    public void closeDialog() {
        Stage stage = (Stage) startStopButton.getScene().getWindow();
        stage.close();
    }
}
