import java.util.*;
public class NpeTest {
    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("int", Integer.valueOf(10000000));
        map.put("double", Double.valueOf(10000000));



        System.out.println((int)map.get("int"));
        System.out.println((String)map.get("int2"));
        System.out.println((int)map.get("int2"));

    }
}
