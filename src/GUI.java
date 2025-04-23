import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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


        FileInputStream inputstream = new FileInputStream("Sprites/clap.gif"); 
        Image image = new Image(inputstream); 
        ImageView ablalar = new ImageView(image);
        
        ablalar.setFitHeight(350);
        ablalar.setFitWidth(200);

        

        // ---------------------------
        // Create the bottom Search HBox with a border
        // ---------------------------
        HBox searcHBox = new HBox(10);
        searcHBox.setPadding(new Insets(10));
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        ObservableList<Artifact> artifactList = FXCollections.observableArrayList();
        FilteredList<Artifact> filteredList = new FilteredList<>(artifactList, p -> true);
        table.setItems(filteredList);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(artifact -> {
                // If the search field is empty, display all artifacts
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
        
                // Compare artifact properties with the search query (case-insensitive)
                String lowerCaseFilter = newValue.toLowerCase();
        
                if (artifact.getArtifactId().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Matches Artifact ID
                }
                return false; // No match
            });
        });
        searcHBox.getChildren().addAll(searchField);
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
            TextField imagePaths = new TextField();
            imagePaths.setPromptText("Image paths separated by \",\"");

            
            Button saveButton = new Button("Save");
            saveButton.setOnAction(event -> {
            if (!artifactID.getText().isEmpty() && !artifactName.getText().isEmpty() &&
            !category.getText().isEmpty() && !civilization.getText().isEmpty() && 
            !discoveryLoc.getText().isEmpty() && !composition.getText().isEmpty() && 
            !discoveryDate.getText().isEmpty() && !currentPl.getText().isEmpty() && !weight.getText().isEmpty() &&
            !width.getText().isEmpty() && !height.getText().isEmpty() && !length.getText().isEmpty() && !tags.getText().isEmpty() && !imagePaths.getText().isEmpty()) {
                String[] temp = tags.getText().split(" ");
                ArrayList<String> taglist = new ArrayList<>();
                for (String tag : temp) {
                    taglist.add(tag.trim());
                }
                List<String> imagePathsList = new ArrayList<>();
                for(String path : imagePaths.getText().split(",")) {
                    imagePathsList.add(path.trim());
                }
                Artifact artifact = new Artifact(artifactID.getText(), artifactName.getText(),
                                         category.getText(), civilization.getText(), 
                                    discoveryLoc.getText(), composition.getText(), 
                                 discoveryDate.getText(), currentPl.getText(),
                             Double.parseDouble(weight.getText()), Double.parseDouble(width.getText()),
                        Double.parseDouble(height.getText()), Double.parseDouble(length.getText()), taglist, imagePathsList);
                
                artifactList.add(artifact);
                ArtifactManager.addArtifact(artifact);
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Artifact added successfully!");
                alert.showAndWait();
                artifactStage.close();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
            }
            });
            
            artifactLayout.getChildren().addAll(
                artifactName, artifactID, category, civilization, discoveryLoc,
                composition, discoveryDate, currentPl, weight, width,
                height, length, tags, imagePaths, saveButton
            );
            
            Scene artifactScene = new Scene(artifactLayout, 300, 600);
            artifactStage.setScene(artifactScene);
            artifactStage.initModality(Modality.APPLICATION_MODAL);
            artifactStage.showAndWait();
        });
        
        Button btnEdit = new Button("Edit Selected");

        btnEdit.setOnAction(e -> {
            Artifact selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText(null);
                alert.setContentText("Select an artifact to edit.");
                alert.showAndWait();
                return;
            }
            Stage editStage = new Stage();
            editStage.setTitle("Edit Artifact");
            VBox editLayout = new VBox(10);
            editLayout.setPadding(new Insets(10));
            TextField artifactIDField = new TextField(selected.getArtifactId());
            TextField artifactNameField = new TextField(selected.getName());
            TextField categoryField = new TextField(selected.getCategory());
            TextField civilizationField = new TextField(selected.getCivilization());
            TextField discoveryLocField = new TextField(selected.getDiscoveryLocation());
            TextField compositionField = new TextField(selected.getComposition());
            TextField discoveryDateField = new TextField(selected.getDiscoveryDate());
            TextField currentPlField = new TextField(selected.getCurrentPlace());
            TextField weightField = new TextField(String.valueOf(selected.getWeight()));
            TextField widthField = new TextField(String.valueOf(selected.getWidth()));
            TextField heightField = new TextField(String.valueOf(selected.getHeight()));
            TextField lengthField = new TextField(String.valueOf(selected.getLength()));
            TextField tagsField = new TextField(String.join(" ", selected.getTags()));
            Button saveEditButton = new Button("Save");
            saveEditButton.setOnAction(ev -> {
                selected.setArtifactId(artifactIDField.getText());
                selected.setName(artifactNameField.getText());
                selected.setCategory(categoryField.getText());
                selected.setCivilization(civilizationField.getText());
                selected.setDiscoveryLocation(discoveryLocField.getText());
                selected.setComposition(compositionField.getText());
                selected.setDiscoveryDate(discoveryDateField.getText());
                selected.setCurrentPlace(currentPlField.getText());
                selected.setWeight(Double.parseDouble(weightField.getText()));
                selected.setWidth(Double.parseDouble(widthField.getText()));
                selected.setHeight(Double.parseDouble(heightField.getText()));
                selected.setLength(Double.parseDouble(lengthField.getText()));
                selected.setTags(new ArrayList<>(List.of(tagsField.getText().split(" "))));
                table.refresh();
                editStage.close();
            });
            editLayout.getChildren().addAll(artifactIDField, artifactNameField, categoryField, civilizationField, discoveryLocField, compositionField, discoveryDateField, currentPlField, weightField, widthField, heightField, lengthField, tagsField, saveEditButton);
            Scene editScene = new Scene(editLayout, 300, 600);
            editStage.setScene(editScene);
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.showAndWait();
        });

        Button btnDelete = new Button("Delete Selected");
        btnDelete.setOnAction(e -> {
            Artifact selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText(null);
                alert.setContentText("Please select an artifact to delete.");
                alert.showAndWait();
                return;
            }
        
            // Confirm deletion
            Alert confirmation = new Alert(AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to delete the selected artifact?");
            if (confirmation.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                String oldArtifactID = selected.getArtifactId();
                artifactList.remove(selected); // Remove from artifactList
                ArtifactManager.deleteArtifact(oldArtifactID); // Remove from the data source
            }
        });
        Button btnRefresh = new Button("Refresh List");
        btnRefresh.setOnAction(e -> {
            // Refresh the table data if needed
            table.refresh();
        });
        sidePanel.getChildren().addAll(btnAdd, btnEdit, btnDelete, btnRefresh, ablalar);    
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
        

        // ---------------------------
        // Build the main layout using a BorderPane
        // ---------------------------
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setTop(menu);
        mainLayout.setLeft(sidePanel);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(false);
        mainLayout.setCenter(scrollPane);  
        mainLayout.setBottom(searcHBox);

        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setTitle("Artifact Manager");
        

        stage.setScene(scene);
        stage.show();
    }

    

    // Helper method to set up the TableView columns
    private void setupArtifactTable() {
        TableColumn<Artifact, ImageView> colImage = new TableColumn<>("Image");
        colImage.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getImageView()));
        colImage.setCellFactory(col -> new TableCell<Artifact, ImageView>() {
            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : item);
                setAlignment(javafx.geometry.Pos.CENTER);
            }
        });
        TableColumn<Artifact, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArtifactId()));
        TableColumn<Artifact, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        TableColumn<Artifact, String> colCategory = new TableColumn<>("Category");
        colCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        TableColumn<Artifact, String> colCivilization = new TableColumn<>("Civilization");
        colCivilization.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCivilization()));
        TableColumn<Artifact, String> colDiscoveryLocation = new TableColumn<>("Discovery Location");
        colDiscoveryLocation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiscoveryLocation()));
        TableColumn<Artifact, String> colComposition = new TableColumn<>("Composition");
        colComposition.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComposition()));
        TableColumn<Artifact, String> colDiscoveryDate = new TableColumn<>("Discovery Date");
        colDiscoveryDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiscoveryDate()));
        TableColumn<Artifact, String> colCurrentPlace = new TableColumn<>("Current Place");
        colCurrentPlace.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurrentPlace()));
        TableColumn<Artifact, String> colWeight = new TableColumn<>("Weight");
        colWeight.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getWeight())));
        TableColumn<Artifact, String> colWidth = new TableColumn<>("Width");
        colWidth.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getWidth())));
        TableColumn<Artifact, String> colHeight = new TableColumn<>("Height");
        colHeight.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getHeight())));
        TableColumn<Artifact, String> colLength = new TableColumn<>("Length");
        colLength.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLength())));
        TableColumn<Artifact, String> colTags = new TableColumn<>("Tags");
        colTags.setCellValueFactory(cellData -> {
            ArrayList<String> tags = cellData.getValue().getTags();
            return new SimpleStringProperty(tags == null ? "" : String.join(",", tags));
        });
        colDiscoveryLocation.setPrefWidth(120);
        colDiscoveryDate.setPrefWidth(120);
        table.getColumns().addAll(colImage, colId, colName, colCategory, colCivilization, colDiscoveryLocation, colComposition, colDiscoveryDate, colCurrentPlace, colWeight, colWidth, colHeight, colLength, colTags);
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
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

    private void openFile(Stage stage, TextArea textArea) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select file to open!");
        File f = fc.showOpenDialog(stage);
        textArea.setText(Files.readString(f.toPath()));
    }

    public static void main(String[] args) {
       launch();
    }
}
