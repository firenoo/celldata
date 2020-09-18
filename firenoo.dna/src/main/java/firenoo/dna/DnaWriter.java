package firenoo.dna;

import java.io.OutputStream;
import java.io.IOException;

import firenoo.lib.data.SaveHelper;

public class DnaWriter implements IDnaWriter{

    public DnaWriter(){}

    @Override
    public void write(IDna dna, OutputStream outStream) throws IOException{
        SaveHelper.writeInt(dna.getDataLength(), outStream);
        SaveHelper.writeInt(dna.unitSize(), outStream);
        SaveHelper.writeLong(dna.getSeed(), outStream);
        SaveHelper.writeInt('\n', outStream);
        for(int i = 0; i < dna.getDataLength(); i++) {
            outStream.write(dna.get(i));
        }
    }


}
