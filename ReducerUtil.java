import java.util.*;
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