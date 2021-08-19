import java.util.*;

class MainCode{
	public static void main(String[] args){
		try{
			GetTableDetails.initialize(" ");

			// List<HashMap<String,Object>> list = new ArrayList<>();
			// HashMap<String,Object> data = new HashMap<>();
			
			// data.put("password","aja@123");
			// data.put("user_id",1);
			// data.put("email","ajay@gmail.com");
			// data.put("name","Ajay Rajan");
			// list.add(data);
			// Rows r = new Rows("users",list);
			// r.addToTable();

			// getAllRecords("users");

			// Select s = new Select("users");
			
			// Criteria c = s.getCriteria().where("user_id",Operator.EQU,(Integer)1).and("email",Operator.EQU,"ajay@gmail.com");

			// List<Row> results=s.executeQuery();

			// System.out.println("\nResults:");
			// int i=0;	
			// for(Row r:results){
			// 	System.out.println("\nRecord "+i);
			// 	System.out.println("user_id: "+r.getInt("user_id"));
			// 	System.out.println("name: "+r.getString("name"));
			// 	System.out.println("email: "+r.getString("email"));
			// 	i++;
			// }

			Update u = new Update("users");
			u.set("name","Chella");
			u.set("email","chella@gmail.com");

			Criteria cu = u.getCriteria().where("user_id",Operator.EQU,(Integer)2);
			u.executeQuery();
			getAllRecords("users");

		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static void getAllRecords(String tableName){
		try{
			Select s = new Select("users");
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
}
