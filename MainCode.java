import java.util.*;

public class MainCode{
	public static void main(String[] args){
		try{
			GetTableDetails.initialize(" ");

			insertRecords("table1");
			// deleteAllRecords("table1");
			getAllRecords("table1");
			// performJoin();
			// getAllRecordsWithQuery("table1");
			
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void getAllRecords(String tableName){
		try{
			Select s = new Select(tableName);
			// s.columns("user_id","name");
			List<Row> results=s.executeQuery();
			
			int i=1;
			System.out.println("\nResults:");
			for(Row r:results){
				System.out.println("\nRecord "+i);
				System.out.println("id: "+r.getInt("id"));
				System.out.println("name: "+r.getString("name"));
				System.out.println("email: "+r.getString("email"));
				i++;
			}
		}catch(Exception e){
			System.out.println(e);
		}

	}

	public static void getAllRecordsWithQuery(String tableName){
		try{
			Select s = new Select(tableName);
			s.columns("user_id","name");
			Criteria c = s.getCriteria().where("user_id",Operator.EQU,(Integer)1).and("email",Operator.EQU,"ajay@gmail.com");
			List<Row> results=s.executeQuery();
			
			int i=1;
			System.out.println("\nResults:");
			for(Row r:results){
				System.out.println("\nRecord "+i);
				System.out.println("user_id: "+r.getInt("user_id"));
				System.out.println("name: "+r.getString("name"));
				// System.out.println("email: "+r.getString("email"));
				i++;
			}
		}catch(Exception e){
			System.out.println(e);
		}

	}

	public static void updateQueryCode(String tableName){
		try{
			Update u = new Update("users");
			u.set("name","Chella");
			u.set("email","chella@gmail.com");

			Criteria cu = u.getCriteria().where("user_id",Operator.EQU,(Integer)2);
			u.executeQuery();
			System.out.println("Updation done!");
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void deleteAllRecords(String tablename){
		try{
			Delete d = new Delete(tablename);
			Criteria c = d.getCriteria().where("user_id",Operator.EQU,10);
			d.executeQuery();
			System.out.println("Deletion done!");
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void insertRecords(String tablename){
		try{
			List<HashMap<String,Object>> list = new ArrayList<>();
			HashMap<String,Object> data = new HashMap<>();
			
			data.put("password","samp");
			data.put("id",1);
			data.put("email","Ajay@gmail.com");
			data.put("name","Ajay Rajan");
			list.add(data);

			HashMap<String,Object> data2 = new HashMap<>();
			
			data2.put("password","samp2");
			data2.put("id",2);
			data2.put("email","Ajith@gmail.com");
			data2.put("name","Ajith");
			list.add(data2);

			HashMap<String,Object> data3 = new HashMap<>();
			
			data3.put("password","samp3");
			data3.put("id",3);
			data3.put("email","Ashok@gmail.com");
			data3.put("name","Ashok K");
			list.add(data3);

			HashMap<String,Object> data4 = new HashMap<>();
			
			data4.put("password","samp4");
			data4.put("id",4);
			data4.put("email","Arun@gmail.com");
			data4.put("name","Arun kumar");
			list.add(data4);

			HashMap<String,Object> data5 = new HashMap<>();
			
			data5.put("password","samp5");
			data5.put("id",9);
			data5.put("email","chella@gmail.com");
			data5.put("name","Chellathurai");
			list.add(data5);

			Insert r = new Insert(tablename,list);
			r.addToTable();

			System.out.println("Insertion done!");
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	public static void performJoin(){
		try{
			JoinResult jr = (new Select("table1")).innerJoin("table2").
			on(new Field("table1","id").equals(new Field("table2","user_id"))).getResult();
			List<Row>  rows = jr.getRows();
			for(Row r:rows){
				System.out.println("Name:" + r.getString("table1.name"));
				System.out.println("Location: "+r.getString("table2.location"));
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
