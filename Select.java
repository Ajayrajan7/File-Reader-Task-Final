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

   public Criteria columns(String... columns) throws ColumnNotFoundException{
       checkIfColumnsAreValid(columns);
       this.columns = columns;
       return criteria;
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
                    results.add(r);
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

class Criteria{
    private List<Object> criterias = new LinkedList<>();
    private List<WrappedCondition> TOP = new LinkedList<>();
    private boolean SWITCH = false;
    
    public Criteria where(String key,Operator operator,Comparable value) throws IllegalStateException{
        if(SWITCH) throw new IllegalStateException("Criteria  <where> is called more than once");
        SWITCH = true;
        TOP.add(new WrappedCondition(
            new Expression(operator,key,value),ExpressionName.AND
        ));
        return this;
    }

    public Criteria and(String key,Operator operator,Comparable value) throws IllegalStateException{
        checkSwitchAndThrowException("and");
        TOP.add(new WrappedCondition(
            new Expression(operator,key,value),ExpressionName.AND
        ));
        return this;

    }

    public Criteria or(String key,Operator operator,Comparable<Object> value) throws IllegalStateException{
        TOP.add(new WrappedCondition(
            new Expression(operator,key,value),ExpressionName.OR
        ));
        return this;
    }


    private void checkSwitchAndThrowException(String caller) throws IllegalStateException{
        if(!SWITCH){
            throw new IllegalStateException("Criteria <"+caller+"> is called before where conditon");
        }
    }

    public List<WrappedCondition> getTop(){
        return TOP;
    }


}

class Expression{  
    private Operator operator; //"Id > 20"
    private String LHSKEY;
    private Comparable<Object> LHS ;  // The value passed by the user during query construction

    public Expression(Operator operator,String LHSKEY,Comparable<Object> LHS){
        this.operator = operator;
        this.LHS = LHS;
        this.LHSKEY = LHSKEY;
    }

    public String getLHSKEY(){
        return LHSKEY;
    }


    /**
     * 
     * @param RHS The actual value from the Row Object
     * @return boolean
     */
    public boolean evaluate(Comparable<Object> RHS) {
         switch(operator){
             case GT :
                   return LHS.compareTo(RHS) == 1 ;
             case EQU :
                   return LHS.compareTo(RHS) == 0;
             case GTE :
                   return LHS.compareTo(RHS) >= 0;
             case LTE :
                   return LHS.compareTo(RHS) <= 0;
             case LT :
                   return LHS.compareTo(RHS) < 0; 
             default :
                   return false;   
        }
    }
} 

class ReducerUtil {
    private  boolean LHS = true;
    private  Row row;
    private  List<WrappedCondition> TOP;
    private  int POSITION = 0;

    public  void initialize(Row row, List<WrappedCondition> TOP){
        this.row = row;
        this.TOP = TOP;
        LHS = true;
    }

    public  boolean parseAllCriterasAndReturnFinalBoolean(){
        if(TOP.size()==0) return LHS;
        while((POSITION) != TOP.size()){
            LHS = reduce(LHS,TOP.get(POSITION));
            POSITION++;
        }
        return LHS;
        
     }
 
     public  boolean reduce(boolean LHS,WrappedCondition RHS){
         final String USERKEY = RHS.getExpression().getLHSKEY();
         final boolean RHSSTATUS = RHS.getExpression().evaluate((Comparable<Object>)row.getColumn(USERKEY));
         switch(RHS.getExpressionName()){
            case AND :
                 return LHS && RHSSTATUS;
            case OR :
                 return LHS || RHSSTATUS;
            default :
                 return false;
         }
     }
}




class WrappedCondition {
     private Expression expression;
     private ExpressionName expressionName; //AND OR 
    
     public WrappedCondition(Expression expression,ExpressionName expressionName){
         this.expression = expression;
         this.expressionName = expressionName;
     }

     public ExpressionName getExpressionName(){
         return expressionName;
     }

    public Expression getExpression(){
        return expression;
    }

    public void setExpression(Expression expression){
        this.expression=expression;
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