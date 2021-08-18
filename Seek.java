import java.io.*;

public class Seek {
   public static void main(String[] args)throws Exception {
      RandomAccessFile raf = null;
      RandomAccessFile raf2 = null;
      RandomAccessFile raf3 = null;
      raf = new RandomAccessFile("C:/Users/AjaySandhya/Desktop/file task/File DB3/data/users.txt", "rw");
      raf2 = new RandomAccessFile("C:/Users/AjaySandhya/Desktop/file task/File DB3/data/sample1.txt", "rw");
      raf3 = new RandomAccessFile("C:/Users/AjaySandhya/Desktop/file task/File DB3/data/sample1.txt", "rw");


      byte[] buffer = new byte[100]; /// 10 chars + 1 new line
      // raf.readFully(buffer);
      // System.out.print(new String(buffer));
      String d="8";
      raf.seek(0);
      // raf.write(d.getBytes());
      // raf.write(d.getBytes());
      // raf.write(d.getBytes());
      // raf.write(d.getBytes());
      // raf.write(d.getBytes());
      // raf.seek(0);
      raf.readFully(buffer);
      System.out.println(new String(buffer));
      raf.close();
     
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
