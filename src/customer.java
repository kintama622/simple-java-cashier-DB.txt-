import java.io.*;
import java.util.*;
import java.time.*;
public class customer {
    public static void menuCustomer() {
        Scanner userInput = new Scanner(System.in);
        LocalDate tanggal = LocalDate.now();
        String DB_Name = "pesanan.txt";
        String codePesanan = UUID.randomUUID().toString().substring(0,8);
        create_pesanan_pertama(DB_Name, userInput,codePesanan,tanggal);
        while (true) {
            System.out.println("======= Selamat Datang di Customer Menu =======");
            reusable.menuUtama("Pesanan");
            int pilihan = reusable.pilihanUser(userInput);
            switch (pilihan) {
                case 1:
                    create_pesanan(DB_Name, userInput, codePesanan,tanggal);
                    break;
                case 2:
                    read_pesanan(DB_Name, codePesanan);
                    break;
                case 3:
                    update_pesanan(DB_Name, userInput, codePesanan);
                    break;
                case 4:
                    delete_pesanan(DB_Name, userInput, codePesanan);
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }
    }

    public static void delete_pesanan(String DB_Name, Scanner userInput, String codePesanan){
        ArrayList<String> dataDB = new ArrayList<>();
        try(
            FileReader file = new FileReader(DB_Name);
            BufferedReader bufferFile = new BufferedReader(file);
        ){
            String data;
            while ((data = bufferFile.readLine())!= null){
                StringTokenizer stringToken = new StringTokenizer(data,",");
                String code = stringToken.nextToken();
                if (code.equals(codePesanan)){
                    dataDB.add(data);
                }
            }
        }catch (IOException e){
            System.out.println("Error IO");
        }
        read_pesanan(DB_Name, codePesanan);
        System.out.print("Masukan Angka yang Akan Didelete : ");
        int indexDelete = userInput.nextInt()-1;
        if (indexDelete < 0 || indexDelete > dataDB.size()){
            System.out.println("Index tidak Valid!");
            return;
        }
        String[] partsDataDB = dataDB.get(indexDelete).split(",");
        String codeDelete = partsDataDB[0];
        String namaDelete = partsDataDB[3];
        ArrayList<String> NewDataDB = new ArrayList<>();
        try(
            FileReader newFile = new FileReader(DB_Name);
            BufferedReader bufferNewFile = new BufferedReader(newFile);
        ){
            String data;
            while ((data = bufferNewFile.readLine())!= null){
                NewDataDB.add(data);
            }
        }catch(IOException e){
            System.out.println("IO Error");
        }
        for (int i = 0; i < NewDataDB.size(); i++){
            String[] partsNewDataDB = NewDataDB.get(i).split(",");
            String codeNewDataDB = partsNewDataDB[0];
            String namaNewDataDB = partsNewDataDB[3];
            if (codeNewDataDB.equalsIgnoreCase(codeDelete) && namaNewDataDB.equalsIgnoreCase(namaDelete)){
                NewDataDB.remove(i);
            }
        }
        reusable.write_all_from_arrayList(NewDataDB, DB_Name);
    }
    

    public static void update_pesanan(String DB_Name, Scanner userInput, String codePesanan){
        ArrayList<String> dataDB = new ArrayList<>();
        try(
            FileReader file = new FileReader(DB_Name);
            BufferedReader bufferFile = new BufferedReader(file);
        ){
            String data;
            while((data = bufferFile.readLine())!= null){
                StringTokenizer stringToken = new StringTokenizer(data,",");
                String code = stringToken.nextToken();
                if (code.equals(codePesanan)){
                    dataDB.add(data);
                }
            }
        }catch(IOException e){
            System.out.println("Error IO");
        }
        read_pesanan(DB_Name, codePesanan);
        System.out.print("Masukan Index yang akan diupdate : ");
        int indexUpdate = userInput.nextInt()-1;
        if (indexUpdate < 0 || indexUpdate > dataDB.size()){
            System.out.println("Index tidak Valid!");
            return;
        }
        String[] partsDataDB = dataDB.get(indexUpdate).split(",");
        System.out.printf("Masukan Qty Baru : ");
        int qtyBaru = userInput.nextInt();
        String codeLama = partsDataDB[0];
        String tanggalLama = partsDataDB[1];
        String golonganLama = partsDataDB[2];
        String namaItemLama = partsDataDB[3];
        String hargaItemLama = partsDataDB[4];
        String qtyItemBaru = String.valueOf(qtyBaru);
        String dataBaru = codeLama+","+tanggalLama+","+golonganLama+","+namaItemLama+","+hargaItemLama+","+qtyItemBaru;
        ArrayList<String> newDataDB = new ArrayList<>();
        try(
            FileReader fileBaru = new FileReader(DB_Name);
            BufferedReader bufferFileBaru = new BufferedReader(fileBaru);
        ){
            String data;
            while((data = bufferFileBaru.readLine())!= null){
                newDataDB.add(data);
            }
        }catch(IOException e){
            System.out.println("Error IO");
        }
        for (int i = 0;i < newDataDB.size();i++){
            String[] partsDataBaru = newDataDB.get(i).split(",");
            String code = partsDataBaru[0];
            String namaItem = partsDataBaru[3];
            if (code.equalsIgnoreCase(codeLama) && namaItem.equalsIgnoreCase(namaItemLama)){
                newDataDB.set(i, dataBaru);
            }
        }
        reusable.write_all_from_arrayList(newDataDB, DB_Name);
    }

    public static void read_pesanan(String DB_Name, String codePesanan){
        try(
            FileReader file = new FileReader(DB_Name);
            BufferedReader bufferFile = new BufferedReader(file);
        ){
            String data;
            int nomor = 1;
            float total = 0;
            System.out.println("================================== NOTA ==================================");
            System.out.println("--------------------------------------------------------------------------");
            System.out.printf("%-3s | %-12s | %-10s | %-8s | %-4s | %-9s | %s\n","No","Tanggal","Golongan","Item","Qty","Harga","Subtotal");
            while ((data = bufferFile.readLine())!= null){
                StringTokenizer stringToken = new StringTokenizer(data,",");
                String code = stringToken.nextToken();
                String tanggal = stringToken.nextToken();
                String golongan = stringToken.nextToken();
                String namaItem = stringToken.nextToken();
                float hargaItem = Float.parseFloat(stringToken.nextToken());
                int qtyItem = Integer.parseInt(stringToken.nextToken());
                if (codePesanan.equalsIgnoreCase(code)){
                    float subtotal = hargaItem*qtyItem;
                    total+= subtotal;
                    System.out.printf("%-3d | %-12s | %-10s | %-8s | %-4d | %,.02f | %,.02f\n",nomor,tanggal,golongan,namaItem,qtyItem,hargaItem,subtotal);
                    nomor++;
                }
            }
            System.out.println("--------------------------------------------------------------------------");
            float pajak = 0.1f;
            float diskon = 0f;
            if (total > 1e5){
                diskon = 0.1f;
            }
            System.out.printf("Total Belanja   : %,.02f\n",total);
            System.out.printf("Pajak           : %.0f%%\n",pajak*100);
            System.out.printf("Diskon          : %.0f%%\n",diskon*100);
            System.out.printf("Biaya Pelayanan : %,.02f\n",20e3);
            if (diskon == 0){
                float grandTotal = ((total*pajak)+total)+20000;
                System.out.printf("Grand Total     : %,.02f\n",grandTotal);
            }else{
                float grandTotal = ((total-(total*diskon))+(total*pajak))+20000;
                System.out.printf("Grand Total     : %,.02f\n",grandTotal);
            }

        }catch(IOException e){
            System.out.println("IO Error");
        }
    }

    public static void create_pesanan(String DB_Name, Scanner userInput, String codePesanan, LocalDate tanggal){
        String DBnamaMenu = "menuRestoran.txt";
        String DBnamaPesanan = DB_Name;
        ArrayList <String> DBmenu = reusable.file(DBnamaMenu);
        ArrayList <String> DBpesanan = reusable.file(DBnamaPesanan);
        admin.read_menu(DBnamaMenu);
        System.out.print("Masukan Index Sesuai Menu : ");
        int indexPesan = userInput.nextInt()-1;
        if(indexPesan < 0 || indexPesan > DBmenu.size()){
            System.out.println("Index Tidak Valid!");
            return;
        }
        System.out.print("Masukan Qty : ");
        int qtyPesan = userInput.nextInt();
        String[] partMenu = DBmenu.get(indexPesan).split(",");
        String golonganItem = partMenu[0];
        String namaItemPesanan = partMenu[1];
        String hargaItemMenu = partMenu[2];
        boolean ditemukan = false;
        for (int i = 0; i < DBpesanan.size();i++){
            String[] partPesanan = DBpesanan.get(i).split(",");
            String code = partPesanan[0];
            String tanggalLama = partPesanan[1];
            String golongan = partPesanan[2];
            String namaItem = partPesanan[3];
            String hargaItem = partPesanan[4];
            int qtyLama = Integer.parseInt(partPesanan[5]);
            if (code.equalsIgnoreCase(codePesanan) && namaItem.equalsIgnoreCase(namaItemPesanan)){
                int qtyBaru = qtyLama+qtyPesan;
                String dataBaru = code+","+tanggalLama+","+golongan+","+namaItem+","+hargaItem+","+qtyBaru;
                DBpesanan.set(i, dataBaru);
                ditemukan = true;
                break;
            }
        }
        if (!ditemukan){
            String data = codePesanan+","+tanggal+","+golonganItem+","+namaItemPesanan+","+hargaItemMenu+","+qtyPesan;
            DBpesanan.add(data);
        }
        reusable.write_all_from_arrayList(DBpesanan, DBnamaPesanan);
    }

    public static void create_pesanan_pertama(String DB_Name, Scanner userInput, String codePesanan, LocalDate tanggal){
        String menuDBName = "menuRestoran.txt";
        ArrayList<String> DBmenu = reusable.file(menuDBName);
        
        admin.read_menu(menuDBName);
        System.out.print("Masukan Angka Sesuai Menu : ");
        int indexPesanan = userInput.nextInt()-1;
        if (indexPesanan < 0 || indexPesanan>DBmenu.size()){
            System.out.println("Index tidak Valid!");
        }
        System.out.print("Masukan Qty : ");
        int qtyPesanan = userInput.nextInt();
        String[] parts = DBmenu.get(indexPesanan).split(",");
        String golonganItem = parts[0];
        String namaItem = parts[1];
        String hargaItem = parts[2];
        String data = codePesanan+","+tanggal+","+golonganItem+","+namaItem+","+hargaItem+","+qtyPesanan;
        reusable.write_from_string(data, DB_Name);
    }

}