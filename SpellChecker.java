/**
 *
 * @author harshal
 */
import java.io.*;
import java.util.*;

public class FinalSpellCorrector {

    static String word;
    static HashMap<String, Integer> hm = new HashMap<String, Integer>();
    static HashSet<String> arr = new HashSet<String>();
    static HashSet<String> arr1 = new HashSet<String>();
    static HashSet<String> arr2 = new HashSet<String>();

    //delete transpose insert replace

    static void delete() {
        String s = word;
        String s1;
        int l = s.length();
        arr.add(s.substring(1, l));
        arr.add(s.substring(0, l - 1));
        for (int i = 1; i < l - 1; i++) {
            s1 = s.substring(0, i) + s.substring(i + 1, l);
            arr.add(s1);
        }
    }

    static void transpose() {

        char temp;
        int l = word.length();
        for (int i = 0; i < l - 1; i++) {
            char[] ch = word.toCharArray();
            temp = ch[i];
            ch[i] = ch[i + 1];
            ch[i + 1] = temp;
            String s = new String(ch);
            arr.add(s);

        }
    }

    static void replace() {
        for (int i = 0; i < word.length(); i++) {
            char[] ch = word.toCharArray();
            for (int j = 0; j < 26; j++) {
                ch[i] = (char) (j + 97);
                arr.add(new String(ch));

            }
        }
    }

    static void insert() {
        int l = word.length();
        char[] ch = new char[l + 1];
        for (int i = 0; i < l; i++) {
            ch[i] = word.charAt(i);
        }

        char cc;

        for (int i = l; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                ch[i] = (char) (j + 97);
                arr.add(new String(ch));

            }
            if ((i - 1) >= 0) {
                cc = ch[i - 1];
                ch[i] = cc;
            }
        }

    }

    static void edit() {
        insert();
        replace();
        transpose();
        delete();
    }

    static void dohashmap() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("E:\\Big.txt"));
        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
        word = br1.readLine();
        String ss;
        while ((ss = br.readLine()) != null) {
            ss = ss.toLowerCase();
            String[] s = ss.split(" ");
            int n;

            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s[i].length(); j++) {
                    if (s[i].charAt(j) >= 97 && s[i].charAt(j) <= 122) {
                    } else {
                        s[i] = s[i].substring(0, j);
                        break;
                    }
                }
                if (hm.get(s[i]) != null) {
                    n = hm.get(s[i]);
                    n++;
                    hm.put(s[i], n);
                } else {
                    hm.put(s[i], 1);
                }

            }
        }
    }

    static void seperateknownelement(HashSet<String> h1, HashSet<String> h2) {
        for (String s2 : h1) {
            if (hm.get(s2) != null) {
                h2.add(s2);
            }
        }

    }

    static String gettingelement(HashSet<String> h1) {
        int z, max = 0;
        String s3 = "";
        for (String s2 : h1) {
            z = hm.get(s2);
            if (z > max) {
                max = z;
                s3 = s2;
            }
        }
        return s3;
    }

    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String pre = "";
        while (!pre.equals("stop")) {
            dohashmap();
            pre = word;
            edit();
            arr1.addAll(arr);
            arr.removeAll(arr1);
            for (String s2 : arr1) {
                word = s2;
                edit();
            }
            seperateknownelement(arr, arr2);

            arr.clear();
            seperateknownelement(arr1, arr);

            String s3 = "";

            s3 = gettingelement(arr);

            if (!s3.equals("")) {
                System.out.println("spell corrected word:" + s3);
            } else {
                s3 = gettingelement(arr2);
                if (!s3.equals("")) {
                    System.out.println("spell corrected word:" + s3);
                } else {
                    System.out.println("NO MATCH");
                }
            }
            arr.clear();
            arr1.clear();
            arr2.clear();
        }
    }
}
