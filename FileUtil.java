import java.io.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.atomic.AtomicInteger;
public class FileUtil {
    private static AtomicInteger noOfDescriptors= new AtomicInteger();
    public static RandomAccessFile getRandomAccessInstance(String tableName) throws FileNotFoundException{
        noOfDescriptors.incrementAndGet();
       return new RandomAccessFile(GetTableDetails.dataPath+File.separator+tableName+".txt", "rw");
    }

    public static void releaseFile(){
        noOfDescriptors.decrementAndGet();
    }


    public synchronized boolean checkZeroReferences(){
        if(noOfDescriptors.get()==0) return true;
        return false;
    }
}

class FileDeleteUtil {
   public static synchronized int deleteLines(String tableName) throws IOException{
    System.out.println("Deleting Process:");
    FileReader inputFileReader = null;
    BufferedReader brIn = null;
    FileWriter  outputFileWriter = null;
    BufferedWriter bwOut = null;
    String filenameTemp = tableName+Long.toHexString(Double.doubleToLongBits(Math.random()));
    String origFile = GetTableDetails.dataPath+File.separator+tableName+".txt";
    String line = null;
    try{
        inputFileReader = new FileReader(origFile);
        brIn = new BufferedReader(inputFileReader);
        outputFileWriter = new FileWriter(GetTableDetails.dataPath+File.separator+filenameTemp+".txt");
        bwOut = new BufferedWriter(outputFileWriter);
        while ((line = brIn.readLine()) != null){
            if(line.charAt(0)!='0'){
                System.out.println("not to be deleted: "+line);
                outputFileWriter.write(line);
                outputFileWriter.write("\n");
            }
                
        }
    }
    catch(IOException e){
        throw new IOException("Cannot Detete Lines in the file");
    }

    catch(NullPointerException e){
        System.out.println("Null pointer Exception "+line);
    }
    finally{
        inputFileReader.close();
        outputFileWriter.close();
        
        //rename old file
        File file1 = new File(origFile);
        System.out.println(file1.delete());

        File tempFile = new File(GetTableDetails.dataPath+File.separator+filenameTemp+".txt");
        File newName = new File(GetTableDetails.dataPath+File.separator+tableName+".txt");
        tempFile.renameTo(newName);
        
    }
    return 0;
   }
   
}
 