import java.util.*;
import java.io.*;
public class GetTableDetails {
    private static String[] FIELDS = new String[]{"STRING","INTEGER","LONG","DOUBLE","FLOAT","BOOLEAN"};
    private static final Set Fields = new HashSet<String>(Arrays.asList(FIELDS));
    public static HashMap<String,LinkedHashMap<String,DataTypes>> tablesVsFieldDetails = new HashMap<>();
    public static HashMap<String,LinkedHashMap<String,Integer>> tableVsSize = new HashMap<>();
    static String dataPath="";
    static String confPath="";
    
    public static void initialize(String path) throws IOException{
        
        Properties props = parseProps(System.getProperty("user.dir")+File.separator+"config.props");
        dataPath = props.getProperty("DataLocation");
        confPath = props.getProperty("ConfigLocation");
        
        Properties props2 = parseProps(confPath+File.separator+"Tables.props");
        createTablesVsFields(props2);
    }

    public static Properties parseProps(String path) throws IOException{
        Properties p = new Properties();
        FileReader f = null;
        
        try{
            f = new FileReader(path);
            p.load(f);
        }catch(IOException e){
            throw new IOException(e);
        }finally{
            try{
                f.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return p;
    }
    public static void createFileIfNotExists(String key){
        File f = new File(dataPath+File.separator+key+".txt");
        if(!f.exists()){
            try{
                f.createNewFile();
                System.out.println("New file is created");
            }catch(Exception e){
                e.printStackTrace();;
            }
        }else{
            // System.out.println("File exists already");
        }
    }
    public static void createPropsFileForDeletionTracking(String key){
        File f = new File(confPath+File.separator+key+".props");
        FileOutputStream fileOutputStream = null;
        if(!f.exists()){
            try{
                f.createNewFile();
                fileOutputStream = new FileOutputStream(confPath+File.separator+key+".props");
                Properties p = new Properties();
                p.setProperty(key,"0");
                p.store(fileOutputStream,"Props file for tracking delete rows");
                System.out.println("New file is for deletion tracking");
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try{
                    fileOutputStream.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }else{
            // System.out.println("File exists already");
        }
    }

    public static LinkedHashMap<String,DataTypes> getFieldVsTypes(String tableName){
        return tablesVsFieldDetails.get(tableName);
    }

    private static void createTablesVsFields(Properties properties){

        for(String key : properties.stringPropertyNames()) {
            createFileIfNotExists(key);
            createPropsFileForDeletionTracking(key);
            String value = properties.getProperty(key);
            try {
                tablesVsFieldDetails.put(key,getFieldVsTypes_(value));
                tableVsSize.put(key,getFieldVsSize(value));
            }
            catch(ClassNotFoundException e){
                throw new RuntimeException(e);
            }
          }
    }

    private static LinkedHashMap<String,DataTypes> getFieldVsTypes_(String tableBuffer)throws IllegalArgumentException,ClassNotFoundException{
        LinkedHashMap<String,DataTypes> fieldVsTypes = new LinkedHashMap<String,DataTypes>();
        String[] values = null ;
        for(String entry : tableBuffer.split(",")){
             values = entry.split(":");
             if(values!=null){
                if(values.length != 2 &&  !Fields.contains(values[1].toUpperCase())){
                    throw new IllegalArgumentException("Field "+values[0]+" has Invalid type "+values[1]);
                }
                else{
                    fieldVsTypes.put(values[0],getClassName(values[1])); 
                }
            }  
        }
        return fieldVsTypes;
    }
    private static LinkedHashMap<String,Integer> getFieldVsSize(String tableBuffer)throws IllegalArgumentException,ClassNotFoundException{
        LinkedHashMap<String,Integer> fieldVsSize = new LinkedHashMap<String,Integer>();
        String[] values = null ;
        int total_size=0;
        for(String entry : tableBuffer.split(",")){
             values = entry.split(":");
             if(values!=null){
                if(values.length != 2 &&  !Fields.contains(values[1].toUpperCase())){
                    throw new IllegalArgumentException("Field "+values[0]+" has Invalid type "+values[1]);
                }
                else{
                    int x=getSizeForType(values[1]);
                    fieldVsSize.put(values[0],x);
                    total_size+=x; 
                }
            }  
        }
        fieldVsSize.put("Total_Row_Size",total_size);
        return fieldVsSize;
    }

    public static Integer getSizeForType(String str){
        String Capitalized = str.toLowerCase().substring(0, 1).toUpperCase() + str.substring(1);
        if(Capitalized.equals("String")){
            // return 1000;
            return 30;
        }else if(Capitalized.equals("Double")){
            // return 309;
            return 20;
        }else if(Capitalized.equals("Float")){
            // return 39;
            return 15;
        }else if(Capitalized.equals("Integer")){
            // return 10;
            return 10;
        }else if(Capitalized.equals("Long")){
            // return 19;
            return 16;
        }
        return null;
    }
    public static DataTypes getClassName(String str){
        // String Capitalized = str.toLowerCase().substring(0, 1).toUpperCase() + str.substring(1);
        String dataTypeName = str.toUpperCase();
        if(dataTypeName.equals("INTEGER")){
            return DataTypes.INTEGER;
        }else if(dataTypeName.equals("STRING")){
            return DataTypes.STRING;
        }else if(dataTypeName.equals("FLOAT")){
            return DataTypes.FLOAT;
        }else if(dataTypeName.equals("LONG")){
            return DataTypes.LONG;
        }else if(dataTypeName.equals("DOUBLE")){
            return DataTypes.DOUBLE;
        }else  if(dataTypeName.equals("BOOLEAN")){
            return DataTypes.BOOLEAN;
        }
        return null;
    }
}

enum DataTypes {
    INTEGER,
    STRING,
    FLOAT,
    LONG,
    DOUBLE,
    BOOLEAN
}