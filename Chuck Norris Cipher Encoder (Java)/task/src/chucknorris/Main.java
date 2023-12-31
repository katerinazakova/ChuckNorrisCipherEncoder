package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String choice = scanner.nextLine();
            if ("encode".equals(choice) || "decode".equals(choice) || "exit".equals(choice)) {
                switch (choice) {
                    case "encode":
                        System.out.println("Input string:");
                        String inputText = scanner.nextLine();
                        System.out.println("Encoded string:");
                        String encodedMessage = encodeInputMessage(inputText);
                        System.out.println(encodedMessage);
                        break;
                    case "decode":
                        System.out.println("Input encoded string:");
                        String inputMessage = scanner.nextLine();
                        boolean validCode = true;
                        if (controlCharacters(inputMessage) == validCode
                                && controlBlockIsOdd(inputMessage) == validCode
                                && controlFirstBlock(inputMessage) == validCode
                                && controlLengthBinaryCode(inputMessage) == validCode
                                && controlBlocks(inputMessage) == validCode) {
                            System.out.println("Decoded string:");
                            System.out.println(decodeChuckNorris(inputMessage));
                        } else {
                            System.out.println("Encoded string is not valid.");
                        }
                        break;
                    case "exit":
                        System.out.println("Bye!");
                        return;
                }
            } else {
                System.out.println("There is no '" + choice + "' operation");
            }
        }
    }

    public static String encodeInputMessage(String text) {
        StringBuilder binaryMessage = new StringBuilder();
        for (char c : text.toCharArray()) {
            String binaryCode = String.format("%7s", Integer.toBinaryString(c)).replace(' ', '0');
            binaryMessage.append(binaryCode);
        }

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < binaryMessage.length()) {
            char currentElementOfBinary = binaryMessage.charAt(i);
            int count = 1;

            while (i + 1 < binaryMessage.length() && binaryMessage.charAt(i + 1) == currentElementOfBinary) {
                count++;
                i++;
            }
            result.append((currentElementOfBinary == '1' ? "0" : "00") + " " + "0".repeat(count) + " ");
            i++;
        }
        return result.toString().trim();
    }

    public static String decodeChuckNorris(String encodedMessage) {
        StringBuilder decoded = new StringBuilder();

        String[] blocks = encodedMessage.split(" ");

        for (int i = 0; i < blocks.length; i += 2) {
            int count = blocks[i].equals("00") ? 0 : 1;
            for (int j = 0; j < blocks[i + 1].length(); j++) {
                decoded.append(count == 0 ? '0' : '1');
            }
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < decoded.length(); i += 7) {
            String binaryChar = decoded.substring(i, Math.min(i + 7, decoded.length()));
            int decimalChar = Integer.parseInt(binaryChar, 2);
            result.append((char) decimalChar);
        }

        return result.toString();
    }

    public static int determineLengthOfDecodeBinaryString(String inputMessage) {
        StringBuilder decoded = new StringBuilder();
        String[] blocks = inputMessage.split(" ");

        for (int i = 0; i < blocks.length; i += 2) {
            int count = blocks[i].equals("00") ? 0 : 1;
            for (int j = 0; j < blocks[i + 1].length(); j++) {
                decoded.append(count == 0 ? '0' : '1');
            }
        }
        return decoded.length();
    }

    public static boolean controlCharacters(String inputMessage) {
        if (inputMessage.matches("^[0\\s]+$")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean controlFirstBlock(String inputMessage) {
        if (inputMessage.startsWith("0") && inputMessage.charAt(1) == '0' &&
                inputMessage.charAt(2) == ' ' || inputMessage.charAt(1) == ' '
                && inputMessage.charAt(2) == '0') {
            return true;
        } else {
            return false;
        }
    }

    public static boolean controlBlockIsOdd(String inputMessage) {
        String[] blocks = inputMessage.split(" ");
        int counter = 0;
        for (int i = 0; i < blocks.length; i++) {
            counter++;
        }

        if (counter % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean controlBlocks(String inputMessage) {
        String[] blocks = inputMessage.split(" ");
        boolean validBlock = true;
        for (int i = 0; i < blocks.length; i += 2) {
            if ("0".equals(blocks[i]) || "00".equals(blocks[i])) {
                validBlock = true;
            } else {
                validBlock = false;
            }
        }
        return validBlock;
    }

    public static boolean controlLengthBinaryCode(String inputMessage) {
        int length = determineLengthOfDecodeBinaryString(inputMessage);
        if (length % 7 == 0) {
            return true;
        } else {
            return false;
        }
    }
}