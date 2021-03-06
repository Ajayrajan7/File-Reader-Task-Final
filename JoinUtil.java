import java.io.*;
import java.util.*;
public class JoinUtil {
    private String path;
    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter out;

    // public static String WriteJoinsToTable(JoinResponse response){
    //     response.intiateIterator();
    //     while(response.hasNext()){
    //         Row[] rows = response.next();
    //         /* Write the rows in the file in order*/
    //     }
    // }

    public static String calculatedTempFileName(){
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }

    // public static FileOutputStream getOPStream(String filename){
    //     String tempDblocation = "" ; /* load the temp db location here*/
    //     return new FileOutputStream(tempDblocation+File.separator+filename,false);
    // }

    public void initializeFile(String tempFileName){
        path = GetTableDetails.dataPath+File.separator+tempFileName+".txt";
        try{
            fw = new FileWriter(path, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public void addToTable(String tablename1,String tablename2,JOINTYPES type,boolean isMatched,LinkedHashMap<String,Object> lhsData,LinkedHashMap<String,Object> rhsData){
        try{
            if(isMatched){
                out.print(1);
                writeData(lhsData,tablename1,true);
                writeData(rhsData,tablename2,true);
                out.print("\n");
            }else{
                switch(type){
                    case LEFTJOIN:
                        out.print(1);
                        writeData(lhsData,tablename1,true);
                        writeData(rhsData,tablename2,false);
                        out.print("\n");
                        break;
                    case RIGHTJOIN:
                        out.print(1);
                        writeData(lhsData,tablename1,true);
                        writeData(rhsData,tablename2,false);
                        out.print("\n");
                        break;
                    case INNERJOIN:
                        return;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public void writeData(LinkedHashMap<String,Object> map,String tablename,boolean canWrite){
        try{
            
            for(Map.Entry<String,Object> entry:map.entrySet()){
                if(canWrite){
                    out.print(Insert.padData(tablename,entry.getKey(),entry.getValue()));
                }else{
                    out.print(Insert.padData(tablename,entry.getKey(),"null"));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    } 
    public void flush(){
        FileUtil.safeClose(out);
        FileUtil.safeClose(bw);
        FileUtil.safeClose(fw);
    }
    
}