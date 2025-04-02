import java.io.*;

import javafx.application.Platform;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.*;
import javafx.stage.*;

import java.util.Scanner;

public class HelloApplication extends Application{
    
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

        VBox mainLayout=new VBox();

        ButtonBar bb =new ButtonBar();
        Button start = new Button("Start");
        
        Button stop = new Button("Stop");
        
        Button write = new Button("Write");
        
        bb.getButtons().addAll(start,stop,write);
        bb.autosize();


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

        mainLayout.getChildren().addAll(menu, textArea,bb); // add menu to mainLayout
        Scene scene = new Scene(mainLayout,500,400);
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
    
    }
}