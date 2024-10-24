package interfaces;

import Books.BookDetailsView;
import Books.Carte;
import javafx.stage.Stage;
import java.io.IOException;

public interface BookHandler {
    void bookField(Stage primaryStage) throws IOException;
    void openReviewDialog(Stage primaryStage, Carte carte, Reviewable reviewHandler) throws IOException;
}
