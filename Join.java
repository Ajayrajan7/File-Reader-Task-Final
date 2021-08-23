import java.util.*;

public class Join{
    protected List<String> chainedTableName = new LinkedList<>();
    private String tempFileName = JoinUtil.calculatedTempFileName();
    private JoinResult jr = new JoinResult(tempFileName,chainedTableName);
    private JoinConstraint joinConstraint = new JoinConstraint(this);
    private TYPES type;
    private String LHSTableName;
    private String RHSTableName;
    public JoinConstraint leftJoin(String RHSTableName) throws JoinException,NoSuchTableException{
            type = TYPES.LEFTJOIN;
            addTableName(RHSTableName,true);
            setRHSTableName(RHSTableName);
            return joinConstraint;
    }

    public JoinConstraint innerJoin(String RHSTableName) throws JoinException,NoSuchTableException{
            type = TYPES.INNERJOIN;
            addTableName(RHSTableName,true);
            setRHSTableName(RHSTableName);
            
            //  checkStateAndThrowException();
            return joinConstraint;
    }

    public JoinConstraint rightJoin(String RHSTableName) throws JoinException,NoSuchTableException{
            type = TYPES.RIGHTJOIN;
            addTableName(RHSTableName,true);
            setRHSTableName(RHSTableName);
            //  checkStateAndThrowException();
            return joinConstraint;

    public void addTableName(String tableName,boolean addToChain) throws NoSuchTableException{
        if(addToChain)
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

    protected void setLHSTableName(String LHSTableName,boolean addToChain) throws NoSuchTableException{
        this.LHSTableName = LHSTableName;
        addTableName(LHSTableName,addToChain);

    }

    protected void setRHSTableName(String RHSTableName){
        this.RHSTableName = RHSTableName;
    }

    protected String getLHSTableName(){
        return LHSTableName;
    }

    protected String getRHSTableName(){
        return RHSTableName;
    }
    
    public String getTempFileName(){
        return tempFileName;
    }

    public TYPES getType(){
        return type;
    }
    //API to access joins

    protected JoinResult getResult() {
        try{
            String lhsTable = getLHSTableName();
            String rhsTable = getRHSTableName();
            RowGenerator lhsPtr = new RowGenerator(lhsTable);
            RowGenerator rhsPtr = new RowGenerator(rhsTable);
            
            boolean isAtleastOneRowMatched = false;
            LinkedHashMap<String,Types> tempFileVsFieldDetails = createNewMappingForTempFileFields(lhsTable,rhsTable);
            LinkedHashMap<String,Integer> tempFileVsSizeDetails = createNewMappingForTempFileSize(lhsTable,rhsTable);
            
            GetTableDetails.tablesVsFieldDetails.put(getTempFileName(),tempFileVsFieldDetails);
            GetTableDetails.tableVsSize.put(getTempFileName(),tempFileVsSizeDetails);


            JoinUtil joinUtil = new JoinUtil();
            joinUtil.initializeFile(getTempFileName());

            while(lhsPtr.hasNext()){
                Row row1 = lhsPtr.next();
                isAtleastOneRowMatched = false;
                Row row2=null;
                while(rhsPtr.hasNext()){
                    row2 = rhsPtr.next();
                    ReducerUtilJoin reducerUtilJoin = new ReducerUtilJoin();
                    reducerUtilJoin.initialize(row1, row2, joinConstraint.getConstraintChain());
                    if(reducerUtilJoin.parseAllCriterasAndReturnFinalBoolean()){
                        isAtleastOneRowMatched = true;
                        joinUtil.addToTable(lhsTable, rhsTable, getType(), true, row1.getRowDetails(), row2.getRowDetails());
                    }
                }

                if(row2==null)   break;

                //If the condtion did'nt match between two rows
                if(!isAtleastOneRowMatched){
                    joinUtil.addToTable(lhsTable, rhsTable, getType(), false, row1.getRowDetails(), row2.getRowDetails());
                }

                rhsPtr = new RowGenerator(rhsTable);
            }   
            joinUtil.flush();
            return jr;
        }catch(Exception e){
            e.printStackTrace();
        }
        return jr;
    }

    public LinkedHashMap<String,Types> createNewMappingForTempFileFields(String lhsTable,String rhsTable){
        LinkedHashMap<String,Types> lhsFieldDetails = GetTableDetails.tablesVsFieldDetails.get(lhsTable);
        LinkedHashMap<String,Types> rhsFieldDetails = GetTableDetails.tablesVsFieldDetails.get(rhsTable);
        LinkedHashMap<String,Types> tempFileVsFieldDetails = new LinkedHashMap<>();
        for(Map.Entry<String,Types> entry:lhsFieldDetails.entrySet()){
            tempFileVsFieldDetails.put(lhsTable+"."+entry.getKey(), entry.getValue());
        }
        for(Map.Entry<String,Types> entry:rhsFieldDetails.entrySet()){
            tempFileVsFieldDetails.put(rhsTable+"."+entry.getKey(), entry.getValue());
        }
        return tempFileVsFieldDetails;
    }

    public LinkedHashMap<String,Integer> createNewMappingForTempFileSize(String lhsTable,String rhsTable){
        LinkedHashMap<String,Integer> lhsFieldDetails = GetTableDetails.tableVsSize.get(lhsTable);
        LinkedHashMap<String,Integer> rhsFieldDetails = GetTableDetails.tableVsSize.get(rhsTable);
        LinkedHashMap<String,Integer> tempFileVsFieldDetails = new LinkedHashMap<>();
        int total_size=0;
        for(Map.Entry<String,Integer> entry:lhsFieldDetails.entrySet()){
            tempFileVsFieldDetails.put(lhsTable+"."+entry.getKey(), entry.getValue());
            total_size+=entry.getValue();
        }
        for(Map.Entry<String,Integer> entry:rhsFieldDetails.entrySet()){
            tempFileVsFieldDetails.put(rhsTable+"."+entry.getKey(), entry.getValue());
            total_size+=entry.getValue();
        }
        tempFileVsFieldDetails.put("Total_Row_Size",total_size);
        return tempFileVsFieldDetails;
    }
    

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