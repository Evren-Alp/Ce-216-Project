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

    public static void exportSelectedArtifactsToJSON(List<Artifact> selectedArtifacts, String filePath) {
        FileManager fileMan = new FileManager(filePath, true);
        String content ="";
        for(int i = 0; i<selectedArtifacts.size(); i++){
            content=content.concat("{");
            content=content.concat("\"Artifact ID\":" + "\"" +selectedArtifacts.get(i).getArtifactId()+ "\",\n" );
            content=content.concat("\"Artifact Name\":" + "\"" +selectedArtifacts.get(i).getName()+ "\",\n" ); 
            content= content.concat("\"Category\":" + "\"" +selectedArtifacts.get(i).getCategory()+ "\",\n" );
            content= content.concat("\"Civilization\":" + "\"" +selectedArtifacts.get(i).getCivilization()+ "\",\n" );
            content= content.concat("\"Discovery Location\":" + "\"" +selectedArtifacts.get(i).getDiscoveryLocation()+ "\",\n" );
            content= content.concat("\"Composition\":" + "\"" +selectedArtifacts.get(i).getComposition()+ "\",\n" );
            content= content.concat("\"Discovery Date\":" + "\"" +selectedArtifacts.get(i).getDiscoveryDate()+ "\",\n" );
            content= content.concat("\"Current Place\":" + "\"" +selectedArtifacts.get(i).getCurrentPlace()+ "\",\n" );
            content= content.concat("\"Weight\":" + "\"" +selectedArtifacts.get(i).getWeight()+ "\",\n" );
            content= content.concat("\"Width\":" + "\"" +selectedArtifacts.get(i).getWidth()+ "\",\n" );
            content= content.concat("\"Height\":" + "\"" +selectedArtifacts.get(i).getHeight()+ "\",\n" );
            content= content.concat("\"Length\":" + "\"" +selectedArtifacts.get(i).getLength()+ "\",\n" );
            content= content.concat("\"Tags\": [\n");
            for(int j=0; j<selectedArtifacts.get(i).getTags().size(); j++){
                content=content.concat("\""+selectedArtifacts.get(i).getTags().get(j)+"\",");
            }
            content= content.substring(0, content.length()-1);
            content= content.concat("],\n");
            content=content.concat("\"Image Paths\": [");
            for(int j=0; j<selectedArtifacts.get(i).getImagePaths().size(); j++){
                content=content.concat("\""+selectedArtifacts.get(i).getImagePaths().get(j)+"\",");
            }
            content=content.substring(0, content.length()-1);
            content=content.concat("]\n}"); 
        }
        fileMan.writeToFile(content, filePath);
    }
}
