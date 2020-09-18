package firenoo.dna;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Loads in DNA objects from a file.
 * @author Firenoo
 */
public interface IDnaLoader{


    IDna load(InputStream stream) throws IOException, IllegalStateException;

    /**
     * Loads multiple DNA data stored in a file. This method assumes that more
     * than one DNA object is encoded in that file.
     */
    default IDna[] loadAll(String fileName) {
        IDna[] dnaResults = null;
        try(InputStream inStream = new FileInputStream(fileName)) {
            dnaResults = loadAll(inStream);
        } catch(IOException e) {
            e.printStackTrace();
            return dnaResults;
        }
        return dnaResults;
    }

    /**
     * Same as loadAll(String) but with InputStream parameter.
     * @param inStream
     * @return an array of all IDna instances found.
     */
    IDna[] loadAll(InputStream inStream);

}
