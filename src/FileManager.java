import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileInputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class FileManager{
    private Artifact myArtifact = new Artifact();
    private String fileAddress = "";
    private String text = "";
    private boolean reads; // to determine whether it's trying to read a file or write to it. MIGHT USE IT LATER.
    private boolean DEBUGGING = false;
    private File file;

    public FileManager(String fileAddress, boolean reads){
        /*
         * Constructor gets fileAddress and checks whether the address is valid or not.
         */

        this.reads = reads;
        this.fileAddress = fileAddress;

        // checking if the address is given or if the format is valid or not
        if(this.text.equals("") && this.fileAddress.equals("") || !this.fileAddress.toUpperCase().endsWith(".JSON")){
            System.out.println("[ERROR] Wrong file address. Address should end with \".json\".");
        }
        

        try{
            this.file = new File(this.fileAddress);
            Scanner reader = new Scanner(this.file);
            while (reader.hasNextLine()) {
                this.text += reader.nextLine() + "\n";
            }
            reader.close();
        }catch(FileNotFoundException e){
            if(DEBUGGING) System.out.println("[ERROR] File not found.");
        }

    }

    public static List<Artifact> loadArtifactsFromFile(String filePath) {
        List<Artifact> artifacts = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line.trim());
            }
    
            String json = jsonBuilder.toString().trim();
            if (json.startsWith("[")) json = json.substring(1);
            if (json.endsWith("]")) json = json.substring(0, json.length() - 1);
    
            String[] objects = json.split("\\},\\s*\\{");
    
            for (String obj : objects) {
                obj = obj.trim();
                if (!obj.startsWith("{")) obj = "{" + obj;
                if (!obj.endsWith("}")) obj = obj + "}";
    
                Artifact a = new Artifact();
                a.setArtifactId(extract(obj, "ArtifactId"));
                a.setName(extract(obj, "Name"));
                a.setCategory(extract(obj, "Category"));
                a.setCivilization(extract(obj, "Civilization"));
                a.setDiscoveryLocation(extract(obj, "DiscoveryLocation"));
                a.setComposition(extract(obj, "Composition"));
                a.setDiscoveryDate(extract(obj, "DiscoveryDate"));
                a.setCurrentPlace(extract(obj, "CurrentPlace"));
    
                a.setWidth(Double.parseDouble(extract(obj, "Width")));
                a.setHeight(Double.parseDouble(extract(obj, "Height")));
                a.setLength(Double.parseDouble(extract(obj, "Length")));
                a.setWeight(Double.parseDouble(extract(obj, "Weight")));
    
                a.setTags(new ArrayList<>(extractList(obj, "Tags")));
                List<String> paths = extractList(obj, "ImagePaths");
                a.setImagePaths(paths);


            if (!paths.isEmpty()) {
                try {
                    FileInputStream inputstream = new FileInputStream(paths.get(0));
                    Image image = new Image(inputstream);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    a.setImageView(imageView);
                } catch (FileNotFoundException e) {
                    System.err.println("Image not found at path: " + paths.get(0));
                }
            }

    
                artifacts.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return artifacts;
    }
    
    private static String extract(String json, String key) {
        try {
            String pattern = "\"" + key + "\":\\s*\"(.*?)\"";
            if (json.matches("(?s).*" + pattern + ".*")) {
                return json.replaceAll("(?s).*" + pattern + ".*", "$1");
            }
    
            pattern = "\"" + key + "\":\\s*([\\d\\.]+)";
            if (json.matches("(?s).*" + pattern + ".*")) {
                return json.replaceAll("(?s).*" + pattern + ".*", "$1");
            }
    
        } catch (Exception e) {
            System.out.println("Failed to extract: " + key);
        }
        return "";
    }
    
    
    private static List<String> extractList(String json, String key) {
        List<String> list = new ArrayList<>();
        String pattern = "\"" + key + "\":\\s*\\[(.*?)\\]";
        if (json.matches("(?s).*" + pattern + ".*")) {
            String inner = json.replaceAll("(?s).*" + pattern + ".*", "$1");
            String[] items = inner.split(",");
            for (String item : items) {
                list.add(item.trim().replaceAll("^\"|\"$", "").replace("\\\\", "\\"));
            }
        }
        return list;
    }
    

    public Artifact getArtifact(){
        return myArtifact;
    }


    public void writeToFile(String text, String fileAddress, boolean append){
        try {
            FileWriter myWriter = new FileWriter(fileAddress, append);
            myWriter.write(text);
            myWriter.close();
            if(DEBUGGING) System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}