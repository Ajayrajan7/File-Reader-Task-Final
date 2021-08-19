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
    System.out.println("Deleting");
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
                System.out.println(line);
                outputFileWriter.write(line);
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
        //rename old file
        File file1 = new File(origFile);
        File file2 = new File("temp-"+origFile);
        file1.renameTo(file2);
       
        //rename current file
        File file3 = new File(filenameTemp);
        File file4 = new File(origFile);
        file3.renameTo(file4);

        file2.delete();
        try{
            inputFileReader.close();
            outputFileWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    return 0;
   }
   
}
 