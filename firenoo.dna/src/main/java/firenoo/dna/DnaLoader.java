package firenoo.dna;

import java.io.InputStream;
import java.io.IOException;

import firenoo.lib.data.SaveHelper;

/**
 * Loads a DNA object from a file.
*/
public class DnaLoader implements IDnaLoader{
    
    public DnaLoader() {
	
    }

    @Override
    public IDna load(InputStream stream) throws IOException, IllegalStateException {
        int length = SaveHelper.readInt(stream); //number of bytes
        int geneSize = SaveHelper.readInt(stream);
        if(geneSize != IDna.DEFAULT_GENE_SIZE) {
            throw new IllegalStateException("Discovered a gene size that is " + 
            "not compatible with this implementation!");
        }
        long seed = SaveHelper.readLong(stream);
        while(SaveHelper.readInt(stream) != '\n') {}
        Dna dna = new Dna(length, seed);
        byte[] b = new byte[length];
        if(stream.read(b, 0, length) < length) {
            throw new IOException("Could not read all bytes.");
        }
        dna.append(b);
        return dna;
    }
    
    @Override
    public IDna[] loadAll(InputStream inStream) {
        IDna[] dnaResults = null;
        try {
            int dnaCount = SaveHelper.readInt(inStream);
            dnaResults = new IDna[dnaCount];
            for(int i = 0; i < dnaCount; i++) {
                dnaResults[i] = load(inStream);
            }
        } catch(IOException e) {
            return dnaResults;
        }
        return dnaResults;
    }    
}
