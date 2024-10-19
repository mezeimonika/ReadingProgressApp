//import javafx.collections.ObservableList;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class BookDetailsView {
//    private ObservableList<String> bookList;
//    private Carte selectedBook;
//    private Label ratingLabel;
//    private Label logLabel;
//    private ListaCarti listaCarti;
//    private Main mainController;
//    AddBook addBook;
//    ListView<String> listView;
//    public BookDetailsView(Carte selectedBook, Stage primaryStage, ListaCarti listaCarti, ObservableList<String> bookList, ListView<String> listView) {
//        this.selectedBook = selectedBook;
//        this.listaCarti = listaCarti;
//        this.listView = listView;
//        this.mainController=mainController;
//        GridPane buttonLayout;
//
//        logLabel = new Label("Reading Time: " +getTotalReadingTime(selectedBook));
//
//        TextField logField = new TextField();
//        logField.setPromptText("Enter reading time (in minutes)");
//
//        Button submitLogButton = new Button("Submit Log");
//        submitLogButton.setOnAction(e -> {
//            String logText = logField.getText();
//            if (!logText.isEmpty()) {
//                try {
//                    int minutes = Integer.parseInt(logText);
//                    selectedBook.adaugaLog(new Log(minutes));
//                    updateLogLabel();
//                } catch (NumberFormatException ex) {
//                    System.out.println("Invalid time format.");
//                }
//            }
//        });
//
//        Button deleteLastLogButton = new Button("Delete Last Log");
//        deleteLastLogButton.setOnAction(e -> {
//            selectedBook.deleteLastLog();
//            updateLogLabel();
//        });
//        addBook=new AddBook(listaCarti,mainController);
//        Button deleteBookButton = new Button("Delete Book");
//        deleteBookButton.setOnAction(e -> {
//            listaCarti.removeCarte(selectedBook);
//            bookList.remove(selectedBook.toString());
////            try {
////                primaryStage.setScene(new Scene(addBook.createMainLayout(primaryStage), 400, 400));
////            } catch (IOException ex) {
////                throw new RuntimeException(ex);
////            }
//        });
//
//        Button backToLibraryButton = new Button("Back to Library");
//        backToLibraryButton.setOnAction(e -> {
////            try {
////                primaryStage.setScene(new Scene(addBook.createMainLayout(primaryStage), 400, 400));
////            } catch (IOException ex) {
////                throw new RuntimeException(ex);
////            }
//        });
//
//        Button finishedButton = new Button("Finished?");
//        finishedButton.setOnAction(e -> {
//            ratingLabel = new Label("Current Rating: " + (selectedBook.getRating() != 0.0 ? selectedBook.getRating() : "No rating yet"));
//
//            TextField ratingField = new TextField();
//            ratingField.setPromptText("Enter rating (1-5)");
//
//            Button deleteRatingButton = new Button("Delete Rating");
//            deleteRatingButton.setOnAction(e1 -> {
//                selectedBook.setRating("");
//                updateRatingLabel();
//
//            });
//
//            Button changeRatingButton = new Button("Change Rating");
//            changeRatingButton.setOnAction(e0-> {
//                String newRatingText = ratingField.getText();
//                if (!newRatingText.isEmpty()) {
//                    try {
//                        String rating = String.parseDouble(newRatingText);
//                        if (rating >= 1 && rating <= 5) {
//                            selectedBook.setRating(rating);
//                            updateRatingLabel();
//                        } else {
//                            System.out.println("Please enter a valid rating between 1 and 5.");
//                        }
//                    } catch (NumberFormatException ex) {
//                        System.out.println("Invalid rating format.");
//                    }
//                }
//                VBox details2 = new VBox(10);
//                details2.getChildren().addAll(
//                        new Label("Book: " + selectedBook.getTitlu()),
//                        new Label("Author: " + selectedBook.getAutor()),
//                        logLabel,
//                        ratingLabel,
//                        ratingField,
//                        changeRatingButton,
//                        deleteRatingButton,
//                        deleteBookButton,
//                        backToLibraryButton
//                );
//                Scene detailsScene = new Scene(details2, 400, 400);
//                primaryStage.setScene(detailsScene);
//            });
//
//            Button submitRatingButton = new Button("Submit Rating");
//            submitRatingButton.setOnAction(e0 -> {
//                String ratingText = ratingField.getText();
//                if (!ratingText.isEmpty()) {
//                    try {
//                        double rating = Double.parseDouble(ratingText);
//                        if (rating >= 1 && rating <= 5) {
//                            selectedBook.setRating(rating);
//                            updateRatingLabel();
//                        } else {
//                            System.out.println("Please enter a valid rating between 1 and 5.");
//                        }
//                    } catch (NumberFormatException ex) {
//                        System.out.println("Invalid rating format.");
//                    }
//                }
//
//                VBox details3=new VBox(10);
//                details3.getChildren().addAll(
//                        new Label("Book: " + selectedBook.getTitlu()),
//                        new Label("Author: " + selectedBook.getAutor()),
//                        logLabel,
//                        ratingLabel,
//                        deleteRatingButton,
//                        changeRatingButton,
//                        deleteBookButton,
//                        backToLibraryButton
//                );
//                Scene detailsScene = new Scene(details3, 400, 400);
//                primaryStage.setScene(detailsScene);
//            });
//
//
//            VBox details=new VBox(10);
//            details.getChildren().addAll(
//                    new Label("Book: " + selectedBook.getTitlu()),
//                    new Label("Author: " + selectedBook.getAutor()),
//                    logLabel,
//                    ratingLabel,
//                    ratingField,
//                    submitRatingButton,
//                    deleteRatingButton,
//                    deleteBookButton,
//                    backToLibraryButton
//            );
//            Scene detailsScene = new Scene(details, 400, 400);
//            primaryStage.setScene(detailsScene);
//        });
//
//        buttonLayout=new GridPane();
//        buttonLayout.setHgap(10);
//        buttonLayout.setVgap(10);
//        buttonLayout.add(submitLogButton, 0, 0);
//        buttonLayout.add(deleteLastLogButton, 1, 0);
//        buttonLayout.add(finishedButton, 0, 1);
//        buttonLayout.add(deleteBookButton, 0, 2);
//        buttonLayout.add(backToLibraryButton, 9, 10);
//        VBox detailsLayout = new VBox(10);
//        detailsLayout.getChildren().addAll(
//                new Label("Book: " + selectedBook.getTitlu()),
//                new Label("Author: " + selectedBook.getAutor()),
//                logLabel,
//                logField,
//                buttonLayout
//        );
//
//          Scene detailsScene = new Scene(detailsLayout, 400, 400);
//        primaryStage.setScene(detailsScene);
//    }
//    private void updateRatingLabel() {
//        ratingLabel.setText("Current Rating: " + selectedBook.getRating());
//    }
//    private void updateLogLabel() {
//
//        logLabel.setText("Reading Time: " + getTotalReadingTime(selectedBook));
//    }
//    private String getTotalReadingTime(Carte selectedBook) {
//        int totalMinutes = 0;
//        for (Log log : selectedBook.afiseazaLoguri()) {
//            totalMinutes += log.getMinute();
//        }
//        if (totalMinutes < 60 && totalMinutes > 0)
//            return totalMinutes + " minutes";
//        else if (totalMinutes >= 60) {
//            int totalHours = totalMinutes / 60;
//            int remainingMinutes = totalMinutes % 60;
//            if (remainingMinutes == 0) {
//                return totalHours + " hours";
//            } else {
//                return totalHours + " hours and " + remainingMinutes + " minutes";
//            }
//        }
//        return "No time logged yet.";
//    }
//
//}
