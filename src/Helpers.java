public class Helpers {


    public static int findNthAppearance(String str, char targetChar, int index) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == targetChar) {
                count++;
                if (count == index) {
                    return i;
                }
            }
        }
        return -1;  // If the target character does not appear index times, return -1
    }
}
