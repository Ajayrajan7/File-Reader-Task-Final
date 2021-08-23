import java.util.*;
public class JoinResponse{
    private List<Row> left = new ArrayList<>();
    private List<Row> right = new ArrayList<>();
    private Iterator<Row> itleft;
    private Iterator<Row> itright;
    private int size;
    public void put(Row left,Row right){
        this.left.add(left);
        this.right.add(right);
        size++;
    }
    public int getSize(){
         return size;
    }
 
    public boolean hasNext(){
        return itleft.hasNext() && itright.hasNext();
    }
 
    public void intiateIterator(){
        itleft = left.iterator();
        itright =  right.iterator();
    }
 
    public Row[] next(){
        return new Row[]{itleft.next(),itright.next()};
    }
 
 }