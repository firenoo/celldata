package firenoo.dna;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import firenoo.lib.data.SaveHelper;

/**
 * Writes DNA objects to a file.
 * DNA objects could be packaged together into a single file.
 * @author Firenoo
*/
public interface IDnaWriter{

    /**
     * Write a single DNA object to the specified file, overriding
     * any existing data!
     */
    default void write(IDna dna, String fileName) {
        writeAll(fileName, dna);
    }

    void write(IDna dna, OutputStream stream) throws IOException;

    /**
     * Writes the DNA objects to the specified file, overriding any
     * existing data in that location!
     */
    default void writeAll(String fileName, IDna... dnas) {
        try(OutputStream outStream = new FileOutputStream(fileName)) {
            writeAll(outStream, dnas);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    default void writeAll(OutputStream outStream, IDna... dnas) throws IOException {
        SaveHelper.writeInt(dnas.length, outStream);
        for(IDna dna : dnas) {
            write(dna, outStream);
        }
    }
    
}
