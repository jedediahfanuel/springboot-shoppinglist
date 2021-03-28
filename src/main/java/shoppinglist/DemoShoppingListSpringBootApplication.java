/*
  controller.java

  Created on Mar 21, 2021, 5:05:33 PM
 */
package shoppinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shoppinglist.controller.controller;
import shoppinglist.repository.DaftarBelanjaDetilRepo;
import shoppinglist.repository.DaftarBelanjaRepo;

import java.util.Scanner;

/**
 *
 * @author jeded
 */
@SpringBootApplication
public class DemoShoppingListSpringBootApplication implements CommandLineRunner
{
    @Autowired
    private DaftarBelanjaRepo repo;

    @Autowired
    private DaftarBelanjaDetilRepo repoDetil;

    public static void main(String[] args)
    {
        SpringApplication.run(DemoShoppingListSpringBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        Scanner cin = new Scanner(System.in);

        controller.lukisGarisHorizontal();

        boolean lanjut = true;
        while (lanjut) {
            System.out.println("" +
                    "Pilih menu (nomor): \n" +
                    "1. Tampilkan Semua List Belanja (Detail)\n" +
                    "2. Tampilkan Judul List Belanja berdasarkan ID\n" +
                    "3. Tampilkan Judul List Belanja berdasarkan Judul\n" +
                    "4. Tambah Data List Belanja Baru\n" +
                    "5. Perbaharui Data List Belanja\n" +
                    "6. Hapus Data List Belanja\n" +
                    "7. Keluar\n" +
                    "--> ");
            String pilihMenu = cin.nextLine();

            switch (pilihMenu) {
                case "1":
                    // Mendapatkan Semua List Daftar Belanja beserta Detilnya
                    controller.bacaSemuaRecord(repo);
                    break;
                case "2":
                    // Mendapatkan Semua List Daftar Belanja berdasarkan ID
                    controller.bacaRecordBerdasarkanID(repo, cin);
                    break;
                case "3":
                    // Mendapatkan Semua List Daftar Belanja berdasarkan Kemiripan String Judul
                    controller.bacaRecordBerdasarkanKemiripanString(repo, cin);
                    break;
                case "4":
                    // Menyimpan Data Daftar Belanja ke database
                    controller.tambahDaftarBelanja(repo, cin);
                    break;
                case "5":
                    // Memperbaharui Data Daftar Belanja berdasarkan id
                    controller.perbaharuiDaftarBelanja(repo, cin);
                    break;
                case "6":
                    // Menghapus Data Daftar Belanja berdasakan id
                    controller.hapusDaftarBelanja(repo, repoDetil, cin);
                    break;
                default:
                    controller.lukisGarisHorizontal();
                    System.out.println("Bye - bye");
                    lanjut = false;
                    break;
            }
        }
    }
}

