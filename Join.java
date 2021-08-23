import java.util.*;

public class Join{
    protected List<String> chainedTableName = new LinkedList<>();
    private String tempFileName = JoinUtil.calculatedTempFileName();
    private JoinResult jr = new JoinResult(tempFileName,chainedTableName);
    private JoinConstraint joinConstraints;
    private TYPES type;
    private String tempTableName;

    public JoinConstraint leftJoin(String RHSTableName) throws JoinException{
        try{
            type = TYPES.LEFTJOIN;
            addTableName(RHSTableName);
            //  checkStateAndThrowException();
            return new JoinConstraint(jr);
        }catch(NoSuchTableException e){
            System.out.println(e);
        }
        return null;
    }

    public JoinConstraint InnerJoin(String RHSTableName) throws JoinException{
        try{
            type = TYPES.INNERJOIN;
            addTableName(RHSTableName);
            //  checkStateAndThrowException();
            return new JoinConstraint(jr);
        }catch(NoSuchTableException e){
            System.out.println(e);
        }
        return null;
    }

    public JoinConstraint rightJoin(String RHSTableName) throws JoinException{
        try{
            type = TYPES.RIGHTJOIN;
            addTableName(RHSTableName);
            //  checkStateAndThrowException();
            return new JoinConstraint(jr);
        }catch(NoSuchTableException e){
            System.out.println(e);
        }
        return null;
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



enum FIELDTYPES {
     LEFT_IS_FIELD_AND_RIGHT_IS_CONSTANT,
    //  LEFT_IS_CONSTANT_AND_RIGHT_IS_FIELD,
    //  LEFT_IS_CONSTANT_AND_RIGHT_IS_CONSTANT,
     LEFT_IS_FIELD_AND_RIGHT_IS_FIELD
}


// JoinResult jr = Select("User1")
//  .leftJoin("User2").on(
//   new Field("User1","id").equals(new Field("User2","id")
// ).and(
//  new Field("User2","id").lt(20)
// ).getResult();
// 
// jr.innerjoin("User3").
//  new Field("User1","id").equals(new Field("User2","id")
// )


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