import java.time.format.DateTimeFormatter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.LocalDate;
import java.util.Locale;
import javafx.util.StringConverter;


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
    ObservableList<Artifact> artifactList = FXCollections.observableArrayList();

    public static TextArea textArea;
    
    // TableView to display artifacts
    private TableView<Artifact> table = new TableView<>();


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

private static final StringConverter<LocalDate> DATE_CONVERTER = new StringConverter<>() {
    @Override
    public String toString(LocalDate date) {
        return (date != null) ? DATE_FORMATTER.format(date) : "";
    }

    @Override
    public LocalDate fromString(String string) {
        return (string != null && !string.isEmpty()) ? LocalDate.parse(string, DATE_FORMATTER) : null;
    }
};



    @Override
    public void start(Stage stage) throws Exception {
        // ---------------------------
        // Create the Menu Bar and Items
        // ---------------------------
        MenuBar menu = new MenuBar();
        MenuItem mOpenFile = new MenuItem("Open File");// DOESNT WORK ------------------------------------------------------------------------------
        MenuItem mAbout = new MenuItem("About");
        MenuItem mNewFile = new MenuItem("New");//NO USE -----------------------------------------------------------------
        MenuItem mQuit = new MenuItem("Quit");
        MenuItem mSave = new MenuItem("Save selected as JSON");
        Menu mFile = new Menu("File");
        Menu mHelp = new Menu("Help");

        mNewFile.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        //mNewFile.setOnAction(e -> newFile(textArea));
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
        
                if (artifact.getArtifactId().toLowerCase().contains(lowerCaseFilter) ||
                    artifact.getName().toLowerCase().contains(lowerCaseFilter) || 
                    artifact.getCategory().toLowerCase().contains(lowerCaseFilter) ||
                    artifact.getCivilization().toLowerCase().contains(lowerCaseFilter) || 
                    artifact.getComposition().toLowerCase().contains(lowerCaseFilter) ||
                    artifact.getCurrentPlace().toLowerCase().contains(lowerCaseFilter) ||
                    artifact.getDiscoveryDate().toLowerCase().contains(lowerCaseFilter) ||
                    artifact.getDiscoveryLocation().toLowerCase().contains(lowerCaseFilter)){
                    return true; 
                }
                return false;
            });
        });
        TextField filterField = new TextField();
        filterField.setPromptText("Filter by tag...");
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(artifact -> {     
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }           

                String[] lowerCaseFilter = newValue.toLowerCase().split(" ");
                if(artifact.getTags() != null){
                    ArrayList<String> artifactTags = new ArrayList<>();
                    for(int i = 0; i<artifact.getTags().size(); i++){
                        artifactTags.add(artifact.getTags().get(i).toLowerCase());
                    }
                    
                    boolean contains = false;
                    for(String filterTag : lowerCaseFilter){
                        if(artifactTags.contains(filterTag)) contains = true;
                        else contains = false;
                    }
                    return contains;
                }
                    return false;
            });
        });
        searcHBox.getChildren().addAll(searchField, filterField);
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
            DatePicker discoveryDatePicker = new DatePicker();
            discoveryDatePicker.setPromptText("dd-MM-yyyy");
            discoveryDatePicker.setConverter(DATE_CONVERTER);


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

            final String[] selectedImagePath = {null};  // holds only one image path

ImageView imagePreview = new ImageView();
imagePreview.setFitWidth(100);
imagePreview.setFitHeight(100);
imagePreview.setPreserveRatio(true);
imagePreview.setStyle("-fx-border-color: gray; -fx-padding: 5;");

            

            Button chooseImages = new Button("Choose Images");
            chooseImages.setOnAction(ev -> {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Image File");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.gif")
    );

    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
        selectedImagePath[0] = selectedFile.getAbsolutePath();
        imagePreview.setImage(new Image(selectedFile.toURI().toString()));
    }
});


            

            
            Button saveButton = new Button("Save");
            saveButton.setOnAction(event -> {
            if (!artifactID.getText().isEmpty() && !artifactName.getText().isEmpty() &&
            !category.getText().isEmpty() && !civilization.getText().isEmpty() && 
            !discoveryLoc.getText().isEmpty() && !composition.getText().isEmpty() && 
            discoveryDatePicker.getValue() != null &&
            !currentPl.getText().isEmpty() && !weight.getText().isEmpty() &&
            !width.getText().isEmpty() && !height.getText().isEmpty() && !length.getText().isEmpty() && !tags.getText().isEmpty() && selectedImagePath[0] != null
) {
                String[] temp = tags.getText().split(" ");
                ArrayList<String> taglist = new ArrayList<>();
                for (String tag : temp) {
                    taglist.add(tag.trim());
                }
                List<String> imagePathsList = List.of(selectedImagePath[0]);

                Artifact artifact = new Artifact(artifactID.getText(), artifactName.getText(),
                                         category.getText(), civilization.getText(), 
                                    discoveryLoc.getText(), composition.getText(), 
                                 discoveryDatePicker.getValue().format(DATE_FORMATTER),
                                currentPl.getText(),
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
                composition, discoveryDatePicker, currentPl, weight, width,
                height, length, tags, chooseImages, imagePreview, saveButton
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
            DatePicker discoveryDatePicker = new DatePicker();
            discoveryDatePicker.setPromptText("dd-MM-yyyy");
            discoveryDatePicker.setConverter(DATE_CONVERTER);

            try {
                discoveryDatePicker.setValue(LocalDate.parse(selected.getDiscoveryDate(), DATE_FORMATTER));
            } catch (Exception ev) {
            discoveryDatePicker.setValue(null);
            }


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
                if (discoveryDatePicker.getValue() != null) {
                    selected.setDiscoveryDate(discoveryDatePicker.getValue().format(DATE_FORMATTER));
                }


                selected.setCurrentPlace(currentPlField.getText());
                selected.setWeight(Double.parseDouble(weightField.getText()));
                selected.setWidth(Double.parseDouble(widthField.getText()));
                selected.setHeight(Double.parseDouble(heightField.getText()));
                selected.setLength(Double.parseDouble(lengthField.getText()));
                selected.setTags(new ArrayList<>(List.of(tagsField.getText().split(" "))));
            
                table.refresh();
            
                ArtifactManager.overwriteArtifacts(artifactList, "Artifact_Files\\Artifacts.json");
            
                editStage.close();
            });
            ;
            editLayout.getChildren().addAll(artifactIDField, artifactNameField, categoryField, civilizationField, discoveryLocField, compositionField, discoveryDatePicker, currentPlField, weightField, widthField, heightField, lengthField, tagsField, saveEditButton);
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
                ArtifactManager.deleteArtifact(oldArtifactID);
                ArtifactManager.exportSelectedArtifactsToJSON(artifactList, "Artifact_Files\\Artifacts.json", false); // Remove from the data source
                ArtifactManager.exportSelectedArtifactsToJSON(artifactList, "Artifact_Files\\Artifacts.json", false); // Remove from the data source
            }
        });
        Button btnRefresh = new Button("Refresh List");
        btnRefresh.setOnAction(e -> refreshTable());
        Button importJson = new Button("Import from JSON");
        importJson.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select JSON File");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
            );
        
            File selectedFile = fileChooser.showOpenDialog(stage);
        
            if (selectedFile != null) {
                List<Artifact> importedArtifacts = FileManager.loadArtifactsFromFile(selectedFile.getAbsolutePath());
                artifactList.addAll(importedArtifacts);
                ArtifactManager.exportSelectedArtifactsToJSON(artifactList, "Artifact_Files\\Artifacts.json", false);
                table.refresh();
      
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Import Successful");
                alert.setHeaderText(null);
                alert.setContentText("Artifacts imported successfully!");
                alert.showAndWait();
            }
        });
        
        sidePanel.getChildren().addAll(btnAdd, btnEdit, btnDelete, importJson, btnRefresh);    
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
       // mNewFile.setOnAction(e -> newFile(textArea));
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
        
        mainLayout.setCenter(table);  
        mainLayout.setBottom(searcHBox);

        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setTitle("Artifact Manager");
        

        stage.setScene(scene);
        stage.setMaximized(true);
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
        table.widthProperty().addListener((obs, oldVal, newVal) -> {
            double totalWidth = newVal.doubleValue();
            int columnCount = table.getColumns().size();
            for (TableColumn<?, ?> col : table.getColumns()) {
                col.setPrefWidth(totalWidth / columnCount);
            }
        });

        refreshTable();
        table.setStyle("-fx-font-size: 20px;");

    }
    

    

    private static void aboutText(){
    Stage helpStage = new Stage();
    helpStage.setTitle("Help");

    // Create the help content
    VBox helpLayout = new VBox(10);
    helpLayout.setPadding(new Insets(10));

    Label helpLabel = new Label("Help Page");
    helpLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

    TextArea helpContent = new TextArea();
    helpContent.setPrefHeight(250); // Set preferred height
    helpContent.setPrefWidth(350);  // Set preferred width
    helpContent.setEditable(false);
    helpContent.setWrapText(true);
    helpContent.setText(
        "Welcome to the Artifact Manager Help Page.\n\n" +
        "1. To add an artifact, click the 'Add Artifact' button on the left panel.\n" +
        "2. To edit an artifact, select it from the table and click 'Edit Selected'.\n" +
        "3. To delete an artifact, select it from the table and click 'Delete Selected'.\n" +
        "4. Use the 'Search' field to search for artifacts by name, ID, or other properties.\n" +
        "5. Use the 'Filter by tag' field to filter artifacts by tags. Use space between tags if you want to search by multiple tags.\n" +
        "6. 'Import from JSON' doesn't work yet. You can select your file but it won't add the artifacts to the table. \n" 
    );

    Button closeButton = new Button("Close");
    closeButton.setOnAction(e -> helpStage.close());

    helpLayout.getChildren().addAll(helpLabel, helpContent, closeButton);

    Scene helpScene = new Scene(helpLayout, 400, 400);
    helpStage.setScene(helpScene);
    helpStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
    helpStage.showAndWait();
    }

    private void saveFile(Stage stage, TextArea tex) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select file to save!");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        
        Artifact selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Select an artifact to save.");
            alert.showAndWait();
            return;
        }
        
        File f = fc.showSaveDialog(stage);
        if (f != null) {
            if (!f.getName().endsWith(".json")) {
                f = new File(f.getAbsolutePath() + ".json");
            }
            Files.writeString(f.toPath(), ArtifactManager.artifactToJSON(selected), StandardOpenOption.CREATE);
        }
    }

    private void openFile(Stage stage, TextArea textArea) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select file to open!");
        File f = fc.showOpenDialog(stage);
        textArea.setText(Files.readString(f.toPath()));
    }
    private void refreshTable() {
        {
            
            artifactList.clear();
            List<Artifact> arr =  FileManager.loadArtifactsFromFile("Artifact_Files\\Artifacts.json");
            for (Artifact art : arr) {
                artifactList.add(art);
                
            }
            table.refresh();
        }
    }

    public static void main(String[] args) {
        
       launch();
    }
}
