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