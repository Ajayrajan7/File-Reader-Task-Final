import java.util.*;
import java.io.*;
public class Rows{
	private  String fileName;
   private Set<String> Fields;
   private List<LinkedHashMap<String,Object>> OrderedRows = new ArrayList<>();


   public Rows(String tableName,final List<HashMap<String,Object>> rowEntries) throws IllegalArgumentException {
       this.fileName = tableName+".txt";
       for(HashMap<String,Object> map:rowEntries){
       		checkOrderAndArrangeIfNot(tableName,map);	
       }
       System.out.println(OrderedRows);
    }

    public int addToTable() throws Exception{
    	String path=GetTableDetails.dataPath+"\\"+fileName;
    	FileWriter fw = new FileWriter(path, true);
    	try{
    		BufferedWriter bw = new BufferedWriter(fw);
    		PrintWriter out = new PrintWriter(bw);
    		for(LinkedHashMap<String,Object> map:OrderedRows){
    			for(Map.Entry<String,Object> entry:map.entrySet()){
    				out.print(entry.getValue());
    			}
    			out.print("\n");
    		} 
    		out.close();
    		bw.close();
    		fw.close();
    	}catch(Exception e){
    		System.out.println(e);
    	}
        return 0; 
    }

    private void  checkOrderAndArrangeIfNot(String tableName,HashMap<String,Object> map){
    	LinkedHashMap<String,Object> finalOrder = new LinkedHashMap<>();
    	LinkedHashMap<String,Class> realOrder = GetTableDetails.tablesVsFieldDetails.get(tableName);

    	for(Map.Entry<String,Class> entry:realOrder.entrySet()){
    		if(map.get(entry.getKey())!=null){
    			// if(entry.getValue().toString().equals("class java.lang.String")){
	    		// 	String val = (String)map.get(entry.getKey());
	    		// 	val= val.replaceAll(" ","\\\\s");
	    		// 	finalOrder.put(entry.getKey(),val);
	    		// }
	    		// else
    				finalOrder.put(entry.getKey(),map.get(entry.getKey()));
    		}
    	}
    	OrderedRows.add(finalOrder);
    }
}