import java.util.*;
public class JoinResult extends Join implements RowGeneratorImpl{
    public JoinResult(String tempTableName,List<String> chainedTableName){
        try{
            setLHSTableName(tempTableName,false);
        }
        catch(NoSuchTableException e){
            System.out.println("There is an error in joining the table");
            throw new RuntimeException("Something fatal has happen");
        }
        
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
