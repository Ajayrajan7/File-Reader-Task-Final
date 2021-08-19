import java.util.*;
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