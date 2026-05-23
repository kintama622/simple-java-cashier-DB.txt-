import java.io.*;
import java.util.*;
public class reusable {
    public static void menuUtama(String pilih) {
        String[] menu = {"Create","Read","Update","Delete","Keluar"};
        int nomor = 1;
        for(String item:menu){
            if(item.equals("Keluar")){
                nomor = 0;
                System.out.printf("%d.%s\n",nomor,item);
            }else{
                System.out.printf("%d.%s %s\n",nomor,item,pilih);
            }
            nomor++;
        }
    }

    public static int pilihanUser(Scanner userInput){
        int pilihan;
        while(true){
            try{
                System.out.print("Masukan Angka Sesuai Menu : ");
                pilihan = userInput.nextInt();
                break;
            }catch(InputMismatchException e){
                System.out.println("Input Tidak Valid");
                userInput.next();
            }
        }
        return pilihan;
    }

    public static void init_console(String DB_Name){
        Scanner userInput = new Scanner(System.in);
        try(
            FileReader file = new FileReader(DB_Name);
            BufferedReader bufferFile = new BufferedReader(file);
        ){
            System.out.println("Database Ditemukan, silahkan lanjut!");
        }catch(FileNotFoundException e){
            System.out.println("File Tidak Ditemukan!");
            System.out.println("Membuat File!");
            admin.create_first_data(DB_Name, userInput);
        }catch(IOException e){
            System.out.println("Error IO");
        }
    }

    public static ArrayList<String> file(String namaDB){
        ArrayList<String> dataDB = new ArrayList<>();
        try(
            FileReader file = new FileReader(namaDB);
            BufferedReader bufferFile = new BufferedReader(file);
        ){
            String data;
            while((data = bufferFile.readLine())!= null){
                dataDB.add(data);
            }
        }catch(IOException e){
            System.out.println("Error IO");
        }
        return dataDB;
    }

    public static void write_all_from_arrayList(ArrayList<String> data, String DB_name){
        try(
            FileWriter file = new FileWriter(DB_name);
            BufferedWriter bufferFile = new BufferedWriter(file);
        ){
            for(String item:data){
                bufferFile.write(item,0,item.length());
                bufferFile.newLine();
            }
        }catch(IOException e){
            System.out.println("Error IO");
        }
    }

    public static void write_from_string(String data, String DB_Name){
        try(
            FileWriter file = new FileWriter(DB_Name,true);
            BufferedWriter bufferFile = new BufferedWriter(file);
        ){
            bufferFile.write(data,0,data.length());
            bufferFile.newLine();
        }catch(IOException e){
            System.out.println("Error IO");
        }
    }

}
