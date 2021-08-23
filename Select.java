import java.util.*;
import java.io.*;
public class Select extends Join{
   private Criteria criteria = new Criteria();
   private Row r;
   private String[] columns;
   private String tablename;
   private Set<String> columnsSet;
   private Set<String> actualCols;

   public Select(String tablename){
        setLHSTableName(tablename,true);
        this.tablename = tablename;
   }

   public void columns(String... columns) throws ColumnNotFoundException{
    //    checkIfColumnsAreValid(columns);
       this.columns = columns;
       this.columnsSet = new LinkedHashSet<>(Arrays.asList(columns));
       this.actualCols = GetTableDetails.tablesVsFieldDetails.get(tablename).keySet();
   }
   public Criteria getCriteria(){
       return criteria;
   }
   /**
    * Iterate over the columns from the GetTableDetails cache and throw if columns mismatched in given columns
    * @param columns
    * @throws ColumnNotFoundException
    */ 
//    private  void checkIfColumnsAreValid(String[] columns) throws ColumnNotFoundException {
       
//    }

   public List<Row> executeQuery(){
       try{
            RowGenerator rowGen = new RowGenerator(tablename);
            List<Row> results = new ArrayList<>();
            while(rowGen.hasNext()){
                this.r = rowGen.next();
                ReducerUtil reducerUtil = new ReducerUtil();
                reducerUtil.initialize(r,criteria.getTop());
                if(reducerUtil.parseAllCriterasAndReturnFinalBoolean()){
                    if(columns==null || columns.length==0)
                        results.add(r);
                    else{
                        sendOnlyRequestedCols();
                        results.add(r);
                    }
                }
            }   
            return results;
       }catch(RowExhausedException e){
           System.out.println(e);
       }catch(FileNotFoundException e){
           System.out.println(e);
       }
       return null;
   }

   public void sendOnlyRequestedCols(){
       try{
           for(String col:actualCols){
               if(!columnsSet.contains(col))
                    r.getRowDetails().remove(col);
           }
       }catch(Exception e){
           System.out.println(e);
       }
   }
}

enum ExpressionName{
    AND,
    OR
}

enum Operator {
    GT,
    GTE,
    LT,
    LTE,
    EQU
}