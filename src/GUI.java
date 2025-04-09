import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUI extends Application {
    public static TextArea textArea;
    
    // TableView to display artifacts
    private TableView<Artifact> table = new TableView<>();

    @Override
    public void start(Stage stage) throws Exception {
        // ---------------------------
        // Create the Menu Bar and Items
        // ---------------------------
        MenuBar menu = new MenuBar();
        MenuItem mOpenFile = new MenuItem("Open File");
        MenuItem mAbout = new MenuItem("About");
        MenuItem mNewFile = new MenuItem("New");
        MenuItem mQuit = new MenuItem("Quit");
        MenuItem mSave = new MenuItem("Save");
        Menu mFile = new Menu("File");
        Menu mHelp = new Menu("Help");

        mNewFile.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        mNewFile.setOnAction(e -> newFile(textArea));
        mQuit.setOnAction(e -> stage.close());
        mQuit.setAccelerator(KeyCombination.keyCombination("ESC"));
        mSave.setOnAction(e -> {
            try {
                saveFile(stage, textArea);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        mSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        mOpenFile.setOnAction(e -> {
            try {
                openFile(stage, textArea);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        mOpenFile.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        mAbout.setOnAction(e -> aboutText());
        mHelp.getItems().addAll(mAbout);
        mFile.getItems().addAll(mNewFile, mOpenFile, mSave, mQuit);
        menu.getMenus().addAll(mFile, mHelp);

        // ---------------------------
        // Create the bottom Search HBox with a border
        // ---------------------------
        HBox searcHBox = new HBox(10);
        searcHBox.setPadding(new Insets(10));
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        Button searchButton = new Button("Search");
        searcHBox.getChildren().addAll(searchField, searchButton);
        searcHBox.setStyle("-fx-border-color: gray; -fx-border-width: 2 0 0 0; -fx-padding: 10;");

        // ---------------------------
        // Create the Side Panel with buttons and add a right border
        // ---------------------------
        VBox sidePanel = new VBox(10);
        sidePanel.setPadding(new Insets(10));
        Button btnAdd = new Button("Add Artifact");
        btnAdd.setOnAction(e -> {
            // Open a modal window to add an artifact
            Stage artifactStage = new Stage();
            artifactStage.setTitle("Add Artifact");
            VBox artifactLayout = new VBox(10);
            artifactLayout.setPadding(new Insets(10));
            
            // Create text fields for artifact properties
            TextField artifactName = new TextField();
            artifactName.setPromptText("Artifact Name");
            TextField artifactID = new TextField();
            artifactID.setPromptText("Artifact ID");
            TextField category = new TextField();
            category.setPromptText("Category");
            TextField civilization = new TextField();
            civilization.setPromptText("Civilization");
            TextField discoveryLoc = new TextField();
            discoveryLoc.setPromptText("Discovery Location");
            TextField composition = new TextField();
            composition.setPromptText("Composition");
            TextField discoveryDate = new TextField();
            discoveryDate.setPromptText("Discovery Date");
            TextField currentPl = new TextField();
            currentPl.setPromptText("Current Place");
            TextField weight = new TextField();
            weight.setPromptText("Weight");
            TextField width = new TextField();
            width.setPromptText("Width");
            TextField height = new TextField();
            height.setPromptText("Height");
            TextField length = new TextField();
            length.setPromptText("Length");
            TextField tags = new TextField();
            tags.setPromptText("Tags");
            
            Button saveButton = new Button("Save");
            saveButton.setOnAction(event -> {
                // For simplicity, we check only Artifact Name here
                if (!artifactName.getText().isEmpty()) {
                    try {
                        // Create a new Artifact using a constructor with your parameters.
                        // Make sure the Artifact class has an appropriate constructor.
                        Artifact artf = new Artifact(
                            artifactName.getText(), 
                            artifactID.getText(),
                            category.getText(), 
                            civilization.getText(), 
                            discoveryLoc.getText(), 
                            composition.getText(), 
                            discoveryDate.getText(), 
                            currentPl.getText(),
                            Double.parseDouble(weight.getText()), 
                            Double.parseDouble(width.getText()),
                            Double.parseDouble(height.getText()), 
                            Double.parseDouble(length.getText())
                            // Tags can be processed as needed.
                        );
                        // Add the new artifact to the table
                        table.getItems().add(artf);
                        
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Artifact added successfully!");
                        alert.showAndWait();
                        artifactStage.close();
                    } catch (NumberFormatException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter valid numeric values for weight, width, height, and length.");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all fields.");
                    alert.showAndWait();
                }
            });
            
            artifactLayout.getChildren().addAll(
                artifactName, artifactID, category, civilization, discoveryLoc,
                composition, discoveryDate, currentPl, weight, width,
                height, length, tags, saveButton
            );
            
            Scene artifactScene = new Scene(artifactLayout, 300, 600);
            artifactStage.setScene(artifactScene);
            artifactStage.initModality(Modality.APPLICATION_MODAL);
            artifactStage.showAndWait();
        });
        
        Button btnEdit = new Button("Edit Selected");
        Button btnDelete = new Button("Delete Selected");
        Button btnRefresh = new Button("Refresh List");
        btnRefresh.setOnAction(e -> {
            // Refresh the table data if needed
            table.refresh();
        });
        sidePanel.getChildren().addAll(btnAdd, btnEdit, btnDelete, btnRefresh);
        sidePanel.setStyle("-fx-border-color: gray; -fx-border-width: 0 2 0 0; -fx-padding: 10;");

        // ---------------------------
        // Set up the TableView to display artifacts in the center.
        // ---------------------------
        setupArtifactTable();

        // (Optional) You can still keep the textArea if you need it in another part of the app.
        textArea = new TextArea();
        VBox.setVgrow(textArea, Priority.ALWAYS);
        // In this configuration, we use the table as the main content.
        
        
        // ---------------------------
        // Set accelerators and actions for menu items
        // ---------------------------
        mNewFile.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        mNewFile.setOnAction(e -> newFile(textArea));
        mQuit.setOnAction(e -> stage.close());
        mQuit.setAccelerator(KeyCombination.keyCombination("ESC"));
        mSave.setOnAction(e -> {
            try {
                saveFile(stage, textArea);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        mSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        mOpenFile.setOnAction(e -> {
            try {
                openFile(stage, textArea);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        mOpenFile.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        mAbout.setOnAction(e -> aboutText());
        mHelp.getItems().addAll(mAbout);
        mFile.getItems().addAll(mNewFile, mOpenFile, mSave, mQuit);
        menu.getMenus().addAll(mFile, mHelp);

        // ---------------------------
        // Build the main layout using a BorderPane
        // ---------------------------
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setTop(menu);
        mainLayout.setLeft(sidePanel);
        mainLayout.setCenter(table);  // The table is now the main center component
        mainLayout.setBottom(searcHBox);

        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setTitle("CE216");
        stage.setScene(scene);
        stage.show();
    }

    

    // Helper method to set up the TableView columns
    private void setupArtifactTable() {
        TableColumn<Artifact, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getArtifactId())
        );

        TableColumn<Artifact, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getName())
        );

        TableColumn<Artifact, String> colCategory = new TableColumn<>("Category");
        colCategory.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getCategory())
        );

        TableColumn<Artifact, String> colCivilization = new TableColumn<>("Civilization");
        colCivilization.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getCivilization())
        );

        TableColumn<Artifact, String> colLocation = new TableColumn<>("Current Place");
        colLocation.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getCurrentPlace())
        );

        table.getColumns().addAll(colId, colName, colCategory, colCivilization, colLocation);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public static void newFile(TextArea textArea){
        textArea.clear();
    }

    private static void aboutText(){
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setHeaderText("About");
        about.setTitle("About");
        about.setContentText("Eger bu yaziyi goruyorsan ne yaptigini bilmedigin icin helpe tiklamissindir. Merak etme, ben de ne yaptigimi bilmiyorum");
        about.showAndWait();
    }

    private void saveFile(Stage stage ,TextArea textArea) throws IOException {
         FileChooser fc = new FileChooser();
         fc.setTitle("Select file to save!");
         File f = fc.showSaveDialog(stage);
         Files.writeString(f.toPath(), textArea.getText(), StandardOpenOption.CREATE);
    }
    
    private void openFile(Stage stage,TextArea textArea) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select file to open!");
        File f = fc.showOpenDialog(stage);
        textArea.setText(Files.readString(f.toPath()));
    }

    public static void main(String[] args) {
       launch();
    }
}
