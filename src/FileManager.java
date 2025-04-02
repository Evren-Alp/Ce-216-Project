
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FileManager{
    private Artifact myArtifact;
    private String fileAddress = "";
    private boolean reads; // to determine whether it's trying to read a file or write to it

    public FileManager(String fileAddress, boolean reads){
        /*
         * Constructor gets fileAddress and checks whether the address is valid or not.
         */
        this.fileAddress = fileAddress;
        if(this.fileAddress.equals("") || !this.fileAddress.toUpperCase().endsWith(".JSON")){ 
            // checking if the address is given or if the format is valid or not
            Scanner sc = new Scanner(System.in);
            while(this.fileAddress.equals("")){
                System.out.println("Enter File Address: ");
                this.fileAddress = sc.nextLine();
            }
            sc.close();
        }
        // if it's reading then call the "readFile" method.
        if(this.reads){
            readFile();
        }
        // if it's writing, then the user has to call the "writeToFile" method
        // MANUALLY and give the context to the method.
    }

    public FileManager(boolean reads){
        String empty = "";
        this.reads = reads;
        this(empty, reads);
    }

    void readFile(){
        String text = "{\n" + //
                        "    \"artifactId\": \"2012gizaros\",\n" + //
                        "    \"name\": \"Ancient Vase\",\n" + //
                        "    \"category\": \"Sculpture\",\n" + //
                        "    \"civilization\": \"Greek\",\n" + //
                        "    \"discoveryLocation\": \"Giza\",\n" + //
                        "    \"composition\": \"Clay\",\n" + //
                        "    \"discoveryDate\": \"2012-06-15\",\n" + //
                        "    \"currentPlace\": \"British Museum\",\n" + //
                        "    \"dimensions\": { \"width\": 10, \"height\": 15, \"length\": 10 },\n" + //
                        "    \"weight\": 2.5,\n" + //
                        "    \"tags\": [\"hellenistic\", \"vase\"],\n" + //
                        "    \"imagePaths\": [\"images/2012gizaros_1.jpg\", \"images/2012gizaros_2.jpg\"]\n" + //
                        "}\n" + //
                        "\n" + //
                        "";



        Pattern pattern1 = Pattern.compile("\"(\\w+)\":\\s*(?:\"([^\"]+)\"|(\\{[^}]+\\})|(\\[.*?\\])|([\\d.]+))");
        Matcher matcher = pattern1.matcher(text);


        String dimensions = "";
        String tags = "";
        String imagePaths = "";


        // saving all the values
        while(matcher.find()){
            if(matcher.group(1).toLowerCase().equals("artifactid")) { this.myArtifact.artifactID = matcher.group(2); }
            else if(matcher.group(1).toLowerCase().equals("name")) { this.myArtifact.name = matcher.group(2); }
            else if(matcher.group(1).toLowerCase().equals("category")) { this.myArtifact.category = matcher.group(2); }
            else if(matcher.group(1).toLowerCase().equals("civilization")) { this.myArtifact.civilization = matcher.group(2); }
            else if(matcher.group(1).toLowerCase().equals("discoverylocation")) { this.myArtifact.discoveryLocation = matcher.group(2); }
            else if(matcher.group(1).toLowerCase().equals("composition")) { this.myArtifact.composition = matcher.group(2); }
            else if(matcher.group(1).toLowerCase().equals("discoverydate")) { this.myArtifact.discoveryDate = matcher.group(2); }
            else if(matcher.group(1).toLowerCase().equals("currentplace")) { this.myArtifact.currentPlace = matcher.group(2); }
            else if(matcher.group(1).toLowerCase().equals("dimensions")) { dimensions = matcher.group(3); }
            else if(matcher.group(1).toLowerCase().equals("weight")) { this.myArtifact.weight = Float.parseFloat(matcher.group(5)); }
            else if(matcher.group(1).toLowerCase().equals("tags")) { tags = matcher.group(4); }
            else if(matcher.group(1).toLowerCase().equals("imagepaths")) { imagePaths = matcher.group(4); }
            else{ System.out.println("Invalid input key. Terminating..."); System.exit(1); }
        }

        // separating and saving all the dimension values
        Pattern dimensionsSeparator = Pattern.compile("\"(\\w+)\"\\s*:\\s*([0-9.]+)");
        Matcher dimensionMatcher = dimensionsSeparator.matcher(dimensions);
        while(dimensionMatcher.find()){
            if(dimensionMatcher.group(1).toLowerCase().equals("width")) { this.myArtifact.width = Float.parseFloat(dimensionMatcher.group(2)); }
            else if(dimensionMatcher.group(1).toLowerCase().equals("height")) { this.myArtifact.height = Float.parseFloat(dimensionMatcher.group(2)); }
            else if(dimensionMatcher.group(1).toLowerCase().equals("length")) { this.myArtifact.length = Float.parseFloat(dimensionMatcher.group(2)); }
            else{ System.out.println("Invalid input key. Terminating..."); System.exit(1); }
        }

        // separating tags and saving them in an arraylist
        Pattern pathsSeparator = Pattern.compile("\"([A-z0-9\\/\\\\_.]*)\"");
        Matcher pathsMatcher = pathsSeparator.matcher(imagePaths);
        while(pathsMatcher.find()){
            this.myArtifact.imagePaths.add(pathsMatcher.group(1));
        }

        // separating image paths and saving them in an arraylist
        Pattern tagsSeparator = Pattern.compile("\"([A-z0-9\\/\\\\_.]*)\"");
        Matcher tagsMatcher = tagsSeparator.matcher(tags);
        while(tagsMatcher.find()){
            this.myArtifact.tags.add(tagsMatcher.group(1));
        }


    }

    public Artifact getArtifact(){
        return myArtifact;
    }


    public void writeToFile(String text, String fileAddress){
        // TODO
    }
}