import java.util.*;
import java.io.*;
public class Update{
   private Criteria criteria = new Criteria();
   private Row r;
   private String tablename;
   private LinkedHashMap<String,Object> toUpdateFields = new LinkedHashMap<>();
   public Update(String tablename){
        this.tablename = tablename;
   }
   
   public Criteria getCriteria(){
       return criteria;
   }
   public void set(String key,Object value){
       toUpdateFields.put(key,value);
   }

   public boolean executeQuery(){
       try{
            RowGenerator rowGen = new RowGenerator(tablename,false);
            while(rowGen.hasNext()){
                this.r = rowGen.next();
                ReducerUtil reducerUtil = new ReducerUtil();
                reducerUtil.initialize(r,criteria.getTop());
                if(reducerUtil.parseAllCriterasAndReturnFinalBoolean()){
                    if(updateCurrentRow(r)){
                        continue;
                    }else{
                        //to handle failure case
                        return false;
                    }
                }
            }
            return true;
       }catch(RowExhausedException e){
           e.printStackTrace();;
       }catch(FileNotFoundException e){
           e.printStackTrace();;
       }
       return false;
        
   }

   private boolean updateCurrentRow(Row r){
       LinkedHashMap<String,Object> rowDetails = r.getRowDetails();
       for(String key:toUpdateFields.keySet()){
           if(rowDetails.get(key)==null)    return false;

            //Updating new value for a field
           rowDetails.put(key,toUpdateFields.get(key));
       }
       RandomAccessFile raf=null;
        try{
            raf=FileUtil.getRandomAccessInstance(tablename);
            raf.seek(r.getSeekPos());
            for(String key:rowDetails.keySet()){
                raf.write((Insert.padData(tablename,key,rowDetails.get(key))).getBytes());
            }
            return true;
        }
        catch(Exception e){
            e.printStackTrace();;
        }
        finally{
            try {
                FileUtil.releaseFile();
                raf.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return false;
   }
}