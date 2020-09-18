package firenoo.dna;

/**
 * Provides a convenience method for getting single bytes rather than a
 * whole array. The data is assumed to be aligned on 8-bit boundaries.
 * @author Firenoo
 */
public interface IByteDna extends IDna{

    @Override
    default byte[] get(int arrayPos) {
        return new byte[]{getByte(arrayPos)};
    }

    /**
     * Get a singular byte at the specified array position.
     * @param arrayPos - the array index
     * @return the byte from the backing array at the array index.
     */
    byte getByte(int arrayPos);

    /**
     * Get all the bytes starting at {@code begin} and ending at {@code end},
     *  both inclusive.
     * Throws an exception if the beginning and end indices are not in the
     * bounds of the backing array.
     * @param begin - The beginning index (in bytes). Should be a positive number.
     * @param end   - The ending index (in bytes). Should be a positive number,
     *                and no less than {@code begin}.
     * @return a byte array of length {@code end - begin}, containing the data
     *         in the array between those two indices, inclusive.
     */
    byte[] getByteArray(int begin, int end) throws IllegalArgumentException;

}