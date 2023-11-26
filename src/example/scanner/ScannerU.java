package example.scanner;

import java.util.Scanner;

public class ScannerU {
        Scanner scanner = new Scanner(System.in);

    public String printString(String s) {
        System.out.print(s);
        String res= scanner.nextLine();
        return res;
    }
    public void onlyPrint(String s) {
        System.out.print(s);
    }

    public int printInt(String s) {
        int result=-1;
        do {
            try {
                System.out.print(s);
                result= scanner.nextInt();
                scanner.nextLine();
                break;
            }catch (Exception e){
                System.out.println("Please enter only a number");
                scanner.nextLine();
            }
        }while (true);
        return result;

    }
}
