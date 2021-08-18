import java.util.*;
import java.io.*;
public class RowGenerator implements RowGeneratorImpl{
	private String tableName;
	private int total_row_size;
	private int current_row_no=0;
	private RandomAccessFile raf = null;
	private byte[] buffer;
	private LinkedHashMap<String,Integer> columnVsSize;
	private LinkedHashMap<String,Types> tablesVsFieldDetails;
	private boolean EXHAUSTED = false;

	public RowGenerator(String tableName) throws FileNotFoundException{
		this.tableName=tableName;
		this.raf = new RandomAccessFile(GetTableDetails.dataPath+"\\"+tableName+".txt", "rw");
		this.columnVsSize = GetTableDetails.tableVsSize.get(tableName);
		this.tablesVsFieldDetails = GetTableDetails.tablesVsFieldDetails.get(tableName);
		this.total_row_size=columnVsSize.get("Total_Row_Size")+3;
		this.buffer = new byte[total_row_size];
	}

	@Override
	public Row next() throws RowExhausedException{
		   
			LinkedHashMap<String,Object> rowDetails = new LinkedHashMap<>();
			Row row = new Row(tableName);
			int ptr=1;
			//Iterating each column in the buffer.
			for(String key:columnVsSize.keySet()){
				String data = new String(buffer,ptr,ptr+columnVsSize.get(key));
				Types type = tablesVsFieldDetails.get(key);

				//Finding the type of the current column and typecasting it to object type
				switch(type){
		             case INTEGER :
		                   rowDetails.put(key,Integer.valueOf(data));
		             case STRING :
		                   rowDetails.put(key,data);
		             case FLOAT :
		                   rowDetails.put(key,Float.valueOf(data));
		             case DOUBLE :
		                   rowDetails.put(key,Double.valueOf(data));
		             case LONG :
		                   rowDetails.put(key,Long.valueOf(data));
		             case BOOLEAN :
		                   rowDetails.put(key,Boolean.valueOf(data));  
        		}
        		ptr+=columnVsSize.get(key);
			}
			row.setRowDetails(rowDetails);
			row.setSeekPos(current_row_no);
			row.setRowLength(total_row_size);
			current_row_no+=total_row_size;
			return row;
	}


	@Override
	public boolean haxNext(){
		try{
			raf.seek(current_row_no);
			raf.read(buffer);

			//Ignoring rows that starts with '0'
			while(buffer[0]!=1){
				current_row_no+=total_row_size;
				raf.seek(current_row_no);
				raf.read(buffer);
			}
      		return true;
	   	} catch(){
	      	System.out.println(e);
	   	}
		finally{
			try {
				raf.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	   	return false;
	}
}
