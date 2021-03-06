import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class JoinResult extends Join{
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

    // @Override
	// public boolean hasNext(){
    //     return false;
    // }
    // /**
    //  * @return Row of two joined tables combined together with all fields
    //  */
    // @Override 
    // public Row next(){

    // }
 
    public List<Row> getRows(){
        String tempTableName = getLHSTableName();
        try{
            RowGenerator rowGen = new RowGenerator(tempTableName);
            List<Row> results = new ArrayList<>();
            while(rowGen.hasNext()){
                Row r = rowGen.next();
                results.add(r);
            }   
            return results;
       }catch(RowExhausedException e){
           e.printStackTrace();
       }catch(FileNotFoundException e){
           e.printStackTrace();
       }
       return null;
        
    }
    /**
     * @return void 
     * This method should be called interanally once the join results exhausted.
     */
    private void deleteFile(){
        String tempTableName = getLHSTableName();
        try{
            File file = new File(GetTableDetails.dataPath+File.separator+tempTableName+".txt");
            file.delete();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void finalize(){
        deleteFile();
    }

}
