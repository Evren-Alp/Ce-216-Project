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
        String filepath = "Artifact_Files\\Artifacts.json";
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
        List<Artifact> allArtifacts = new ArrayList<>();
    
        if (append) {
            List<Artifact> existing = FileManager.loadArtifactsFromFile(filePath);
            allArtifacts.addAll(existing);
        }
    
        allArtifacts.addAll(selectedArtifacts);
    
        
        StringBuilder content = new StringBuilder();
        content.append("[\n");
    
        for (int i = 0; i < allArtifacts.size(); i++) {
            Artifact a = allArtifacts.get(i);
            content.append("  {\n");
            content.append("    \"ArtifactId\": \"" + a.getArtifactId() + "\",\n");
            content.append("    \"Name\": \"" + a.getName() + "\",\n");
            content.append("    \"Category\": \"" + a.getCategory() + "\",\n");
            content.append("    \"Civilization\": \"" + a.getCivilization() + "\",\n");
            content.append("    \"DiscoveryLocation\": \"" + a.getDiscoveryLocation() + "\",\n");
            content.append("    \"Composition\": \"" + a.getComposition() + "\",\n");
            content.append("    \"DiscoveryDate\": \"" + a.getDiscoveryDate() + "\",\n");
            content.append("    \"CurrentPlace\": \"" + a.getCurrentPlace() + "\",\n");
    
            content.append("    \"dimensions\": {\n");
            content.append("      \"Width\": " + a.getWidth() + ",\n");
            content.append("      \"Height\": " + a.getHeight() + ",\n");
            content.append("      \"Length\": " + a.getLength() + "\n");
            content.append("    },\n");
    
            content.append("    \"Weight\": " + a.getWeight() + ",\n");
    
            content.append("    \"Tags\": [");
            for (int j = 0; j < a.getTags().size(); j++) {
                content.append("\"" + a.getTags().get(j) + "\"");
                if (j < a.getTags().size() - 1) content.append(", ");
            }
            content.append("],\n");
    
            content.append("    \"ImagePaths\": [");
            for (int j = 0; j < a.getImagePaths().size(); j++) {
                content.append("\"" + a.getImagePaths().get(j).replace("\\", "\\\\") + "\"");
                if (j < a.getImagePaths().size() - 1) content.append(", ");
            }
            content.append("]\n");
    
            content.append("  }");
            if (i < allArtifacts.size() - 1) content.append(",");
            content.append("\n");
        }
    
        content.append("]\n");
    
        FileManager fileMan = new FileManager(filePath, false);
        fileMan.writeToFile(content.toString(), filePath, false); 
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
            content.setLength(content.length() - 1); // virgul sil
        }
        content.append("],\n");

        content.append("    \"ImagePaths\": [");
        for (int j = 0; j < artifact.getImagePaths().size(); j++) {
            content.append("\"").append(artifact.getImagePaths().get(j)).append("\", ");
        }
        if (!artifact.getImagePaths().isEmpty()) {
            content.setLength(content.length() - 2); // virgul ve bosluk sil
        }
        content.append("]\n}\n");

        return content.toString();
    }

    public static void overwriteArtifacts(List<Artifact> updatedList, String filePath) {
        exportSelectedArtifactsToJSON(updatedList, filePath, false); 
    }
    
}
