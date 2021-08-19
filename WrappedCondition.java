class WrappedCondition {
     private Expression expression;
     private ExpressionName expressionName; //AND OR 
    
     public WrappedCondition(Expression expression,ExpressionName expressionName){
         this.expression = expression;
         this.expressionName = expressionName;
     }

     public ExpressionName getExpressionName(){
         return expressionName;
     }

    public Expression getExpression(){
        return expression;
    }

    public void setExpression(Expression expression){
        this.expression=expression;
    }


}