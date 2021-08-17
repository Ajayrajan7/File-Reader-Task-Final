public interface RowGeneratorImpl {
    public Row next() throws RowExhausedException;
    public boolean haxNext();
    public Row getNthRow() throws NoSuchRowException;
}