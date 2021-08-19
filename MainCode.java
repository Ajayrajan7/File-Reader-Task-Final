import java.util.*;

public class MainCode{
	public static void main(String[] args){
		try{
			GetTableDetails.initialize(" ");

			// insertRecords("users");
			// deleteAllRecords("users");
			getAllRecords("users");
			// getAllRecordsWithQuery("users");
			
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
			System.out.println("Updation done!");
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void deleteAllRecords(String tablename){
		try{
			Delete d = new Delete(tablename);
			Criteria c = d.getCriteria().where("user_id",Operator.EQU,6);
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
			data.put("user_id",5);
			data.put("email","sample1@gmail.com");
			data.put("name","Sample 1");
			list.add(data);

			HashMap<String,Object> data2 = new HashMap<>();
			
			data2.put("password","samp2");
			data2.put("user_id",6);
			data2.put("email","samp2@gmail.com");
			data2.put("name","Sample 2");
			list.add(data2);

			HashMap<String,Object> data3 = new HashMap<>();
			
			data3.put("password","samp3");
			data3.put("user_id",7);
			data3.put("email","samp3@gmail.com");
			data3.put("name","Sample 3");
			list.add(data3);

			HashMap<String,Object> data4 = new HashMap<>();
			
			data4.put("password","samp4");
			data4.put("user_id",8);
			data4.put("email","samp4@gmail.com");
			data4.put("name","Sample 4");
			list.add(data4);

			HashMap<String,Object> data5 = new HashMap<>();
			
			data5.put("password","samp5");
			data5.put("user_id",9);
			data5.put("email","samp5@gmail.com");
			data5.put("name","Sample 5");
			list.add(data5);

			Insert r = new Insert("users",list);
			r.addToTable();

			System.out.println("Insertion done!");
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
}
