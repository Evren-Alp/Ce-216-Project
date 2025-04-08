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
        String filepath = "adress";
        artifacts.add(artifact);
        List<Artifact> newArtifact = new ArrayList<Artifact>();
        newArtifact.add(artifact);
        exportSelectedArtifactsToJSON(newArtifact, filepath);
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
        FileManager fileMan = new FileManager(filePath, true);
        String content ="";
        for(int i = 0; i<selectedArtifacts.size(); i++){
            content.concat("{");
            content.concat("\"Artifact ID\":" + "\"" +selectedArtifacts.get(i).getArtifactId()+ "\",\n" );
            content.concat("\"Artifact Name\":" + "\"" +selectedArtifacts.get(i).getName()+ "\",\n" ); 
            content.concat("\"Category\":" + "\"" +selectedArtifacts.get(i).getCategory()+ "\",\n" );
            content.concat("\"Civilization\":" + "\"" +selectedArtifacts.get(i).getCivilization()+ "\",\n" );
            content.concat("\"Discovery Location\":" + "\"" +selectedArtifacts.get(i).getDiscoveryLocation()+ "\",\n" );
            content.concat("\"Composition\":" + "\"" +selectedArtifacts.get(i).getComposition()+ "\",\n" );
            content.concat("\"Discovery Date\":" + "\"" +selectedArtifacts.get(i).getDiscoveryDate()+ "\",\n" );
            content.concat("\"Current Place\":" + "\"" +selectedArtifacts.get(i).getCurrentPlace()+ "\",\n" );
            content.concat("\"Weight\":" + "\"" +selectedArtifacts.get(i).getWeight()+ "\",\n" );
            content.concat("\"Width\":" + "\"" +selectedArtifacts.get(i).getWidth()+ "\",\n" );
            content.concat("\"Height\":" + "\"" +selectedArtifacts.get(i).getHeight()+ "\",\n" );
            content.concat("\"Length\":" + "\"" +selectedArtifacts.get(i).getLength()+ "\",\n" );
            content.concat("\"Tags\": [\n");
            for(int j=0; j<selectedArtifacts.get(i).getTags().size(); j++){
                content.concat("\""+selectedArtifacts.get(i).getTags().get(j)+"\",");
            }
            content.substring(0, content.length()-1);
            content.concat("],\n");
            content.concat("\"Image Paths\": [\n");
            for(int j=0; j<selectedArtifacts.get(i).getImagePaths().size(); j++){
                content.concat("\""+selectedArtifacts.get(i).getImagePaths().get(j)+"\",");
            }
            content.substring(0, content.length()-1);
            content.concat("]\n}"); 
        }
        fileMan.writeToFile(content, filePath);
    }
}
