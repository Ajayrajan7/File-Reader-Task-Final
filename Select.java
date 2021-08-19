import java.util.*;
import java.io.*;
public class Select {
   private Criteria criteria = new Criteria();
   private Row r;
   private String[] columns;
   private String tablename ;
   public Select(String tablename){
          this.tablename = tablename;
   }

   public void columns(String... columns) throws ColumnNotFoundException{
       checkIfColumnsAreValid(columns);
       this.columns = columns;
   }
   public Criteria getCriteria(){
       return criteria;
   }
   /**
    * Iterate over the columns from the GetTableDetails cache and throw if columns mismatched in given columns
    * @param columns
    * @throws ColumnNotFoundException
    */ 
   private  void checkIfColumnsAreValid(String[] columns) throws ColumnNotFoundException {
       
   }

   public List<Row> executeQuery(){
       try{
            RowGenerator rowGen = new RowGenerator(tablename);
            List<Row> results = new ArrayList<>();
            while(rowGen.hasNext()){
                this.r = rowGen.next();
                // System.out.println(r.getRowDetails());
                ReducerUtil reducerUtil = new ReducerUtil();
                reducerUtil.initialize(r,criteria.getTop());
                if(reducerUtil.parseAllCriterasAndReturnFinalBoolean()){
                    if(columns==null || columns.length==0)
                        results.add(r);
                    // else{
                    //     sendOnlyRequestCols(columns);
                    // }
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
// Select s = new Select("User").columns("Id","Name","Password").where("Id",Operator.EQ,20).and("Name",Operator.EQ,"Ajay").or("Name",Operator.EQ,"Chella");