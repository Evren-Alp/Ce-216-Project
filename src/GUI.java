import java.io.*;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.*;

import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import javafx.stage.*;

import java.util.Scanner;

public class GUI extends Application{
    public static TextArea textArea;
    @Override

    public void start(Stage stage) throws Exception {
        MenuBar menu = new MenuBar();
        textArea = new TextArea();
        MenuItem mOpenFile = new MenuItem("Open File");
        MenuItem mAbout = new MenuItem("About");
        MenuItem mNewFile = new MenuItem("New");
        MenuItem mQuit = new MenuItem("Quit");
        MenuItem mSave = new MenuItem("Save");

        Menu mFile = new Menu("File");
        Menu mHelp =new Menu("Help");

        

       /* ButtonBar bb =new ButtonBar();
        Button start = new Button("Start");
        Button stop = new Button("Stop");
        Button write = new Button("Write");
        bb.getButtons().addAll(start,stop,write);
        bb.setStyle("-fx-alignment: center-left;");*/

        HBox searcHBox = new HBox(10);
        searcHBox.setPadding(new Insets(10));
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e-> {
            String query = searchField.getText().toLowerCase();
            ArtifactManager.searchArtifacts(query);
        });
        searcHBox.getChildren().addAll(searchField, searchButton);
        searcHBox.setStyle("-fx-border-color: gray; -fx-border-width: 2 0 0 0; -fx-padding: 10;");




        VBox sidePanel = new VBox(10);
        sidePanel.setPadding(new Insets(10));
        Button btnAdd = new Button("Add Artifact");
        btnAdd.setOnAction(e -> {
            Stage artifactStage = new Stage();
            artifactStage.setTitle("Add Artifact");
<<<<<<< Updated upstream
=======
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
            TextField imagePath = new TextField();
            imagePath.setPromptText("Image path (opt.)");
>>>>>>> Stashed changes

            VBox artifactLayout = new VBox(40);
            artifactLayout.setPadding(new Insets(40));

            TextField artifactName = new TextField();
            TextField artifactID = new TextField();
            TextField category = new TextField();
            TextField civilization = new TextField();
            TextField discoveryLoc = new TextField();
            TextField composition = new TextField();
            TextField discoveryDate = new TextField();
            TextField currentPl = new TextField();
            TextField weight = new TextField();
            TextField width = new TextField();
            TextField height = new TextField();
            TextField length = new TextField();
            TextField tags = new TextField();

            artifactName.setPromptText("Artifact Name");
            artifactID.setPromptText("Artifact ID");
            category.setPromptText("Category");
            civilization.setPromptText("Civilization");
            discoveryLoc.setPromptText("Discovery Location");
            composition.setPromptText("Composition");
            discoveryDate.setPromptText("Discovery Date");
            currentPl.setPromptText("Current Place");
            weight.setPromptText("Weight");
            width.setPromptText("Width");
            height.setPromptText("Height");
            length.setPromptText("Length");
            tags.setPromptText("Tags");
        
            Button saveButton = new Button("Save");
            saveButton.setOnAction(event -> {
<<<<<<< Updated upstream
            if (!artifactID.getText().isEmpty()&!artifactName.getText().isEmpty()&
            !category.getText().isEmpty()&!civilization.getText().isEmpty()& 
       !discoveryLoc.getText().isEmpty()&composition.getText().isEmpty()& 
    !discoveryDate.getText().isEmpty()&!currentPl.getText().isEmpty()&!weight.getText().isEmpty()&
    !width.getText().isEmpty()&!height.getText().isEmpty()&!length.getText().isEmpty())
             { //her bilgi girilsin mi yoksa sadece biri yeter mi??
=======
            if (!artifactID.getText().isEmpty() && !artifactName.getText().isEmpty() &&
            !category.getText().isEmpty() && !civilization.getText().isEmpty() && 
            !discoveryLoc.getText().isEmpty() && !composition.getText().isEmpty() && 
            !discoveryDate.getText().isEmpty() && !currentPl.getText().isEmpty() && !weight.getText().isEmpty() &&
            !width.getText().isEmpty() && !height.getText().isEmpty() && !length.getText().isEmpty() && !tags.getText().isEmpty()) {
                String[] temp = tags.getText().split(" ");
                ArrayList<String> taglist = new ArrayList<>();
                for (String tag : temp) {
                    taglist.add(tag.trim());
                }
                String img = "";
                if(imagePath.getText().isEmpty()) img = "Images/default _image.jpg";
               
>>>>>>> Stashed changes
                Artifact artifact = new Artifact(artifactID.getText(), artifactName.getText(),
                                         category.getText(), civilization.getText(), 
                                    discoveryLoc.getText(), composition.getText(), 
                                 discoveryDate.getText(), currentPl.getText(),
                             Double.parseDouble(weight.getText()), Double.parseDouble(width.getText()),
<<<<<<< Updated upstream
                        Double.parseDouble(height.getText()), Double.parseDouble(length.getText()));
                //Add the artifact to the existing artifact list but the list does not exist yet
=======
                        Double.parseDouble(height.getText()), Double.parseDouble(length.getText()), taglist, img);
                
                artifactList.add(artifact);
                ArtifactManager.addArtifact(artifact);
>>>>>>> Stashed changes
                
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

            artifactLayout.setSpacing(1); 
            artifactLayout.getChildren().addAll(
<<<<<<< Updated upstream
                artifactName, artifactID, category, civilization, discoveryLoc, 
                composition, discoveryDate, currentPl, weight, width, 
                height, length, tags, saveButton
=======
                artifactName, artifactID, category, civilization, discoveryLoc,
                composition, discoveryDate, currentPl, weight, width,
                height, length, tags, imagePath, saveButton
>>>>>>> Stashed changes
            );

            Scene artifactScene = new Scene(artifactLayout, 300, 600);
            artifactStage.setScene(artifactScene);
            artifactStage.initModality(Modality.APPLICATION_MODAL);
            artifactStage.showAndWait();
        });
        Button btnEdit = new Button("Edit Selected");
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
                table.getItems().remove(selected); // Remove from TableView
                ArtifactManager.deleteArtifact(oldArtifactID); // Remove from the data source
            }
        });
        Button btnRefresh = new Button("Refresh List");
        sidePanel.getChildren().addAll(btnAdd, btnEdit, btnDelete, btnRefresh);
        sidePanel.setStyle("-fx-border-color: gray; -fx-border-width: 0 2 0 0; -fx-padding: 10;");



        



        textArea =new TextArea();
        VBox.setVgrow(textArea, Priority.ALWAYS);
        //textArea.setEditable(false);

        mNewFile.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        mNewFile.setOnAction(e -> newFile(textArea));

        mQuit.setOnAction(e -> stage.close());
        mQuit.setAccelerator(KeyCombination.keyCombination("ESC"));

        mSave.setOnAction(e -> {
            try {
                saveFile(stage,textArea);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        mSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        mOpenFile.setOnAction(e-> {
            try {
                openFile(stage,textArea);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        mOpenFile.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

        mAbout.setOnAction(e -> aboutText());



        mHelp.getItems().addAll(mAbout);
        mFile.getItems().addAll(mNewFile, mOpenFile,mSave,mQuit); // add menuitems to file menu
        menu.getMenus().addAll(mFile, mHelp); // add menus to menubar

        BorderPane mainLayout=new BorderPane();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setTop(menu);
        mainLayout.setLeft(sidePanel);
        mainLayout.setBottom(searcHBox);
        

         // add menu to mainLayout
        Scene scene = new Scene(mainLayout,800,600);
        stage.setTitle("CE216");
        stage.setScene(scene);
        stage.show();
    }

    public static void newFile(TextArea textArea){
        textArea.clear();

    }

    private static void aboutText(){
        Alert about = new Alert(AlertType.INFORMATION) ;
        about.setHeaderText("About");
        about.setTitle("About");
        about.setContentText("Eger bu yaziyi goruyorsan ne yaptigini bilmedigin icin helpe tÄ±klamissindir. Merak etme, ben de ne yaptÄ±gÄ±mÄ± bilmiyorum");
        about.showAndWait();

    }

    private void saveFile(Stage stage ,TextArea textArea) throws IOException {
         FileChooser fc = new FileChooser();
         fc.setTitle("Select file to save!");
         File f = fc.showSaveDialog(stage); // more to do hereâ€¦
         Files.writeString(f.toPath(), textArea.getText(), StandardOpenOption.CREATE);
         }
    private void openFile(Stage stage,TextArea textArea) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select file to open!");
        File f = fc.showOpenDialog(stage); // more to do hereâ€¦
        textArea.setText(Files.readString(f.toPath()));

    }



 
    




    public static void main(String[] args) {
       launch();
    }
}