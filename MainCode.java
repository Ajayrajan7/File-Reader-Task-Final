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


			// data.clear();
			// list.remove(0);
			
			// data.put("user_id",1);
			// data.put("fileName","today_log.log");
			// list.add(data);
			// r=new Rows("log_details",list);
			// r.addToTable();

			Select_ select = new Select_();
			LinkedHashMap<String,Object> data=select.getData("users","user_id","name","email");
			System.out.println(data);
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
