package firenoo.dna;

/**
 * Implementation as specified by the DNA API
 * Data is stored in a byte array.
 * @author Firenoo
*/
public class Dna implements IByteDna {
    
    //The data is stored here.
    private byte[] data;
    //Where to add the next byte of data to.
    private int dataPtr;
    //The number of genes in this DNA object.
    private int geneCount;

    private long seed;

    
    public Dna() {
        this(16);
    }

    /**
     * @param initialLength length of the initial byte array (in bytes)
     */
    public Dna(int initialLength) {
        this(initialLength, 0L);
    }

    /**
     * @param initialLength length of the initial byte array (in bytes)
     */
    public Dna(int initialLength, long seed) {
        this.data = new byte[initialLength];
        this.dataPtr = 0;
        this.geneCount = 0;
        this.seed = seed;
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public int geneCount() {
        return dataPtr / unitSize();
    }

    public int getDataLength() {
        return dataPtr;
    }

    @Override
    public void append(byte... nData) {
        if(this.dataPtr + nData.length >= data.length) {
            data = reallocate((int)(Math.max(1.5 * data.length, data.length + nData.length)));
        }
        System.arraycopy(nData, 0, data, dataPtr, nData.length);
        dataPtr += nData.length;
    }

    @Override
    public void append(int... nData) {
        //move data ptr to align to a 4-byte boundary
        this.dataPtr += (4 - (dataPtr % 4)) % 4;
        byte[] d = intToByteArray(nData);
        if(this.dataPtr + d.length >= data.length) {
            data = reallocate((int)(Math.max(1.5 * data.length, data.length + d.length)));
        }
        System.arraycopy(d, 0, data, dataPtr, d.length);
        dataPtr += nData.length * 4;
    }

    @Override
    public void set(int loc, byte[] nData) {
        if(loc + nData.length > data.length) {
            data = reallocate((int)(data.length * 1.5));
            dataPtr = loc + nData.length;
        }
        System.arraycopy(nData, 0, data, loc, nData.length);
    }

    @Override
    public byte getByte(int arrayPos) {
        return this.data[arrayPos];
    }

    /**
     * Returns a single byte of data. In this implementation, it is the
     * same as #get(int) since the gene size is 1 byte.
     */
    @Override
    public byte[] getGene(int genePos) {
        byte[] result = new byte[4];
        System.arraycopy(data, genePos * 4, result, 0, 4);
        return result;
    }

    @Override
    public byte[] getGenes(int begin, int end) throws IllegalArgumentException {
        return getByteArray(begin * 4, end * 4);
    }

    @Override
    public byte[] getByteArray(int begin, int end) throws IllegalArgumentException {
        if(end < begin) {
            throw new IllegalArgumentException("End index cannot be less than" + 
            " the beginning index.");
        }
        if(end > this.data.length) {
            throw new IllegalArgumentException("The end index is larger than" +
            "the backing array.");
        }
        if(begin < 0) {
            throw new IllegalArgumentException("The beginning index is negative.");
        }

        byte[] result = new byte[end - begin + 1];
        System.arraycopy(this.data, begin, result, 0, result.length);
        return result;
    }

    @Override
    public void mutate(IMutator mutator) {
        mutator.mutate(this.data, dataPtr);
    }

    @Override
    public IDna copy() {
        Dna dna = new Dna(this.data.length, this.seed);
        System.arraycopy(this.data, 0, dna.data, 0, this.data.length);
        return dna;
    }

    @Override
    public boolean isEmpty() {
        return geneCount == 0;
    }

    private byte[] reallocate(int newSize) {
        byte[] newarray = new byte[newSize];
        System.arraycopy(this.data, 0, newarray, 0, this.data.length);
        return newarray;
    }

    private byte[] intToByteArray(int[] integers) {
        byte[] result = new byte[integers.length * 4];
        for(int i = 0; i < integers.length; i++) {
            result[4 * i] = (byte) (integers[i] & 0xFF);
            result[4 * i + 1] = (byte)((integers[i] >>> 8) & 0xFF);
            result[4 * i + 2] = (byte)((integers[i] >>> 16) & 0xFF);
            result[4 * i + 3] = (byte)((integers[i] >>> 24) & 0xFF);

        }
        return result;
    }

}
