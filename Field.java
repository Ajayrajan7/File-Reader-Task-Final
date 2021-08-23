import java.util.*;
public class Field{
    public String tableName_self,fieldName_self;
    private String tableName_another,fieldName_another;
    private FIELDTYPES fieldType = FIELDTYPES.LEFT_IS_FIELD_AND_RIGHT_IS_CONSTANT;
    private Operator operator;
    private boolean isRHSfield = true;
    private Comparable<Object> fieldValue;
    private int state = -1;
    private Row rhs,lhs;
    public Field(String tableName,String fieldName) throws IllegalArgumentException{
       this.tableName_self=tableName;
       this.fieldName_self=fieldName;

       if(tableName == null || fieldName == null) throw new IllegalArgumentException("tableName or fieldName shouldn't be null");
    }


    public Field equals(Object RHSFieldOrConstValue) throws IllegalArgumentException{
        operator = Operator.EQU;
        castGivenArgument(RHSFieldOrConstValue);
        return this;
    }

    public Field lt(Object RHSFieldOrConstValue) throws IllegalArgumentException{
        operator = Operator.LT;
        castGivenArgument(RHSFieldOrConstValue);
        return this;
    }

    public Field gt(Object RHSFieldOrConstValue) throws IllegalArgumentException{
        operator = Operator.GT;
        castGivenArgument(RHSFieldOrConstValue);
        return this;
    }

    public Field gte(Object RHSFieldOrConstValue) throws IllegalArgumentException{
        operator = Operator.GTE;
        castGivenArgument(RHSFieldOrConstValue);
        return this;
    }

    public Field lte(Object RHSFieldOrConstValue) throws IllegalArgumentException{
        operator = Operator.LTE;
        castGivenArgument(RHSFieldOrConstValue);
        return this;
    }
    public boolean evaluate(Row LHS,Row RHS){
        lhs = LHS;
        rhs = RHS;  
        return differentiateAndEvaluate();
    }

    private boolean differentiateAndEvaluate(){
        switch(fieldType){
            case LEFT_IS_FIELD_AND_RIGHT_IS_CONSTANT:
               return fieldVsFieldEval();
            case LEFT_IS_FIELD_AND_RIGHT_IS_FIELD:
               return fieldVsFieldEval();
            default:
               return false;
        }
    }

    private boolean fieldVsConstEval(Comparable<Object> RHS){
        switch(operator){
            case GT :
                  return fieldValue.compareTo(RHS) == 1 ;
            case EQU :
                  return fieldValue.compareTo(RHS) == 0;
            case GTE :
                  return fieldValue.compareTo(RHS) >= 0;
            case LTE :
                  return fieldValue.compareTo(RHS) <= 0;
            case LT :
                  return fieldValue.compareTo(RHS) < 0; 
            default :
                  return false;   
       }
    }

    private boolean fieldVsFieldEval(){
        Comparable<Object> lhsValue = (Comparable)lhs.getColumn(fieldName_self); //can be changed based on "User1.Id" or "Id".
        Comparable<Object> rhsValue = (Comparable)rhs.getColumn(fieldName_another);
        if(lhsValue == null || rhsValue == null) return false;
        switch(operator){
            case GT :
                  return lhsValue.compareTo(rhsValue) == 1 ;
            case EQU :
                  return lhsValue.compareTo(rhsValue) == 0;
            case GTE :
                  return lhsValue.compareTo(rhsValue) >= 0;
            case LTE :
                  return lhsValue.compareTo(rhsValue) <= 0;
            case LT :
                  return lhsValue.compareTo(rhsValue) < 0; 
            default :
                  return false;   
       }
    }

    public void castGivenArgument(Object RHSFieldOrConstValue){
        if(RHSFieldOrConstValue instanceof Field){
           Field field = (Field)RHSFieldOrConstValue;
           tableName_another = field.tableName_self;
           fieldName_another = field.fieldName_self;
           fieldType = FIELDTYPES.LEFT_IS_FIELD_AND_RIGHT_IS_FIELD;
       }
       else{
           if((RHSFieldOrConstValue instanceof Number) || (RHSFieldOrConstValue instanceof String)){
              fieldValue = (Comparable<Object>)RHSFieldOrConstValue;
           }
           else{
               throw new IllegalArgumentException("The arguement ["+RHSFieldOrConstValue+"] should be field value"+
               "or constant of type comparable");
           }              
       }
    }


    
}
