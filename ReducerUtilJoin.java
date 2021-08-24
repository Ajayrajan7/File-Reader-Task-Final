import java.util.*;
public class ReducerUtilJoin {
    private  Row row1;
    private  Row row2;
    private List<WrappedField> constrainChain;
    private boolean state = true;

    public void initialize(Row row1, Row row2, List<WrappedField> constrainChain){
        this.row1 = row1;
        this.row2 = row2;
        this.constrainChain = constrainChain;
    }

    public boolean parseAllCriterasAndReturnFinalBoolean(){
        for(WrappedField wrapped : constrainChain){
            state = reduce(state,wrapped);
        }
        return state;
    }

    public  boolean reduce(boolean prevState,WrappedField wrapped){
        final boolean currentState = wrapped.getField().evaluate(row1, row2);
        switch(wrapped.getExpressionName()){
           case AND :
                return prevState && currentState;
           case OR :
                return prevState || currentState;
           default :
                return false;
        }
    }
}
