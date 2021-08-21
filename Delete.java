import java.io.*;
import java.util.*;
public class Delete{
   private Criteria criteria = new Criteria();
   private Row r;
   private String tablename;
   private String filePath;
   private int zeroCount =0;
   private int deleteLimit = 0;
   Properties p;
   Properties p2;

   public Delete(String tablename){
        this.tablename = tablename;
        // this.dataFilePath = GetTableDetails.dataPath;
        this.filePath = GetTableDetails.confPath+File.separator+tablename+".props";
        try{
            p = GetTableDetails.parseProps(filePath);
            p2 = GetTableDetails.parseProps(GetTableDetails.confPath+File.separator+"deleteconfig.props");
            this.zeroCount=Integer.parseInt(p.getProperty(tablename));
            this.deleteLimit = Integer.parseInt(p2.getProperty("limit"));
        }catch(IOException e){
            System.out.println(e);
        } 
        
   }
   
   public Criteria getCriteria(){
       return criteria;
   }

   public boolean executeQuery(){
       try{
            RowGenerator rowGen = new RowGenerator(tablename);
            while(rowGen.hasNext()){
                this.r = rowGen.next();
                ReducerUtil reducerUtil = new ReducerUtil();
                reducerUtil.initialize(r,criteria.getTop());
                if(reducerUtil.parseAllCriterasAndReturnFinalBoolean()){
                    if(deleteRowHelper(r)){
                        continue;
                    }else{
                        //to handle failure case
                        return false;
                    }
                }
            }
            //Threshold check for deleting rows starts with 0
            if(zeroCount>deleteLimit){
                FileDeleteUtil.deleteLines(tablename);
                setZeroCount(zeroCount);
            }
            return true;
       }catch(FileNotFoundException e){
           System.out.println(e);
       }catch(RowExhausedException e){
           System.out.println(e);
       }catch(IOException e){
           System.out.println(e);
       }
    return false;
        
   }
   private void setZeroCount(int zeroCount){
       try{
            Properties p = new Properties();
            p.setProperty(tablename,String.valueOf(zeroCount));
            OutputStream output = new FileOutputStream(filePath);
            p.store(output,null);
       }catch(Exception e){
           System.out.println(e);
       }
        
   }
   private boolean deleteRowHelper(Row r){
       try{
            RandomAccessFile file = FileUtil.getRandomAccessInstance(tablename);  
            file.seek(r.getSeekPos()-1);  
            file.write("0".getBytes());  
            FileUtil.releaseFile();
            file.close();
            zeroCount++;
            setZeroCount(zeroCount);
            return true;
       }catch(IOException e){
           System.out.println(e);
       }
    return false;
   }
}