import java.util.*;
import java.io.*;
public class GetTableDetails {
    private static String[] FIELDS = new String[]{"STRING","INTEGER","LONG","DOUBLE","FLOAT","BOOLEAN"};
    private static final Set Fields = new HashSet<String>(Arrays.asList(FIELDS));
    public static HashMap<String,LinkedHashMap<String,Types>> tablesVsFieldDetails = new HashMap<>();
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
        FileReader f = new FileReader(path);
        p.load(f);
        return p;
    }
    private static void createFileIfNotExists(String key){
        File f = new File(dataPath+File.separator+key+".txt");
        if(!f.exists()){
            try{
                f.createNewFile();
                System.out.println("New file is created");
            }catch(Exception e){
                System.out.println(e);
            }
        }else{
            // System.out.println("File exists already");
        }
    }
    private static void createPropsFileForDeletionTracking(String key){
        File f = new File(confPath+File.separator+key+".props");
        if(!f.exists()){
            try{
                f.createNewFile();
                Properties p = new Properties();
                p.setProperty(key,"0");
                p.store(new FileOutputStream(confPath+File.separator+key+".props"),"Props file for tracking delete rows");
                System.out.println("New file is for deletion tracking");
            }catch(Exception e){
                System.out.println(e);
            }
        }else{
            // System.out.println("File exists already");
        }
    }

    public static LinkedHashMap<String,Types> getFieldVsTypes(String tableName){
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

    private static LinkedHashMap<String,Types> getFieldVsTypes_(String tableBuffer)throws IllegalArgumentException,ClassNotFoundException{
        LinkedHashMap<String,Types> fieldVsTypes = new LinkedHashMap<String,Types>();
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

    private static Integer getSizeForType(String str) throws ClassNotFoundException{
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
    private static Types getClassName(String str) throws ClassNotFoundException{
        // String Capitalized = str.toLowerCase().substring(0, 1).toUpperCase() + str.substring(1);
        String dataTypeName = str.toUpperCase();
        if(dataTypeName.equals("INTEGER")){
            return Types.INTEGER;
        }else if(dataTypeName.equals("STRING")){
            return Types.STRING;
        }else if(dataTypeName.equals("FLOAT")){
            return Types.FLOAT;
        }else if(dataTypeName.equals("LONG")){
            return Types.LONG;
        }else if(dataTypeName.equals("DOUBLE")){
            return Types.DOUBLE;
        }else  if(dataTypeName.equals("BOOLEAN")){
            return Types.BOOLEAN;
        }
        return null;
    }
}

enum Types {
    INTEGER,
    STRING,
    FLOAT,
    LONG,
    DOUBLE,
    BOOLEAN
}