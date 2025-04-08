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
        searcHBox.getChildren().addAll(searchField, searchButton);
        searcHBox.setStyle("-fx-border-color: gray; -fx-border-width: 2 0 0 0; -fx-padding: 10;");




        VBox sidePanel = new VBox(10);
        sidePanel.setPadding(new Insets(10));
        Button btnAdd = new Button("Add Artifact");
        btnAdd.setOnAction(e -> {
            Stage artifactStage = new Stage();
            artifactStage.setTitle("Add Artifact");

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
            String AN = artifactName.getText();
            if (!AN.isEmpty()) { //her bilgi girilsin mi yoksa sadece biri yeter mi??
                Artifact artf = new Artifact(artifactName.getText(), artifactID.getText(),
                                         category.getText(), civilization.getText(), 
                                    discoveryLoc.getText(), composition.getText(), 
                                 discoveryDate.getText(), currentPl.getText(),
                             Double.parseDouble(weight.getText()), Double.parseDouble(width.getText()),
                        Double.parseDouble(height.getText()), Double.parseDouble(length.getText()));
                //Add the artifact to the existing artifact list but the list does not exist yet
                
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