import java.io.*;
public class Seek {
    public static void main(String[] args) {
        RandomAccessFile raf = null;
        try {
           raf = new RandomAccessFile("C:\\Users\\AjaySandhya\\Desktop\\file task\\File DB3\\data\\sample1.txt", "rw");
           raf.seek(0);
           byte[] buffer = new byte[11]; /// 10 chars + 1 new line
           raf.read(buffer);
           System.out.println(new String(buffer));
           raf.seek(5*11);
           raf.read(buffer);
           System.out.println(new String(buffer));
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        finally{
            try{
               raf.close(); 
            }
            catch(IOException e ){
                e.printStackTrace();
            }
        }
        
     }
}
