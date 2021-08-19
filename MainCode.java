import java.util.*;

public class MainCode{
	public static void main(String[] args){
		try{
			GetTableDetails.initialize(" ");

			// insertRecords("users");
			
			getAllRecords("users");
			// getAllRecordsWithQuery("users");
			// deleteAllRecords("users");
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void getAllRecords(String tableName){
		try{
			Select s = new Select(tableName);
			// s.columns("user_id","name");
			List<Row> results=s.executeQuery();
			
			int i=0;
			System.out.println("\nResults:");
			for(Row r:results){
				System.out.println("\nRecord "+i);
				System.out.println("user_id: "+r.getInt("user_id"));
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
			
			int i=0;
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
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void deleteAllRecords(String tablename){
		try{
			Delete d = new Delete(tablename);

		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void insertRecords(String tablename){
		try{
			List<HashMap<String,Object>> list = new ArrayList<>();
			HashMap<String,Object> data = new HashMap<>();
			
			data.put("password","temp2");
			data.put("user_id",3);
			data.put("email","temp2@gmail.com");
			data.put("name","Testing2");
			list.add(data);

			// HashMap<String,Object> data2 = new HashMap<>();
			
			// data2.put("password","arun");
			// data2.put("user_id",4);
			// data2.put("email","arun@gmail.com");
			// data2.put("name","Arun Kumar");
			// list.add(data2);


			Insert r = new Insert("users",list);
			r.addToTable();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
}
