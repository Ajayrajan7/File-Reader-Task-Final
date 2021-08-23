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
//Select("User1").leftJoin("User1").on(
        //   new Field("User1","id").equals(new Field("User2.id")
        // ).and(
        //  new Field("User2","id").lt(20)
        // ).innerjoin("User3").
        //  new Field("User1","id").equals(new Field("User2","id")
        // )

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
    protected List<String> chainedTableName = new LinkedList<>();
    private String tempFileName = JoinUtil.calculatedTempFileName();
    private JoinResult jr = new JoinResult(tempFileName,chainedTableName);
    private JoinConstraint joinConstraints;
    private TYPES type;
    private String tempTableName ;
    
    //Select("User1")
        //  .leftJoin("User1").on(
        //   new Field("User1","id").equals(new Field("User2.id")
        // ).and(
        //  new Field("User2","id").lt(20)
        // ).rightJoin("User3").
        //  new Field("User1","id").equals(new Field("User2","id")
        // )

    public JoinConstraint leftJoin(String RHSTableName) throws JoinException,NoSuchTableException{
         type = TYPES.LEFTJOIN;
         addTableName(RHSTableName);
         return new JoinConstraint(jr);
    }

    public JoinConstraint InnerJoin(String RHSTableName) throws JoinException,NoSuchTableException{
        type = TYPES.INNERJOIN;
        addTableName(RHSTableName);
        return new JoinConstraint(jr);
    }

    public JoinConstraint rightJoin(String RHSTableName) throws JoinException,NoSuchTableException{
        type = TYPES.RIGHTJOIN;
        addTableName(RHSTableName);
        return new JoinConstraint(jr);
    }

    public void addTableName(String tableName) throws NoSuchTableException{
            chainedTableName.add(tableName);
    }

    public boolean isValidTable(String tableName){
        return chainedTableName.contains(tableName);
    }

    // public void checkStateAndThrowException() throws JoinException{
    //     if(STATE < 0){
    //         throw new JoinException("[LEFT|RIGHT|INNER] Join without Constraints");
    //     }
    // }




}


public class JoinException extends Exception{
     public JoinException(String message){
        super(message);
     }
}

        // JoinResult jr = Select("User1")
        //  .leftJoin("User1").on(
        //   new Field("User1","id").equals(new Field("User2.id")
        // ).and(
        //  new Field("User2","id").lt(20)
        // )
        // jr.innerjoin("User3").
        //  new Field("User1","id").equals(new Field("User2","id")
        // )

public class JoinResult extends Join implements RowGeneratorImpl{
    private String tempTableName;
    public JoinResult(String tempTableName,List<String> chainedTableName){
        this.tempTableName = tempTableName;
        this.chainedTableName = chainedTableName;
    }

    @Override
	public boolean hasNext(){
        return false;
    }
    /**
     * @return Row of two joined tables combined together with all fields
     */
    @Override 
    public Row next(){

    }
 

    /**
     * @return void 
     * This method should be called interanally once the join results exhausted.
     */
    private void deleteFile(){
        /*
        Delete the file with the name of the tempTableName
        */
    }

    public void finalize(){
        deleteFile();
    }

}

public class JoinConstraint{
    private List<WrappedField> constrainChain = new LinkedList<>();
    private byte STATE = -1;
    private JoinResult jr;
    
    public JoinConstraint(JoinResult jr){
        this.jr = jr;
    }
    public JoinConstraint on(Field finalField) throws IllegalStateException{
        if(STATE != 0) throw new IllegalStateException("on clause Called multiple times");
        constrainChain.add(new WrappedField(ExpressionName.AND, finalField));
        STATE++;
        return this;
   }

   public JoinConstraint and(Field finalField) throws IllegalStateException{
        checkStateAndThrowException();
        constrainChain.add(new WrappedField(ExpressionName.AND, finalField));
        return this;
   }

   public JoinConstraint or(Field finalField) throws IllegalStateException{
        checkStateAndThrowException();
        constrainChain.add(new WrappedField(ExpressionName.OR, finalField));
        return this;
   }

   public JoinResult getResult(){
       return jr;
   }



   public List<WrappedField> getConstaintChain(){
       return constrainChain;
   }

   public boolean hasField(Field field){

       return constructFieldWithTableNameAndCheckIfFieldExists(field);
   }


   private boolean constructFieldWithTableNameAndCheckIfFieldExists(Field field){
      return checkIfTableConstainsField(field.tableName_self, field.fieldName_self);
   }

  
   
   private boolean checkIfTableConstainsField(String tableName,String fieldName){
       /* check if the table hashmap constain the given field */
       boolean hasField = true;
       return hasField ? true : false;
   }


   private void checkStateAndThrowException() throws IllegalStateException{
       if(STATE > 0){
          throw new IllegalStateException("On clause should be called before adding logical statements [AND|OR]");
       }
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

/*
table1ptr=file(users1);
table2ptr=file(users2);
while(table1ptr!=null){
    Row row1 = table1ptr.getRow();
    while(table2ptr!=null){
        Row row2 = table2ptr.getRow();
           boolean isValidRow = reducer(row1,row2);
    }
}

 public  boolean reduce(boolean prevState,WrappedCondition wrapped,LHS,RHS){
         final boolean RHSSTATUS = RHS.getExpression().evaluate(LHS,RHS);
         switch(wrapped.getExpressionName()){
            case AND :
                 return prevstate && RHSSTATUS;
            case OR :
                 return prevsate || RHSSTATUS;
            default :
                 return false;
         }
     }
*/