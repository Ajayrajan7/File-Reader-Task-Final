import java.util.*;

public class MainCode{
	public static void main(String[] args){
		try{
			GetTableDetails.initialize(" ");
			// insertRecords("table5");
			// deleteAllRecords("table1");
			// getAllRecords("table4");
			// performJoin();
			// getAllRecordsWithQuery("table1");
			// createTable("table5");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void getAllRecords(String tableName){
		try{
			Select s = new Select(tableName);
			// s.columns("user_id","name");
			List<Row> results=s.executeQuery();
			
			int i=1;
			System.out.println("\nResults:\n");
			for(Row r:results){
				System.out.println("\nRecord "+i);
				System.out.println("id: "+r.getInt("user_id"));
				System.out.println("location: "+r.getString("location"));
				// System.out.println("email: "+r.getString("email"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();;
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
			e.printStackTrace();;
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
			e.printStackTrace();;
		}
	}

	public static void deleteAllRecords(String tablename){
		try{
			Delete d = new Delete(tablename);
			Criteria c = d.getCriteria().where("user_id",Operator.EQU,10);
			d.executeQuery();
			System.out.println("Deletion done!");
		}catch(Exception e){
			e.printStackTrace();;
		}
	}

	public static void insertRecords(String tablename){
		try{
			List<HashMap<String,Object>> list = new ArrayList<>();
			HashMap<String,Object> data = new HashMap<>();
			data.put("name","Ajay");
			data.put("age",2);
			data.put("id",1);
			list.add(data);

			// HashMap<String,Object> data2 = new HashMap<>();
			
			// // data2.put("password","samp2");
			// data2.put("user_id",2);
			// // data2.put("email","Ajith@gmail.com");
			// data2.put("location","Chennai");
			// list.add(data2);

			// HashMap<String,Object> data3 = new HashMap<>();
			
			// // data3.put("password","samp3");
			// data3.put("user_id",3);
			// // data3.put("email","Ashok@gmail.com");
			// data3.put("location","Coimbatore");
			// list.add(data3);

			// HashMap<String,Object> data4 = new HashMap<>();
			
			// // data4.put("password","samp4");
			// data4.put("user_id",4);
			// // data4.put("email","Arun@gmail.com");
			// data4.put("location","Trichy");
			// list.add(data4);

			// HashMap<String,Object> data5 = new HashMap<>();
			
			// // data5.put("password","samp5");
			// data5.put("user_id",6);
			// // data5.put("email","chella@gmail.com");
			// data5.put("location","NYC");
			// list.add(data5);

			// HashMap<String,Object> data6 = new HashMap<>();
			
			// // data5.put("password","samp5");
			// data6.put("user_id",7);
			// // data5.put("email","chella@gmail.com");
			// data6.put("location","KYC");
			// list.add(data6);

			// HashMap<String,Object> data7 = new HashMap<>();
			
			// // data5.put("password","samp5");
			// data7.put("user_id",8);
			// // data5.put("email","chella@gmail.com");
			// data7.put("location","DUB");
			// list.add(data7);

			Insert r = new Insert(tablename,list);
			r.addToTable();

			System.out.println("Insertion done!");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void performJoin(){
		try{
			JoinResult jr = new Select("table1").rightJoin("table2").
			on(new Field("table1","id").eq(new Field("table2","user_id"))).getResult();
			
			List<Row>  rows = jr.getRows();
			System.out.println("\nJoin Results::");
			for(Row r:rows){
				System.out.println("Name:" + r.getInt("table1.id"));
				System.out.println("Location: "+r.getInt("table2.id"));
			}

			// JoinResult jr2 = jr.innerJoin("table3").on(new Field("table2", "location").eq(new Field("table3", "city"))).getResult();
			// // List<Row> row2 = jr2.getRows();
			// // for(Row r:row2){
			// // 	System.out.println(r.getRowDetails());
			// // }

			// JoinResult jr3 = jr2.innerJoin("table4").on(new Field("table1","id").
			// 				eq(new Field("table4","id"))).and(new Field("table3","city").
			// 				eq(new Field("table4","city"))).getResult();
			
			// List<Row> row3 = jr3.getRows();
			// for(Row r:row3){
			// 	System.out.println(r.getRowDetails());
			// }

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void createTable(String tablename){
		try{
			CreateTable ct = new CreateTable(tablename);
			ct.addColumn("name", "string");
			ct.addColumn("id", "integer");
			ct.addColumn("age", "integer");
			if(ct.create()){
				System.out.println("Table created successfully");
			}else{
				System.out.println("Table not created");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
