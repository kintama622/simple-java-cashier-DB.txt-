import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("====== PROGRAM RESTORAN SEDERHANA =====");
        System.out.println("1. Menu Admin");
        System.out.println("2. Menu Customer");
        int pilihan;
        while (true) {
            try{
                System.out.print("Masukan Angka Seusuai Menu : ");
                pilihan = userInput.nextInt();
                break;
            }catch(InputMismatchException e){
                System.out.println("Input Tidak Valid!");
                userInput.next();
            }
        }
        switch (pilihan) {
            case 1:
                admin.menuAdmin();
                break;
            case 2:
                customer.menuCustomer();
                break;
            default:
                break;
        }
    }
}
