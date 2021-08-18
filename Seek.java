import java.io.*;

public class Seek {
   public static void main(String[] args)throws Exception {
      RandomAccessFile raf = null;
      RandomAccessFile raf2 = null;
      RandomAccessFile raf3 = null;
      raf = new RandomAccessFile("C:/Users/AjaySandhya/Desktop/file task/File DB3/data/sample1.txt", "rw");
      raf2 = new RandomAccessFile("C:/Users/AjaySandhya/Desktop/file task/File DB3/data/sample1.txt", "rw");
      raf3 = new RandomAccessFile("C:/Users/AjaySandhya/Desktop/file task/File DB3/data/sample1.txt", "rw");


      byte[] buffer = new byte[11]; /// 10 chars + 1 new line
      // raf.readFully(buffer);
      // System.out.print(new String(buffer));
      
      raf.seek(0);
      raf.readFully(buffer);
      System.out.println(buffer[0]);
      
      raf2.seek(12);
      raf2.readFully(buffer);
      System.out.println(new String(buffer));

      raf3.seek(24);
      raf3.readFully(buffer);
      System.out.println(new String(buffer));
      
      raf.seek(60);
      raf.readFully(buffer);
      System.out.println(new String(buffer));
      
      raf2.seek(12);
      raf2.readFully(buffer);
      System.out.println(new String(buffer));

      raf3.seek(0);
      raf3.readFully(buffer);
      System.out.println(new String(buffer));

      // int i=0;
      // while(true){
      //    System.out.println(i);
      //    raf.seek(11*i);
      //    raf.readFully(buffer);
      //    System.out.print(new String(buffer));
      //    i++;
      // }
  }
}
