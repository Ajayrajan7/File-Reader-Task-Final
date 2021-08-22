import java.util.*;
import java.io.*;
class Column{
    public String tableName_self,fieldName_self;
    private String tableName_another,fieldName_another;
    private int state = -1;
    public Column(String tableName,String fieldName) throws IllegalArgumentException{
       this.tableName_self=tableName;
       this.fieldName_self=fieldName;

       if(tableName == null || fieldName == null) throw new IllegalArgumentException("tableName or fieldName shouldn't be null");
    }

    public Column equals(Object RHSColumnOrConstValue) throws IllegalArgumentException{
        if(RHSColumnOrConstValue instanceof Column){
             Column column = (Column)RHSColumnOrConstValue;
             if(tableName_self.equals(column.tableName_self)){
                 /*Self table join*/

             }
             else{
                /*Another table join*/
             }
        }
        else{
            if(RHSColumnOrConstValue instanceof String){
                RHSColumnField = (String)RHSColumnOrConstValue;    
            }
            else{
                throw new IllegalArgumentException("The arguement ["+RHSColumnOrConstValue+"] should be column value"+
                "or constant");
            }              
        }
        return this;
    }

    public boolean evaluate(Row LHS,Row RHS){
        return differentiateAndEvaluate(LHS,RHS);
    }

    private boolean differentiateAndEvaluate(Row LHS,Row RHS){
         
    }


    public void setAnotherTableName(String tableName1){
        this.tableName_another = tableName1;    
    }

}


//Select("User1").leftJoin("User1").on(
        //   new Column("User1","id").equals(new Column("User2.id")
        // ).and(
        // new Column("User2","id").lt(20)
        // )

enum JOINTYPES {
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

public class WrappedColumn{
    private ExpressionName expressionName;
    private Column column ;
    public WrappedColumn(ExpressionName expressionName,Column column){
         this.expressionName = expressionName;
         this.column = column;
    }

    public Column getColumn(){
        return column;
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
    private String tableName;
    private List<WrappedColumn> constrainChain = new LinkedList<>();
    private List<Join>   joinChain = new LinkedList<>();

    public Join(String tableName){

    }

    public Join leftJoin(String RHSTableName){
         return this;
    }

    public Join InnerJoin(String RHSTableName){
        return this;
    }

    public Join rightJoin(String RHSTableName){
        return this;
    }

    public Join on(Column finalColumn){
         finalColumn.setAnotherTableName(tableName);
         constrainChain.add(new WrappedColumn(ExpressionName.AND, finalColumn));
         return this;
    }

    public Join equals(Object RHSColumnOrConstValue) throws IllegalArgumentException{
        String RHSColumnField = null;
        if(RHSColumnOrConstValue instanceof Column){

        }
        else{
            if(RHSColumnOrConstValue instanceof String){
                RHSColumnField = (String)RHSColumnOrConstValue;    
            }
            else{
                throw new IllegalArgumentException("The arguement ["+RHSColumnOrConstValue+"] should be column value"+
                "or constant");
            }
                       
        }
        return this;
    }

    

    public boolean hasField(Column column){

        return constructFieldWithTableNameAndCheckIfFieldExists(column);
    }


    private boolean constructFieldWithTableNameAndCheckIfFieldExists(Column column){
       return checkIfTableConstainsColumn(column.tableName, column.fieldName);
    }

   
    
    private boolean checkIfTableConstainsField(String tableName,String fieldName){
        /* check if the table hashmap constain the given field */
        boolean hasField = true;
        return hasField ? true : false;
    }

}



// Select("User1").join(new Join("User2"),JoinTypes.INNERJOIN).on("User1.id",Operator.EQUALS,"User2.id").and("User1.name",Operator.EQUALS,"chella")
//.join(new Join("User3"),JoinTypes.LEFTJOIN)).on("User3.id",Operator.GT,20);

//select * from User1 INNER JOIN User2 on User1.id = User2.id and User1.name = "Chella" 
//   LEFT JOIN join User3 on User3.id > 20

// Select("User1").join(new Join("User2"),JoinTypes.INNERJOIN).on(new Column("User1.id"),Operator.EQUALS,new Column("User2.id")).and(new Column("User1.name"),Operator.EQUALS,"chella")
//.join(new Join("User3"),JoinTypes.LEFTJOIN)).on("User3.id",Operator.GT,20);


//Select("User1").where(new Column("Id").equals("20"))
//Select("User1").leftJoin("User1").on(new Column("User1.id").equals(new Column("User2.id")).and(new Column("User2.id").lt(20))
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