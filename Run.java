package com.hammmingCode;

public class Run {
    public static void main(String[] args) {
        String bitText = args[1];
        HammingCode coder = new HammingCode();
        if (args[0].equals("-c")) {
            String encodedText = coder.encode(bitText);
            System.out.println("Encoded text: " + encodedText);
        } else if (args[0].equals("-d")) {
           String decodeText = coder.decode(bitText);
           System.out.println("Decoded text: " + decodeText);
        }
    }
}
