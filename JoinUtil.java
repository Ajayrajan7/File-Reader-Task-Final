import java.io.*;
import java.util.*;
public class JoinUtil {
    public static String WriteJoinsToTable(JoinResponse response){
        response.intiateIterator();
        while(response.hasNext()){
            Row[] rows = response.next();
            /* Write the rows in the file in order*/
        }
    }

    public static String calculatedTempFileName(){
        return Long.toHexString(Double.doubleToLongBits(Math.random()))+".txt";
    }

    public static FileOutputStream getOPStream(String filename){
        String tempDblocation = "" ; /* load the temp db location here*/
        return new FileOutputStream(tempDblocation+File.separator+filename,false);
    }
}