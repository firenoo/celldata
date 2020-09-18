package firenoo.dna;

/**
 * Allows for the data stored in a Dna object to be mutated/changed by exposing
 * the data structure/backing array for modification.
 * @author Firenoo
 */
@FunctionalInterface
public interface IMutator {

    /**
     * Mutates a Dna object's data.
     * @param data - the data. Note that data.length is not a true 
     *               representation how much data is stored in the 
     *               Dna object.
     * @param dataLength - The amount of space the data actually uses.
     */
    int mutate(byte[] data, int dataLength);

}