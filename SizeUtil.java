public class SizeUtil {
    public static void main(String[] args) {
  
        // get max value
        double max = Double.MAX_VALUE;
        String str = String.format("%.10f", max);
        // System.out.println(str);
        System.out.println(str.split("\\.")[0].length());

        System.out.println("------------------------------");
        double fmax = Float.MAX_VALUE;
        str = String.format("%.10f", fmax);
        // System.out.println(str);
        System.out.println(str.split("\\.")[0].length());

        System.out.println("------------------------------");
        int imax = Integer.MAX_VALUE;
        System.out.println(String.valueOf(imax).length());


        System.out.println("------------------------------");
        long lmax = Long.MAX_VALUE;
        System.out.println(String.valueOf(lmax).length());

       
     }
}
