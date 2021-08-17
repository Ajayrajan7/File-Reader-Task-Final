import java.io.*;

public class Seek {
   public static void main(String[] args)throws Exception {
      RandomAccessFile raf = null;
         raf = new RandomAccessFile("/home/local/ZOHOCORP/chella-pt3956/File-Reader-Task-Final/data/sample1.txt", "rw");
         byte[] buffer = new byte[11]; /// 10 chars + 1 new line
         // raf.readFully(buffer);
         // System.out.print(new String(buffer));
         // raf.seek(100*11);
         // raf.readFully(buffer);
         // System.out.print(new String(buffer));
      int i=0;
      while(true){
      System.out.println(i);
      raf.seek(11*i);
      raf.readFully(buffer);
      System.out.print(new String(buffer));
      i++;
   }
  }
}
