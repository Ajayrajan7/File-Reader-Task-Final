import java.util.*;
import java.io.*;
public class Select_{

	public List<LinkedHashMap<String,Object>> getData(String tableName, String ...args){

		// String path=GetTableDetails.dataPath+File.separator+tableName+".txt";
		// List<LinkedHashMap<String,Object>> realOrder = GetTableDetails.tablesVsFieldDetails.get(tableName);

		// try{
		// 	File f = new File(path);
		// 	BufferedReader br = new BufferedReader(new FileReader(f));

		// 	String line;
		// 	String[] parts;
		// 	LinkedHashMap<String,Object> outputData=new ArrayList<>();
		// 	Set<String> neededCols = new HashSet<String>(Arrays.asList(args));
		// 	int index=0;

		// 	while((line=br.readLine())!=null){
		// 		parts=line.split(" ");
		// 		Row r = new Row();
		// 		LinkedHashMap<String,Object> currentLineMap = new LinkedHashMap<>();
		// 		for(Map.Entry<String,Class> entry:realOrder.entrySet()){
		// 			// if(neededCols.contains(entry.getKey())){
		// 				if(entry.getValue().toString().equals("class java.lang.String")){
		// 					// parts[index]=parts[index].replaceAll("\\\\s"," ");
		// 					currentLineMap.put(entry.getKey(),parts[index]);
		// 				}else if(entry.getValue().toString().equals("class java.lang.Integer")){
		// 					currentLineMap.put(entry.getKey(),Integer.valueOf(parts[index]));
		// 				}else if(entry.getValue().toString().equals("class java.lang.Long")){
		// 					currentLineMap.put(entry.getKey(),Long.valueOf(parts[index]));
		// 				}else if(entry.getValue().toString().equals("class java.lang.Double")){
		// 					currentLineMap.put(entry.getKey(),Double.valueOf(parts[index]));
		// 				}else if(entry.getValue().toString().equals("class java.lang.Float")){
		// 					currentLineMap.put(entry.getKey(),Float.valueOf(parts[index]));
		// 				}else if(entry.getValue().toString().equals("class java.lang.Boolean")){
		// 					currentLineMap.put(entry.getKey(),Boolean.valueOf(parts[index]));
		// 				}
		// 			// }
		// 			index++;
		// 		}
		// 		r.setRowDetails(currentLineMap);
		// 		// ReducerUtil reducerUtil = new ReducerUtil();
		// 		// reducerUtil.initialize(r,);
		// 		if(reducerUtil.parseAllCriterasAndReturnFinalBoolean)
		// 			outputData.add(currentLineMap);
		// 		index=0;
		// 	}
		// 	br.close();
		// 	return outputData;
		// }catch(Exception e){
		// 	System.out.println(e);
		// }
		return null;
	}

}