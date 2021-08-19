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
		this.raf = FileUtil.getRandomAccessInstance(tableName);
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
			for(String key:tablesVsFieldDetails.keySet()){
				String data = (new String(buffer,ptr,columnVsSize.get(key)-1)).trim();
				Types type = tablesVsFieldDetails.get(key);
				// System.out.println(ptr+" "+columnVsSize.get(key));
				// System.out.println(key+" "+data+" "+type);
				//Finding the type of the current column and typecasting it to object type
				switch(type){
					case STRING :
		                   rowDetails.put(key,data);
						   break;
		            case INTEGER :
		                   rowDetails.put(key,Integer.valueOf(data));
						   break;	             
					case FLOAT :
		                   rowDetails.put(key,Float.valueOf(data));
						   break;
					case DOUBLE :
		                   rowDetails.put(key,Double.valueOf(data));
						   break;
					case LONG :
		                   rowDetails.put(key,Long.valueOf(data));
						   break;
					case BOOLEAN :
		                   rowDetails.put(key,Boolean.valueOf(data));  
        		}
        		ptr+=columnVsSize.get(key);
			}
			row.setRowDetails(rowDetails);
			row.setSeekPos(current_row_no+1);
			row.setRowLength(total_row_size);
			current_row_no+=total_row_size;
			return row;
	}


	@Override
	public boolean hasNext(){
		try{
			raf.seek(current_row_no);
			Arrays.fill(buffer, (byte)0);
			raf.readFully(buffer);
			System.out.println("Buffer "+new String(buffer));

			// System.out.println("Buf[0] ="+(char)buffer[0]);
			//Ignoring rows that starts with '0'

			
			while(buffer[0]!=49){  // ascii code of 1 is 49 which is stored in file
				current_row_no+=total_row_size;
				raf.seek(current_row_no);
				Arrays.fill(buffer, (byte)0);
				raf.readFully(buffer);
			}
      		return true;
	   	} catch(EOFException e){
	      	System.out.println(e);
			try {
				FileUtil.releaseFile();
				raf.close();
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
			FileUtil.releaseFile();
	   	}catch(IOException e){
			System.out.println(e);
		}
		finally{
			
		}
	   	return false;
	}
}
