import java.util.List;

public class Artifact {
    public String artifactId;
    public String name;
    public String category;
    public String civilization;
    public String discoveryLocation;
    public String composition;
    public String discoveryDate;
    public String currentPlace;
    public double weight, width, height, length;
<<<<<<< Updated upstream
    public List<String> tags;
    public List<String> imagePaths;
=======
    public ArrayList<String> tags;
    public String imagePath;
    public ImageView imageView;
>>>>>>> Stashed changes

    public Artifact(String artifactId ,String name, String category, String civilization, 
               String discoveryLocation, String composition, String discoveryDate,
<<<<<<< Updated upstream
                String currentPlace, double weight, double width, double height, double length){
=======
                String currentPlace, double weight, double width, double height, double length, ArrayList<String> tags, String imagePath) {
>>>>>>> Stashed changes
        this.artifactId = artifactId;
        this.name = name;
        this.category = category;
        this.civilization = civilization;
        this.discoveryLocation = discoveryLocation;
        this.composition = composition;
        this.discoveryDate = discoveryDate;
        this.currentPlace = currentPlace;
        this.weight = weight;
        this.width = width;
        this.height = height;
        this.length = length;
<<<<<<< Updated upstream
=======
        this.tags = tags;
        this.imagePath = imagePath;
        
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream(imagePath); 
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Image file not found. Please check the path.");
            alert.showAndWait();
            e.printStackTrace();
        } 
        Image image = new Image(inputstream); 
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        this.imageView= imageView;
>>>>>>> Stashed changes
    }

        public Artifact(){}

    public void printArtifactData(){
        System.out.println("---------- Artifact Data ----------");
        System.out.println("ID: " + this.artifactId + "      Name: " + this.name + "\nCategory: " + this.category + "      Civilization: " + this.civilization);
        System.out.println("Discovery Locaiton: " + this.discoveryLocation + "      Discovery Date: " + this.discoveryDate);
        System.out.println("Composition: " + this.composition);
        System.out.println("CurrentPlace: " + this.currentPlace);
        System.out.println("Weight: " + this.weight + "    Height: " + this.height + "    Width: " + this.width + "    Length: " + this.length);
        System.out.println("Image path:" + this.imagePath);
        System.out.println("Tags:");
        for(String tag:tags){
            System.out.println("  -" + tag);
        }
    }



    // Getters and Setters
    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCivilization() {
        return civilization;
    }

    public void setCivilization(String civilization) {
        this.civilization = civilization;
    }

    public String getDiscoveryLocation() {
        return discoveryLocation;
    }

    public void setDiscoveryLocation(String discoveryLocation) {
        this.discoveryLocation = discoveryLocation;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getDiscoveryDate() {
        return discoveryDate;
    }

    public void setDiscoveryDate(String discoveryDate) {
        this.discoveryDate = discoveryDate;
    }

    public String getCurrentPlace() {
        return currentPlace;
    }

    public void setCurrentPlace(String currentPlace) {
        this.currentPlace = currentPlace;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
