import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArtifactManager {
    private List<Artifact> artifacts= new ArrayList<>();

    public void loadArtifactsFromJSON(String filePath) throws IOException {
    }

    public void saveArtifactsToJSON(String filePath) throws IOException {
    }

    public void addArtifact(Artifact artifact) {
        artifacts.add(artifact);
    }

    public void editArtifact(Artifact artifact) {
        
    }

    public void deleteArtifact(String artifactId) {
    }

    public List<Artifact> searchArtifacts(String query) {
        return artifacts;
    }

    public List<Artifact> filterArtifacts(List<String> tags) {
        return null;
    }

    public void importArtifactsFromJSON(String filePath) {
    }

    public void exportSelectedArtifactsToJSON(List<Artifact> selectedArtifacts, String filePath) {
    }
}
