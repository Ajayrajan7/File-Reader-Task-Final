import java.io.*;
import java.util.*;
public class Delete{
   private Criteria criteria = new Criteria();
   private Row r;
   private String[] columns;
   private String tablename;
   private String filePath;
   private String dataFilePath;
   private int zeroCount =0;
   Properties p;

   public Delete(String tablename){
        this.tablename = tablename;
        // this.dataFilePath = GetTableDetails.dataPath;
        this.filePath = GetTableDetails.confPath+"\\"+tablename+".props";
        p=new Properties();
        try{
            p.load(new FileReader(filePath));
            this.zeroCount=Integer.parseInt(p.getProperty(tablename));
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
            if(zeroCount>1000){
                FileDeleteUtil.deleteLines(tablename);
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
   private boolean deleteRowHelper(Row r){
       try{
           RandomAccessFile file = FileUtil.getRandomAccessInstance(tablename);  
            file.seek(r.getSeekPos());  
            file.write("0".getBytes());  
            FileUtil.releaseFile();
            file.close();
            zeroCount++;
            Properties p = new Properties();
            p.setProperty(tablename,String.valueOf(zeroCount));
            OutputStream output = new FileOutputStream(filePath);
            p.store(output,null); 
            return true;
       }catch(IOException e){
           System.out.println(e);
       }
    return false;
   }
}