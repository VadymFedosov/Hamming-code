package com.hammmingCode;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HammingCode {
    public HammingCode() {}

    private int infBitsCount(int checkN, int[] b) {
        int res = 0;
        for (int i = checkN -1; i < b.length; i += (checkN * 2)) {
            for (int j = i; j < i + checkN && j < b.length; j++) {
                if (!(j == 0 || ((j + 1 & (j)) == 0)) && b[j] == 1) {
                    res++;
                }
            }
        }
        return res;
    }
    public String encode(String bitText) {
        int textLength = bitText.length();
        int checkBitsAmount = 0;
        char []bitArr = bitText.toCharArray();

        if(textLength != 0) {
            int temp = textLength;
            while( temp >= 0) {
                if(temp == 0 || ((temp + 1 & (temp)) == 0)) {
                    checkBitsAmount++;
                }
                temp--;
            }
        }
        if( textLength % 2 == 0) {
            checkBitsAmount -= 1;
        }

        int []bits = new int[textLength + checkBitsAmount + 1];
        int checkNumber = 1;

        int j = 0;
        for (int i = 0; i < textLength + checkBitsAmount + 1; i++) {
            if((i == 0 || ((i + 1 & (i)) == 0))) {
                bits[i] = 2;
            } else {
                if(bitArr[j] == '0') {
                    bits[i] = 0;
                } else if(bitArr[j] == '1'){
                    bits[i] = 1;
                } else {
                    throw  new IllegalArgumentException();
                }
                j++;
            }
        }

        j = 0;
        int []encodeBits = bits.clone();
        int iBC;
        for (int i = 0; i < textLength + checkBitsAmount + 1; i += Math.pow(2, j), j++) {
            iBC = infBitsCount(checkNumber, bits);
            if (iBC%2 == 0) {
                encodeBits[i] = 0;
            } else {
                encodeBits[i] = 1;
            }
            checkNumber *=2;
        }

        StringBuilder result = new StringBuilder();
        for (int ch: encodeBits){
            result.append(ch);
        }

        return  result.toString();
    }

    public String decode(String bitText) {
        int textLength = bitText.length();
        int []bits = new int[textLength];

        int i = 0;
        for (char ch:bitText.toCharArray()) {
            if (ch == '0') {
                bits[i] = 0;
            } else if(ch == '1') {
                bits[i] = 1;
            } else {
                throw new IllegalArgumentException();
            }
            i++;
        }
        int checkNumber = 1;
        int errorPosition = 0;

        StringBuilder decodeBits = new StringBuilder();
        for (i = 0; i < textLength; i++) {
            if((i == 0 || ((i + 1 & (i)) == 0))) {
                if (bits[i] != (infBitsCount(checkNumber, bits)%2)) {
                    errorPosition += i+1;
                }
                checkNumber *= 2;
            } else {
                if(bits[i] == 0) {
                    decodeBits.append('0');
                } else if(bits[i] == 1) {
                    decodeBits.append('1');
                }
            }
        }

        StringBuilder correctMessage;
        String resultCode;
        StringBuilder decB = new StringBuilder();
        if (errorPosition != 0) {
            System.out.println("Error position: " + errorPosition);
            correctMessage = new StringBuilder();

            for (i = 0; i < textLength; i++) {
                if (!(i == 0 || ((i + 1 & (i)) == 0))) {
                    if (i == errorPosition-1) {
                        if (bits[i] == 0) {
                            decB.append('1');
                        } else {
                            decB.append('0');
                        }
                    } else {
                        decB.append(bits[i]);
                    }
                }

                if (i == errorPosition-1) {
                    if (bits[i] == 0) {
                        correctMessage.append('1');
                    } else {
                        correctMessage.append('0');
                    }
                } else {
                    correctMessage.append(bits[i]);
                }
            }

            System.out.println("Corrected message: " + correctMessage.toString());
            resultCode = decB.toString();
        } else {
            System.out.println("Errors not detected.");
            resultCode = decodeBits.toString();;
        }

        return  resultCode;
    }
}
