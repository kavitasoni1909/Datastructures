import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class ArraysSol {

    private boolean isUnique(String str){
//        Determine if a string has all Unique Characters

//        Type of character in the string : only alphabets(26/52), mix of numbers, letters and special chars)
//        Assuming string is made of Ascii character (256)
//        Alternatively, sort the string Arrays.sort(str.toCharArray()) and
//        compare neighbouring chars linerally for identical ones. O(nlogn)

        int MAX_CHAR = 256;
        if(str.length() > MAX_CHAR){
            return false;
        }

        boolean[] checker = new boolean[MAX_CHAR]; //const space: O(1)
        Arrays.fill(checker, false);

        char[] str_arr = str.toCharArray();
        for(int i=0; i < str_arr.length ; i++){ //Time complexity: O(n), n is size of string
            int c = (int)str_arr[i];

            if(checker[c]){
                return false;
            }else{
                checker[c] = true;
            }
        }

        return true;
    }

    private boolean arePermutation1(String s1, String s2){
//        Check if two strings are permutation of each other. Assuming comparison is case sensitive and whitespace significant
//        Time Complexity : O(nlogn)

//        If whitespace is to be ignored then remove whitespace before length comparison.
//        s1 = s1.replaceAll("\\s",""); s2 = s2.replaceAll("\\s","")

        if(s1.length() != s2.length()){
            return false;
        }
//        If not case sensitive s1 = s1.toLowerCase(); s2 = s2.toLowerCase();
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();

        Arrays.sort(c1);
        Arrays.sort(c2);

        for(int i=0; i<c1.length; i++){
            if(c1[i] != c2[i]){
                return false;
            }
        }

        return true;
    }

    private boolean arePermutation2(String s1, String s2){
//      Time complexity: O(n), constant space
        int[] count = new int[256];

        for(int i=0; i<s1.length(); i++){
            char c = s1.charAt(i);
            count[c]++;

            c = s2.charAt(i);
            count[c]--;
        }

        for(int i=0; i<256; i++){
            if(count[i] != 0){
                return false;
            }
        }

        return true;
    }

    private boolean isPalindromePermutation1(String s){
//      Check if string is a permutation of palindrome.
//      Time Complexity: O(n)

        int[] count = new int[256];
        for(char c : s.toCharArray()){
            count[(int)c]++;
        }

        boolean foundOdd = false;
        for(int i : count){
            if(i % 2 != 0){
                if(foundOdd){
                    return false;
                }
                foundOdd = true;
            }
        }
        return true;
    }

    private boolean isPalindromePermutation2(String s){
//      Time Complexity: O(n)
        int[] count = new int[256];
        int countOdd = 0;

        for(char c : s.toCharArray()){
            count[(int)c]++;

            if(count[(int)c] % 2 == 1){
                countOdd++;
            }else{
                countOdd--;
            }
        }
        return countOdd <= 1;
    }

    private boolean areOneEditAway(String s1 , String s2){
//      Given two string s1 and s2, find if they are exactly one edit away. ( One insert/removal/replace)
//      Strings with same length should be one replacement away. Strings with length diff of one character can be
//      one insert/removal away
//      Time Complexity: O(l1 + l2), Space Complexity: O(1)

        int l1 = s1.length();
        int l2 = s2.length();

        if(Math.abs(l1 - l2) > 1){
            return false;
        }

        int i1 = 0; int i2 = 0;
        boolean foundDiff = false;

        while( i1 < l1 && i2 < l2){
            if(s1.charAt(i1) != s2.charAt(i2)){
                if(foundDiff){
                    return false;
                }
                foundDiff = true;

                if( l1 == l2){
                    i1++;
                    i2++;
                } else if(l1 > l2){
                    i1++;
                }else{
                    i2++;
                }
            }else{
                i1++;
                i2++;
            }
        }

        return true;
    }

    private String compressString(String s){
//      String compression with count of repeated character, aaabbccc : a3b2c3
//      Time & Space Complexity: O(N)
//      Advantage of choosing StringBuilder, if String was used runtime : O( N + K^2),
//      since String concatenation operates in O(K^2)

        if( s == null || s.trim().length() == 0){
            return "Input valid string with letters";
        }

        s = s.trim();
        StringBuilder compressed = new StringBuilder();

        if(s.length() == 1){
            compressed.append(s.charAt(0));
            compressed.append(1);

            return compressed.toString();
        }

        int i = 0; int j = 1;
        while(i < s.length() && j < s.length()){
            if(!Character.isLetter(s.charAt(i)) || ! Character.isLetter(s.charAt(j))){
                return "Input valid string with letters only";
            }

            if(s.charAt(i) != s.charAt(j)){
                compressed.append(s.charAt(i));
                compressed.append(j-i);
                i = j;
            }
            j++;
        }
        compressed.append(s.charAt(i));
        compressed.append(j-i);

        if(compressed.length() < s.length()) return compressed.toString();

        return s;
    }

    public void rotate(int[][] matrix) {
//      Given n x n 2D matrix representing an image, rotate the image by 90 degrees
//      Time Complexity: O(n^2), Space: O(1)

        if(matrix != null && matrix.length == 0 &&
                (matrix.length != matrix[0].length)){
            return;
        }

        int n = matrix.length;

        for(int layer=0; layer<n/2; layer++){
            int first = layer;
            int last = n-1-layer;
            int off = 0;

            for(int i=first; i<last; i++){

                //top
                int temp = matrix[first][i];

                //left -> top
                matrix[first][i] = matrix[last-off][first];

                //bottom -> left
                matrix[last-off][first] = matrix[last][last-off];

                //right -> bottom
                matrix[last][last-off] = matrix[i][last];

                //top -> right
                matrix[i][last] = temp;

                off++;
            }
        }
    }

    public void setZeroes(int[][] matrix) {
//      Given an m x n integer matrix, if an element is 0, set its entire row and column to 0's.
//      Time Complexity: O(m x n), Space: O(m+n)

        HashSet<Integer> rows = new HashSet();
        HashSet<Integer> cols = new HashSet();

        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){

                if(matrix[i][j] == 0){
                    rows.add(i);
                    cols.add(j);
                }
            }
        }

        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){

                if(rows.contains(i) || cols.contains(j)){
                    matrix[i][j] = 0;
                }
            }
        }
    }

    private boolean isRotation(String s1, String s2){
//      Check if strings are rotation of each other. s1 = "abcdef", s2 = "defabc"
//      Time Complexity: O(N)
        if(s1 == null || s2 == null || s1.length() != s2.length()){
            return false;
        }

        String concatenated = s1 + s1;

        return concatenated.indexOf(s2) != -1; //checking if s2 is substring of concatenated

    }
}
