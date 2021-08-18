public class Update{
   private Criteria criteria = new Criteria();
   private Row r;
   private String[] columns;
   private String tablename ;
   public Update(String tablename){
        this.tablename = tablename;
   }
   
   public Criteria getCriteria(){
       return criteria;
   }

   public boolean executeQuery(){
        RowGenerator rowGen = new RowGenerator(tablename);
        while(rowGen.hasNext()){
            this.r = rowGen.next();
            ReducerUtil reducerUtil = new ReducerUtil();
            reducerUtil.initialize(r,criteria.TOP);
            if(reducerUtil.parseAllCriterasAndReturnFinalBoolean()){
                if(updateCurrentRow(r)){
                    continue;
                }else{
                    //to handle failure case
                }
            }
        }
        return results;
   }
}