import java.io.*;
import java.util.*;
public class admin {
    public static void menuAdmin() {
        String DB_Name = "menuRestoran.txt";
        Scanner userInput = new Scanner(System.in);
        reusable.init_console(DB_Name);
        while(true){
            System.out.println("===== Selamat Datang di Admin Menu =====");
            reusable.menuUtama("Menu");
            int pilihan = reusable.pilihanUser(userInput);
            switch (pilihan) {
                case 1:
                    create_data(DB_Name, userInput);
                    break;
                case 2:
                    read_menu(DB_Name);
                    break;
                case 3:
                    update_menu(DB_Name, userInput);
                    break;
                case 4:
                    delete_menu(DB_Name, userInput);
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }
    }

    public static void delete_menu(String DB_Name, Scanner userInput){
        ArrayList<String> dataDB = reusable.file(DB_Name);
        read_menu(DB_Name);
        System.out.print("Masukan Index Yang Ingin Dihapus : ");
        int indexUser = userInput.nextInt()-1;
        if (indexUser < 0 || indexUser > dataDB.size()){
            System.out.println("Input Tidak Valid!");
            return;
        }
        dataDB.remove(indexUser);
        reusable.write_all_from_arrayList(dataDB, DB_Name);
    }

    public static void update_menu(String DB_Name, Scanner userInput){
        ArrayList<String> dataDB = reusable.file(DB_Name);
        read_menu(DB_Name);
        System.out.print("Masukan Angka Sesuai Menu yang Akan di Update : ");
        int inputUpdate = userInput.nextInt()-1;
        if (inputUpdate < 0 || inputUpdate > dataDB.size()){
            System.out.println("Input Tidak Valid!");
            return;
        }
        String[] dataUpdate = dataDB.get(inputUpdate).split(",");
        System.out.println("1. Ganti Golongan");
        System.out.println("2. Ganti Nama Item");
        System.out.println("3. Ganti Harga Item");
        System.out.print("Masukan Angka Sesuai Index : ");
        int indexUser = userInput.nextInt()-1;
        if (indexUser < 0 || indexUser > dataUpdate.length){
            System.out.println("Input Tidak Valid");
            return;
        }
        System.out.print("Dirubah Menjadi : ");
        String update = userInput.next();
        dataUpdate[indexUser] = update;
        String dataBaru = dataUpdate[0]+","+dataUpdate[1]+","+dataUpdate[2];
        dataDB.set(inputUpdate, dataBaru);
        reusable.write_all_from_arrayList(dataDB,DB_Name);
    }

    public static void read_menu(String DB_Name){
        try(
            FileReader file = new FileReader(DB_Name);
            BufferedReader bufferFile = new BufferedReader(file);
        ){
            int nomor = 1;
            String data;
            System.out.printf("%-3s | %-9s | %-16s | %s\n","No","Kategori","Nama","Harga");
            System.out.println("--------------------------------------------------");
            while ((data = bufferFile.readLine())!= null) {
                StringTokenizer stringToken = new StringTokenizer(data,",");
                String kelompok = stringToken.nextToken();
                String namaItem = stringToken.nextToken();
                Float hargaItem = Float.parseFloat(stringToken.nextToken());
                System.out.printf("%-3d | %-9s | %-16s | %,.02f\n",nomor,kelompok,namaItem,hargaItem);
                nomor++;
            }
        }catch(IOException e){
            System.out.println("Error IO");
        }
    }

    public static void create_data(String DB_Name, Scanner userInput){
        try(
            FileWriter file = new FileWriter(DB_Name,true);
            BufferedWriter bufferFile = new BufferedWriter(file);
        ){
            System.out.print("Masukan Golongan (Makanan/Minuman) : ");
            String golongan = userInput.next();
            System.out.print("Masukan Nama Item : ");
            String namaItem = userInput.next();
            System.out.print("Masukan Harga Item : ");
            Float hargaItem = userInput.nextFloat();
            String data = golongan+","+namaItem+","+hargaItem;
            bufferFile.write(data,0,data.length());
            bufferFile.newLine();
            System.out.printf("%s | %s | %,.02f | BERHASIL DITAMBAHKAN\n",golongan,namaItem,hargaItem);
        }catch(IOException e){
            System.out.println("Error IO!");
        }
    }
    
    public static void create_first_data(String DB_Name, Scanner userInput){
        try(
            FileWriter file = new FileWriter(DB_Name);
            BufferedWriter bufferFile = new BufferedWriter(file);
        ){
            System.out.print("Masukan Golongan (Makanan/Minuman) : ");
            String golongan = userInput.next();
            System.out.print("Masukan Nama Item : ");
            String namaItem = userInput.next();
            System.out.print("Masukan Harga Item : ");
            Float hargaItem = userInput.nextFloat();
            String data = golongan+","+namaItem+","+hargaItem;
            bufferFile.write(data,0,data.length());
            bufferFile.newLine();
            System.out.printf("%s | %s | %,.02f | BERHASIL DITAMBAHKAN\n",golongan,namaItem,hargaItem);
        }catch(IOException e){
            System.out.println("Error IO. Tidak bisa buat data!");
        }
    }

}
