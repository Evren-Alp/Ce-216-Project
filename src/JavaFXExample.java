import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER); // (1)
        Button benjamin = new Button("Give it to me daddy!"); // (3)
        vbox.getChildren().add(benjamin);
        Scene scene = new Scene(vbox, 400, 300); // (2)
        stage.setTitle("Hello CE216");
        benjamin.setOnAction(e -> System.out.println("hate niggers")); // (4)
        stage.setScene(scene);
        stage.show();
    }
}