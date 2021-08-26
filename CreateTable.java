import java.util.*;
import java.io.*;
public class CreateTable {
    String tablename;
    LinkedHashMap<String,DataTypes> columnList = new LinkedHashMap<>();
    public CreateTable(String tablename) throws TableNameExistsAlreadyException{
        if(GetTableDetails.tablesVsFieldDetails.get(tablename)!=null){
            throw new TableNameExistsAlreadyException("Table name exists already");
        }
        this.tablename = tablename;    
    }

    public void addColumn(String columnName, DataTypes columnType){
        columnList.put(columnName,columnType);
    }

    public boolean create(){
        LinkedHashMap<String,Integer> tableVsSizeDetails = new LinkedHashMap<>();
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,DataTypes> entry:columnList.entrySet()){
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue());
            sb.append(',');
            tableVsSizeDetails.put(entry.getKey(), GetTableDetails.getSizeForType(entry.getValue()));
        }
        sb.deleteCharAt(sb.length()-1);
        GetTableDetails.createFileIfNotExists(tablename);
        GetTableDetails.createPropsFileForDeletionTracking(tablename);
        GetTableDetails.tablesVsFieldDetails.put(tablename, columnList);
        GetTableDetails.tableVsSize.put(tablename,tableVsSizeDetails);
        addToPropsFile(sb.toString());
        return true;
    }

    public void addToPropsFile(String value) {
        OutputStream output = null;
       try{
            Properties p = new Properties();
            p.setProperty(tablename,value);
            output = new FileOutputStream(GetTableDetails.confPath+File.separator+"Tables.props",true);
            p.store(output,null);
       }catch(Exception e){
           e.printStackTrace();
       }finally{
            FileUtil.safeClose(output);
       }
    }
}   
