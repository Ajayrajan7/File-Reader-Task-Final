import java.util.*;
import java.io.*;
public class Insert{
   private static String fileName;
   private Set<String> Fields;
   private List<LinkedHashMap<String,Object>> OrderedRows = new ArrayList<>();


   public Insert(String tableName,final List<HashMap<String,Object>> rowEntries) throws IllegalArgumentException {
       this.fileName = tableName;
       for(HashMap<String,Object> map:rowEntries){
       		checkOrderAndArrangeIfNot(tableName,map);	
       }
    //    System.out.println(OrderedRows);
    }

    public int addToTable() throws Exception{
    	String path=GetTableDetails.dataPath+File.separator+fileName+".txt";
    	FileWriter fw = new FileWriter(path, true);
		BufferedWriter bw = null;
		PrintWriter out = null;
    	try{
    		bw = new BufferedWriter(fw);
    		out = new PrintWriter(bw);

    		for(LinkedHashMap<String,Object> map:OrderedRows){
				out.print(1);
    			for(Map.Entry<String,Object> entry:map.entrySet()){
    				out.print(padData(fileName,entry.getKey(),entry.getValue()));
    			}
    			out.print("\n");
    		} 
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
			FileUtil.safeClose(out);
			FileUtil.safeClose(bw);
			FileUtil.safeClose(fw);
		}
        return 0; 
    }

	 static String padData(String tablename,String key,Object value){
		int size = (GetTableDetails.tableVsSize.get(tablename)).get(key);
		return String.format("%"+(-size)+"s",value.toString()).replace(' ',' ');
	}

    private void  checkOrderAndArrangeIfNot(String tableName,HashMap<String,Object> map){
    	LinkedHashMap<String,Object> finalOrder = new LinkedHashMap<>();
    	LinkedHashMap<String,DataTypes> realOrder = GetTableDetails.tablesVsFieldDetails.get(tableName);

    	for(Map.Entry<String,DataTypes> entry:realOrder.entrySet()){
    		if(map.get(entry.getKey())!=null){
				finalOrder.put(entry.getKey(),map.get(entry.getKey()));
    		}
    	}
    	OrderedRows.add(finalOrder);
    }
}