import java.io.*;
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
        this.dataFilePath = GetTableDetails.dataPath;
        this.filePath = "C:\\Users\\AjaySandhya\\Desktop\\File DB\\config\\"+tablename+"."+props;
        this.zeroCount=Integer.parseInt((new Properties().load(new FileReader(filePath))).getProperty(tablename));
   }
   
   public Criteria getCriteria(){
       return criteria;
   }

   public boolean executeQuery(){
        RowGenerator rowGen = new RowGenerator(tablename);
        while(rowGen.hasNext()){
            this.r = rowGen.next();
            ReducerUtil reducerUtil = new ReducerUtil();
            reducerUtil.initialize(r,criteria.TOP);
            if(reducerUtil.parseAllCriterasAndReturnFinalBoolean()){
                if(deleteRowHelper(r)){
                    continue;
                }else{
                    //to handle failure case
                }
            }
        }
        //Threshold check for deleting rows starts with 0
        if(zeroCount>1000){
            deleteRowsStartsWithZero();
        }
        return results;
   }
   private boolean deleteRowHelper(Row r){
        RandomAccessFile file = new RandomAccessFile(dataFilePath, "rw");  
        file.seek(r.getSeekPos());  
        file.write(0);  
        file.close();
        zeroCount++;
        Properties p = new Properties();
        p.setProperty(key,String.valueOf(zeroCount));
        p.store(new FileOutputStream(filePath,"Props file for tracking delete rows"); 
   }
}