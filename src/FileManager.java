
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        while(this.text.equals("")){
            Scanner sc = new Scanner(System.in);
            while(this.fileAddress.equals("") || !this.fileAddress.toUpperCase().endsWith(".JSON")){
                System.out.println("Wrong file address. Address should end with \".json\". Try again: ");
                this.fileAddress = sc.nextLine();
            }
            sc.close();

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
        

        // if it's reading then call the "readFile" method.
        if(reads){
            readFile();
        }
        // if it's writing, then the user has to call the "writeToFile" method
        // MANUALLY and give the context to the method.
    }

    public FileManager(boolean reads){
        this("", reads);
        
    }

    void readFile(){
        


        Pattern pattern1 = Pattern.compile("\"(\\w+)\":\\s*(?:\"([^\"]+)\"|(\\{[^}]+\\})|(\\[.*?\\])|([\\d.]+))");
        Matcher matcher = pattern1.matcher(this.text);


        String dimensions = "";
        String tags = "";



        // saving all the values
        while(matcher.find()){
            if(matcher.group(1).toLowerCase().equals("artifactid")) { this.myArtifact.artifactId = matcher.group(2); if(DEBUGGING) System.out.println("[OK] found ID"); }
            else if(matcher.group(1).toLowerCase().equals("name")) { this.myArtifact.name = matcher.group(2); if(DEBUGGING) System.out.println("[OK] found name"); }
            else if(matcher.group(1).toLowerCase().equals("category")) { this.myArtifact.category = matcher.group(2); if(DEBUGGING) System.out.println("[OK] found category"); }
            else if(matcher.group(1).toLowerCase().equals("civilization")) { this.myArtifact.civilization = matcher.group(2); if(DEBUGGING) System.out.println("[OK] found civilization"); }
            else if(matcher.group(1).toLowerCase().equals("discoverylocation")) { this.myArtifact.discoveryLocation = matcher.group(2); if(DEBUGGING) System.out.println("[OK] found discovery location"); }
            else if(matcher.group(1).toLowerCase().equals("composition")) { this.myArtifact.composition = matcher.group(2); if(DEBUGGING) System.out.println("[OK] found composition"); }
            else if(matcher.group(1).toLowerCase().equals("discoverydate")) { this.myArtifact.discoveryDate = matcher.group(2); if(DEBUGGING) System.out.println("[OK] found discovery date"); }
            else if(matcher.group(1).toLowerCase().equals("currentplace")) { this.myArtifact.currentPlace = matcher.group(2); if(DEBUGGING) System.out.println("[OK] found current place"); }
            else if(matcher.group(1).toLowerCase().equals("dimensions")) { dimensions = matcher.group(3); if(DEBUGGING) System.out.println("[OK] found dimensions"); }
            else if(matcher.group(1).toLowerCase().equals("weight")) { this.myArtifact.weight = Double.parseDouble(matcher.group(5)); if(DEBUGGING) System.out.println("[OK] found weight"); }
            else if(matcher.group(1).toLowerCase().equals("tags")) { tags = matcher.group(4); if(DEBUGGING) System.out.println("[OK] found tags"); }
            else if(matcher.group(1).toLowerCase().equals("imagepath")) { this.myArtifact.imagePath = matcher.group(2); if (DEBUGGING) System.out.println("[OK] found imagePath"); }
            else{ System.out.println("Invalid input key. Terminating..."); System.exit(1); }
        }

        // separating and saving all the dimension values
        Pattern dimensionsSeparator = Pattern.compile("\"(\\w+)\"\\s*:\\s*([0-9.]+)");
        Matcher dimensionMatcher = dimensionsSeparator.matcher(dimensions);
        while(dimensionMatcher.find()){
            if(dimensionMatcher.group(1).toLowerCase().equals("width")) { this.myArtifact.width = Double.parseDouble(dimensionMatcher.group(2)); if(DEBUGGING) System.out.println("[OK] found width"); }
            else if(dimensionMatcher.group(1).toLowerCase().equals("height")) { this.myArtifact.height = Double.parseDouble(dimensionMatcher.group(2)); if(DEBUGGING) System.out.println("[OK] found height"); }
            else if(dimensionMatcher.group(1).toLowerCase().equals("length")) { this.myArtifact.length = Double.parseDouble(dimensionMatcher.group(2)); if(DEBUGGING) System.out.println("[OK] found length"); }
            else{ System.out.println("Invalid input key. Terminating..."); System.exit(1); }
        }

        // separating tags and saving them in an arraylist
<<<<<<< Updated upstream
        Pattern pathsSeparator = Pattern.compile("\"([A-z0-9\\/\\\\_.]*)\"");
        Matcher pathsMatcher = pathsSeparator.matcher(imagePaths);
        while(pathsMatcher.find()){
            if(DEBUGGING) System.out.println("[OK] found an image path");
            this.myArtifact.imagePaths.add(pathsMatcher.group(1));
        }

        // separating image paths and saving them in an arraylist
        Pattern tagsSeparator = Pattern.compile("\"([A-z0-9\\/\\\\_.]*)\"");
=======
        Pattern tagsSeparator = Pattern.compile("\"([A-z0-9\\/.:]*)\"*");
>>>>>>> Stashed changes
        Matcher tagsMatcher = tagsSeparator.matcher(tags);
        while(tagsMatcher.find()){
            if(DEBUGGING) System.out.println("[OK] found a tag");
            this.myArtifact.tags.add(tagsMatcher.group(1));
        }

        
        // if(myArtifact.artifactID.equals("")){ if(DEBUGGING) System.out.println("Artifact ID not given."); myArtifact.artifactID = "NULL"; }




    }

    public Artifact getArtifact(){
        return myArtifact;
    }


    public void writeToFile(String str, String fileAddress){
        // TODO
    }
}