import java.util.*;
public class JoinResult extends Join implements RowGeneratorImpl{
    private String tempTableName;
    public JoinResult(String tempTableName,List<String> chainedTableName){
        this.tempTableName = tempTableName;
        this.chainedTableName = chainedTableName;
    }

    @Override
	public boolean hasNext(){
        return false;
    }
    /**
     * @return Row of two joined tables combined together with all fields
     */
    @Override 
    public Row next(){

    }
 

    /**
     * @return void 
     * This method should be called interanally once the join results exhausted.
     */
    private void deleteFile(){
        /*
        Delete the file with the name of the tempTableName
        */
    }

    public void finalize(){
        deleteFile();
    }

}
