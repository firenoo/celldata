package firenoo.dna;

/**
 * Represents the entire DNA strand.
 * Each individual will have 1 DNA strand, which consists
 * of many genes. In this implementation, the genes are stored
 * in integers.
 * A gene is a unit of 8 bits and is interpreted strictly
 * by Rna objects (in this implementation of the spec)
 * Version 0.3
 * @author Firenoo
 */
public interface IDna {

    /**
     * The default gene size.
     */
    static final int DEFAULT_GENE_SIZE = 4;

    /**
     * Defines the size, in bits, of a gene.
     */
    default int unitSize() {
        return DEFAULT_GENE_SIZE;
    }

    default long getSeed() {
        return 0L;
    }

    /**
     * The amount of space taken by the backing array, in bytes.
     */
    int getDataLength();

    /**
     * The number of data sets in this DNA representation.
     */
    int geneCount();
    
    /**
     * Creates a deep copy of  the DNA strand.
     */
    IDna copy();

    /**
     * Appends the set of data to the end of this DNA
     * strand. 
     * @param nData - The data to be added.
     */
    void append(byte... nData);
    
    /**
     * Appends the set of data to the end of this DNA
     * strand. 
     * @param nData - The data to be added. NOTE: the data
     *                will be aligned to a 4-byte boundary
     *                before being appended. Zeroes are padded
     *                where necessary.
     */
    void append(int... nData);

    /**
     * Sets the data at the specified location to the specified value.
     * More precisely, up to {@code data.length} bytes are replaced/added
     * starting at {@code loc}. Space is allocated if there is not enough
     * space.
     * @param loc   - index, in bytes.
     * @param nData - data to add.
     */
    void set(int loc, byte[] nData);

    /**
     * Retrieves the data at the specified location. The true
     * length of data can be found with calling #unitSize().
     * If it is less than a single byte, the data is bitshifted
     * as far to the right as possible without losing data.
     * @param arrayPos - The position where the set of
     *              data is kept.
     * @return The DNA data at the specified position, in
               a byte array.
     */
    byte[] get(int arrayPos);

    /**
     * Retrieves the gene at the specified gene location.
     * @param genePos - The gene location, or rather, the number of
     *                  genes after the first gene in the Dna object.
     * @return Returns a byte array containing the DNA data.
     */
    byte[] getGene(int genePos);

    /**
     * Retrieves any number of genes and stores them in a byte array.
     * @param begin - the first gene location to begin the search. [incl]
     * @param end   - where to stop looking (in gene sizes) [incl]
     * @return A byte array containing all the genes specified
     * @throws IllegalArgumentException If any of the following are true:
     * <ul>
     *   <li> {@code end < begin}</li>
     *   <li> {@code begin < 0} </li>
     *   <li> {@code end} is larger than the amount of data stored in the
     *      Dna object. </li>
     * </ul>
     */
    byte[] getGenes(int begin, int end) throws IllegalArgumentException;

    /**
     * Allows for modification of the data (as opposed to merely appending to
     * the end).
     * @param mutator - function to change the mutator.
     */
    void mutate(IMutator mutator);

    /**
     * Whether or not the dna strand has 0 strands.
     */
    boolean isEmpty();

}
