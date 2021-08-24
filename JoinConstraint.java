import java.util.*;
public class JoinConstraint{
    private List<WrappedField> constrainChain = new LinkedList<>();
    private byte STATE = -1;
    private Join joinObj;
    
    public JoinConstraint(Join joinObj){
        this.joinObj = joinObj;
    }
    public JoinConstraint on(Field finalField) throws IllegalStateException{
        STATE++;
        if(STATE != 0) throw new IllegalStateException("on clause Called multiple times");
        constrainChain.add(new WrappedField(ExpressionName.AND, finalField));
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
       return joinObj.getResult();
   }



   public List<WrappedField> getConstraintChain(){
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
       boolean hasField = GetTableDetails.tablesVsFieldDetails.get(tableName)!=null && GetTableDetails.tablesVsFieldDetails.get(tableName).get(fieldName)!=null; 
       return hasField ? true : false;
   }


   private void checkStateAndThrowException() throws IllegalStateException{
       if(STATE > 0){
          throw new IllegalStateException("On clause should be called before adding logical statements [AND|OR]");
       }
   }
}