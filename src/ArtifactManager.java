import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArtifactManager {
    public static List<Artifact> artifacts= new ArrayList<>();

    public void loadArtifactsFromJSON(String filePath) throws IOException {
    }

    public void saveArtifactsToJSON(String filePath) throws IOException {
    }

    public static void addArtifact(Artifact artifact) {
        String filepath = "Artifacts.json";
        artifacts.add(artifact);
        List<Artifact> newArtifact = new ArrayList<Artifact>();
        newArtifact.add(artifact);
        exportSelectedArtifactsToJSON(newArtifact, filepath, true);
    }

    public void editArtifact(Artifact artifact) {
        
    }

    public static void deleteArtifact(String artifactId) {
    }

    public static List<Artifact> searchArtifacts(String query) {
        //we dont need this method anymore 
        return artifacts;
    }

    public List<Artifact> filterArtifacts(List<String> tags) {
        return null;
    }

    public void importArtifactsFromJSON(String filePath) {
    }

    public static void exportSelectedArtifactsToJSON(List<Artifact> selectedArtifacts, String filePath, boolean append) {
        FileManager fileMan = new FileManager(filePath, false);
        String content ="";
        for(int i = 0; i<selectedArtifacts.size(); i++){
            content=content.concat("{\n");
            content=content.concat("    \"ArtifactId\":" + "\"" +selectedArtifacts.get(i).getArtifactId()+ "\",\n" );
            content=content.concat("    \"Name\":" + "\"" +selectedArtifacts.get(i).getName()+ "\",\n" ); 
            content=content.concat("    \"Category\":" + "\"" +selectedArtifacts.get(i).getCategory()+ "\",\n" );
            content=content.concat("    \"Civilization\":" + "\"" +selectedArtifacts.get(i).getCivilization()+ "\",\n" );
            content=content.concat("    \"DiscoveryLocation\":" + "\"" +selectedArtifacts.get(i).getDiscoveryLocation()+ "\",\n" );
            content=content.concat("    \"Composition\":" + "\"" +selectedArtifacts.get(i).getComposition()+ "\",\n" );
            content=content.concat("    \"DiscoveryDate\":" + "\"" +selectedArtifacts.get(i).getDiscoveryDate()+ "\",\n" );
            content=content.concat("    \"CurrentPlace\":" + "\"" +selectedArtifacts.get(i).getCurrentPlace()+ "\",\n" );
            
            content=content.concat("    \"dimensions\": {");
        
            content=content.concat("\"Width\":" + selectedArtifacts.get(i).getWidth() + ", ");
            content=content.concat("\"Height\":" + selectedArtifacts.get(i).getHeight() + ", ");
            content=content.concat("\"Length\":" + selectedArtifacts.get(i).getLength() + " }, \n");

            content=content.concat("    \"Weight\":" + selectedArtifacts.get(i).getWeight() + ", \n");

            content=content.concat("    \"Tags\": [");
            for(int j=0; j<selectedArtifacts.get(i).getTags().size(); j++){
                content=content.concat("\""+selectedArtifacts.get(i).getTags().get(j)+"\",");
            }
            content= content.substring(0, content.length()-1);
            content= content.concat("],\n");
            content=content.concat("    \"ImagePaths\": [");
            for(int j=0; j<selectedArtifacts.get(i).getImagePaths().size(); j++){
                content=content.concat("\""+selectedArtifacts.get(i).getImagePaths().get(j)+"\", ");
            }
            content=content.substring(0, content.length()-2);
            content=content.concat("]\n}\n"); 
        }
        fileMan.writeToFile(content, filePath, append);
    }


    public static String artifactToJSON(Artifact artifact) {
        StringBuilder content = new StringBuilder();
        content.append("{\n");
        content.append("    \"ArtifactId\":").append("\"").append(artifact.getArtifactId()).append("\",\n");
        content.append("    \"Name\":").append("\"").append(artifact.getName()).append("\",\n");
        content.append("    \"Category\":").append("\"").append(artifact.getCategory()).append("\",\n");
        content.append("    \"Civilization\":").append("\"").append(artifact.getCivilization()).append("\",\n");
        content.append("    \"DiscoveryLocation\":").append("\"").append(artifact.getDiscoveryLocation()).append("\",\n");
        content.append("    \"Composition\":").append("\"").append(artifact.getComposition()).append("\",\n");
        content.append("    \"DiscoveryDate\":").append("\"").append(artifact.getDiscoveryDate()).append("\",\n");
        content.append("    \"CurrentPlace\":").append("\"").append(artifact.getCurrentPlace()).append("\",\n");

        content.append("    \"dimensions\": {");
        content.append("\"Width\":").append(artifact.getWidth()).append(", ");
        content.append("\"Height\":").append(artifact.getHeight()).append(", ");
        content.append("\"Length\":").append(artifact.getLength()).append(" }, \n");

        content.append("    \"Weight\":").append(artifact.getWeight()).append(", \n");

        content.append("    \"Tags\": [");
        for (int j = 0; j < artifact.getTags().size(); j++) {
            content.append("\"").append(artifact.getTags().get(j)).append("\",");
        }
        if (!artifact.getTags().isEmpty()) {
            content.setLength(content.length() - 1); // Remove trailing comma
        }
        content.append("],\n");

        content.append("    \"ImagePaths\": [");
        for (int j = 0; j < artifact.getImagePaths().size(); j++) {
            content.append("\"").append(artifact.getImagePaths().get(j)).append("\", ");
        }
        if (!artifact.getImagePaths().isEmpty()) {
            content.setLength(content.length() - 2); // Remove trailing comma and space
        }
        content.append("]\n}\n");

        return content.toString();
    }
}
