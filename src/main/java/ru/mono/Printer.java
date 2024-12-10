package ru.mono;

public class Printer {
    @InvokeCount(6)
    public static void printUpperCase(String s){
        System.out.println(s.toUpperCase());
    }
    @InvokeCount(5)
    public static void printLoverCase(String s){
        System.out.println(s.toLowerCase());
    }
    @InvokeCount(4)
    protected static void printFlippedText(String s){
        for(int i = s.length()-1; i>=0; i--) System.out.print(s.charAt(i));
        System.out.println();
    }
    @InvokeCount(3)
    protected static void printHalfText(String s){
        System.out.println(s.substring(0, s.length()/2));
    }
    @InvokeCount(2)
    private static void printWithoutSpaces(String s){
        for(String word : s.split(" ")) System.out.print(word);
        System.out.println();
    }
    @InvokeCount(1)
    private static void printFlippedCase(String s){
        for(char ch : s.toCharArray()) System.out.print(
                Character.isLowerCase(ch) ? Character.toUpperCase(ch) : (Character.isUpperCase(ch) ? Character.toLowerCase(ch) : ch));
        System.out.println();
    }
}
