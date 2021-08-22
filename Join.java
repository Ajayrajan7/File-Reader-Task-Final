import java.util.*;
import java.io.*;
class Field{
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
           tableName_another = field.fieldName_self;
           fieldName_another = field.fieldName_self;
           fieldType = FIELDTYPES.LEFT_IS_FIELD_AND_RIGHT_IS_FIELD;
       }
       else{
           if((RHSFieldOrConstValue instanceof Number) || (RHSFieldOrConstValue instanceof String)){
              fieldValue = (Comparable<Object>)RHSFieldOrConstValue;
           }
           else{
               throw new IllegalArgumentException("The arguement ["+RHSFieldOrConstValue+"] should be field value"+
               "or constant");
           }              
       }
    }

//Select("User1").leftJoin("User1").on(
        //   new Field("User1","id").equals(new Field("User2.id")
        // ).and(
        //  new Field("User2","id").lt(20)
        // ).innerjoin("User3").
        //  new Field("User1","id").equals(new Field("User2","id")
        // )
    
}


//Select("User1").leftJoin("User1").on(
        //   new Field("User1","id").equals(new Field("User2.id")
        // ).and(
        //  new Field("User2","id").lt(20)
        // ).innerjoin("User3").
        //  new Field("User1","id").equals(new Field("User2","id")
        // )

enum FIELDTYPES {
     LEFT_IS_FIELD_AND_RIGHT_IS_CONSTANT,
    //  LEFT_IS_CONSTANT_AND_RIGHT_IS_FIELD,
    //  LEFT_IS_CONSTANT_AND_RIGHT_IS_CONSTANT,
     LEFT_IS_FIELD_AND_RIGHT_IS_FIELD
}


public class JoinResponse{
   private List<Row> left = new ArrayList<>();
   private List<Row> right = new ArrayList<>();
   private Iterator<Row> itleft;
   private Iterator<Row> itright;
   private int size;
   public void put(Row left,Row right){
       this.left.add(left);
       this.right.add(right);
       size++;
   }
   public int getSize(){
        return size;
   }

   public boolean hasNext(){
       return itleft.hasNext() && itright.hasNext();
   }

   public void intiateIterator(){
       itleft = left.iterator();
       itright =  right.iterator();
   }

   public Row[] next(){
       return new Row[]{itleft.next(),itright.next()};
   }

}

public class WrappedField{
    private ExpressionName expressionName;
    private Field field ;
    public WrappedField(ExpressionName expressionName,Field field){
         this.expressionName = expressionName;
         this.field = field;
    }

    public Field getField(){
        return field;
    }

}

public class JoinUtil {
    public static String WriteJoinsToTable(JoinResponse response){
        response.intiateIterator();
        while(response.hasNext()){
            Row[] rows = response.next();
            /* Write the rows in the file in order*/
        }
    }

    public static String calculatedTempFileName(){
        return Long.toHexString(Double.doubleToLongBits(Math.random()))+".txt";
    }

    public static FileOutputStream getOPStream(String filename){
        String tempDblocation = "" ; /* load the temp db location here*/
        return new FileOutputStream(tempDblocation+File.separator+filename,false);
    }
}

public class Join{
    private List<String> chainedTableName = new LinkedList<>();
    private Queue<JoinConstraint> joinChain = new LinkedList<>();
    private TYPES type;
    private int STATE = -1;

    //Select("User1")
        //  .leftJoin("User1").on(
        //   new Field("User1","id").equals(new Field("User2.id")
        // ).and(
        //  new Field("User2","id").lt(20)
        // ).innerjoin("User3").
        //  new Field("User1","id").equals(new Field("User2","id")
        // )

    public JoinConstraint leftJoin(String RHSTableName) throws JoinException{
         checkStateAndThrowException();
         return new JoinConstraint();
    }

    public JoinConstraint InnerJoin(String RHSTableName) throws JoinException{
        checkStateAndThrowException();
        return new Join();
    }

    public JoinConstraint rightJoin(String RHSTableName) throws JoinException{
        checkStateAndThrowException();
        return new Join();
    }

    public void addTableName(String tableName) throws NoSuchTableException{
            chainedTableName.add(tableName);
    }

    public boolean isValidTable(String tableName){
        return chainedTableName.contains(tableName);
    }

    public void checkStateAndThrowException() throws JoinException{
        if(STATE < 0){
            throw new JoinException("[LEFT|RIGHT|INNER] Join without Constraints");
        }
    }




}


public class JoinException extends Exception{
     public JoinException(String message){
        super(message);
     }
}

//Select("User1")
        //  .leftJoin("User1").on(
        //   new Field("User1","id").equals(new Field("User2.id")
        // ).and(
        //  new Field("User2","id").lt(20)
        // ).innerjoin("User3").
        //  new Field("User1","id").equals(new Field("User2","id")
        // )

public class JoinConstraint{
    private List<WrappedField> constrainChain = new LinkedList<>();
    public JoinConstraint on(Field finalField){
        constrainChain.add(new WrappedField(ExpressionName.AND, finalField));
        return this;
   }

   public JoinConstraint equals()
        
   }

   public boolean hasField(Field field){

       return constructFieldWithTableNameAndCheckIfFieldExists(field);
   }


   private boolean constructFieldWithTableNameAndCheckIfFieldExists(Field field){
      return checkIfTableConstainsField(field.tableName, field.fieldName);
   }

  
   
   private boolean checkIfTableConstainsField(String tableName,String fieldName){
       /* check if the table hashmap constain the given field */
       boolean hasField = true;
       return hasField ? true : false;
   }
}
    

enum TYPES {
        LEFTJOIN,
        RIGHTJOIN,
        INNERJOIN,
        // CROSSJOIN
    }




// Select("User1").join(new Join("User2"),JoinTypes.INNERJOIN).on("User1.id",Operator.EQUALS,"User2.id").and("User1.name",Operator.EQUALS,"chella")
//.join(new Join("User3"),JoinTypes.LEFTJOIN)).on("User3.id",Operator.GT,20);

//select * from User1 INNER JOIN User2 on User1.id = User2.id and User1.name = "Chella" 
//   LEFT JOIN join User3 on User3.id > 20

// Select("User1").join(new Join("User2"),JoinTypes.INNERJOIN).on(new Field("User1.id"),Operator.EQUALS,new Field("User2.id")).and(new Field("User1.name"),Operator.EQUALS,"chella")
//.join(new Join("User3"),JoinTypes.LEFTJOIN)).on("User3.id",Operator.GT,20);


//Select("User1").where(new Field("Id").equals("20"))
//Select("User1").leftJoin("User1").on(new Field("User1.id").equals(new Field("User2.id")).and(new Field("User2.id").lt(20))
/*
SELECT role_names.role_name, permissions.permission_name 
FROM role_names  JOIN role_to_permission  ON role_names.role_id=role_to_permission.role_id
 JOIN permissions  ON role_to_permission.permission_id=permissions.permission_id;
*/

// create table test1(id int,name varchar(20));
// create table test2(id int,name varchar(20));
// insert into test1 values(1,"chella"),(2,"aaaaa"),(3,"dsfdsf"),(4,"dsffwere");
// insert into test2 values(5,"csfdsdfhella"),(2,"aaaaa"),(6,"dgfdsfdsf"),(7,"fsgdsffwere");
// --select * from test1 right join test2 ;