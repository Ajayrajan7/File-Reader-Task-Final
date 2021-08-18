import java.util.LinkedHashMap;

public class Row {
    //Make the below fields private and make getters and setters.
    private String tableName;
    private int seekPos;
    private int rowLength;
    private LinkedHashMap<String,Object> rowDetails;

    public void setSeekPos(int seekPos){
        this.seekPos=seekPos;
    }

    public int getSeekPos(){
        return seekPos;
    }

    public void setRowLength(int rowLength){
        this.rowLength = rowLength;
    }
    
    public int getRowLength(){
        return rowLength;
    }

    public Row(String tableName){
        this.tableName=tableName;
    }
    void setRowDetails(LinkedHashMap<String,Object> rowDetails){
        this.rowDetails = rowDetails;
    }
    
    LinkedHashMap<String,Object> getRowDetails(){
        return rowDetails;
    }

    Object getColumn(String key){
        return rowDetails.get(key);
    }

    double getDouble(String columnName) throws ClassCastException,NoSuchColumnException{
        try {
          return  (double)rowDetails.get(columnName);
        }
        catch(NullPointerException ex){
            throw new NoSuchColumnException("Column "+columnName+" is not found in the table " +tableName);
        }
        
    }

    long getLong(String columnName) throws ClassCastException,NoSuchColumnException{

        
        try {
            return (long)rowDetails.get(columnName);
          }
          catch(NullPointerException ex){
              throw new NoSuchColumnException("Column "+columnName+" is not found in the table " +tableName);
          }
    }

    int getInt(String columnName) throws ClassCastException,NoSuchColumnException{    
        try {
            return (int)rowDetails.get(columnName);
          }
          catch(NullPointerException ex){
              throw new NoSuchColumnException("Column "+columnName+" is not found in the table " +tableName);
          }
    }

    float getFloat(String columnName) throws ClassCastException,NoSuchColumnException{
        try {
            return (float)rowDetails.get(columnName);
          }
          catch(NullPointerException ex){
              throw new NoSuchColumnException("Column "+columnName+" is not found in the table " +tableName);
          }
    }

    String getString(String columnName) throws ClassCastException,NoSuchColumnException{
        //get the field
        String temp = (String)rowDetails.get(columnName);
        if(null == temp)  throw new NoSuchColumnException("Column "+columnName+" is not found in the table " +tableName);
        return temp;
    }

}
