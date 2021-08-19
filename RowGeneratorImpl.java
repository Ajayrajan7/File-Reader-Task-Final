public interface RowGeneratorImpl {
    public Row next() throws RowExhausedException;
    public boolean hasNext();
    // public Row getNthRow() throws NoSuchRowException;
}