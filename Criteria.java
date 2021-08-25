import java.util.*;
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

    public Criteria or(String key,Operator operator,Comparable value) throws IllegalStateException{
        checkSwitchAndThrowException("or");
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