/*
  controller.java

  Created on Mar 22, 2021, 8:35:04 PM
 */
package shoppinglist.controller;

import org.springframework.data.domain.Sort;
import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;
import shoppinglist.repository.DaftarBelanjaDetilRepo;
import shoppinglist.repository.DaftarBelanjaRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author jeded
 */
public class controller
{

    public static void lukisGarisHorizontal()
    {
        final String garisHorizontal = "\n======================" +
                                       "========================" +
                                       "========================" +
                                       "========================" +
                                       "====================\n\n";
        System.out.println(garisHorizontal);
    }

    private static boolean checkUserResponse(String userInput)
    {
        return userInput.equals("Y") || userInput.equals("y");
    }

    public static long bacaIdTerakhir(DaftarBelanjaRepo repo)
    {
        // Mendapatkan id terakhir dari DaftarBelanja
        DaftarBelanja daftarBelanja = repo.findTopByOrderByIdDesc();

        return daftarBelanja.getId() + 1;
    }

    public static void bacaSemuaRecord(DaftarBelanjaRepo repo)
    {
        System.out.println("\nMendapatkan Semua Record DaftarBelanja");

        // Mendapatkan semua data (DaftarBelanja)
        List<DaftarBelanja> all = repo.findAll(Sort.by(Sort.Direction.ASC, "id"));

        for (DaftarBelanja db : all) {
            System.out.println(db.getId() + " - " + db.getJudul());

            List<DaftarBelanjaDetil> listBarang = db.getDaftarBarang();
            for (DaftarBelanjaDetil barang : listBarang) {
                System.out.println("\t " + barang.getNamaBarang() + " " + barang.getByk() + " " + barang.getSatuan());
            }
        }

        lukisGarisHorizontal();
    }

    public static void bacaSemuaJudul(DaftarBelanjaRepo repo)
    {
        System.out.println();

        // Mendapatkan semua data (DaftarBelanja)
        List<DaftarBelanja> all = repo.findAll(Sort.by(Sort.Direction.ASC, "id"));

        for (DaftarBelanja db : all) {
            System.out.println(db.getId() + " - " + db.getJudul());
        }

        lukisGarisHorizontal();
    }

    public static void bacaRecordBerdasarkanID(DaftarBelanjaRepo repo, Scanner cin)
    {
        System.out.println("\nMasukkan ID dari objek DaftarBelanja yang ingin ditampilkan");
        System.out.println("--> (-1 untuk batal)");

        long id = Long.parseLong(cin.nextLine());

        if (id != -1) {
            System.out.println("ID -> " + id);

            // Mendapatkan data (DaftarBelanja) berdasarkan id
            Optional<DaftarBelanja> optDB = repo.findById(id);
            if (optDB.isPresent()) {
                DaftarBelanja db = optDB.get();
                System.out.println("\tJudul : "  + db.getJudul() + "\n\tWaktu : " + db.getTanggal());

                List<DaftarBelanjaDetil> listBarang = db.getDaftarBarang();
                for (DaftarBelanjaDetil barang : listBarang) {
                    System.out.println(
                            "\t  > " + barang.getNamaBarang() +
                            " " + barang.getByk() +
                            " " + barang.getSatuan()
                    );
                    System.out.println("\t\t  >> " + barang.getMemo());
                }
            }
            else {
                System.out.println("List Belanja dengan id {" + id + "} tidak ditemukan.");
            }

            lukisGarisHorizontal();
        }
    }

    public static void bacaRecordBerdasarkanKemiripanString(DaftarBelanjaRepo repo, Scanner cin)
    {
        System.out.println("\nMasukkan kemiripan Judul dari objek DaftarBelanja yang ingin ditampilkan");
        System.out.println("--> (-1 untuk batal)");

        String stringJudul = cin.nextLine().trim();

        if (!stringJudul.equals("-1")) {
            System.out.println("Kemiripan -> " + stringJudul);

            // Mendapatkan data (DaftarBelanja) berdasarkan stringJudul
            List<DaftarBelanja> containingDB = repo.findByJudulIgnoreCaseContaining(stringJudul);

            if (containingDB.isEmpty()){
                System.out.println("List Belanja dengan kemiripan judul {" + stringJudul + "} tidak ditemukan.");
            }
            else {
                for (DaftarBelanja db : containingDB) {
                    System.out.println(db.getId() + " - " + db.getJudul());
                }
            }

            lukisGarisHorizontal();
        }
    }

    public static void tambahDaftarBelanja(DaftarBelanjaRepo repo, Scanner cin)
    {
        // Melakukan input judul dan tanggal Daftar Belanja
        System.out.println("\nInput Data :\n");
        System.out.println("Judul   : (-1 untuk batal)");
        String cinJudul = cin.nextLine().trim();

        if (!cinJudul.equals("-1")) {
            long idDaftarBelanja = bacaIdTerakhir(repo);
            LocalDateTime waktuTanggal = LocalDateTime.now().withNano(0);

            DaftarBelanja listBelanja = new DaftarBelanja();
            listBelanja.setId(idDaftarBelanja);
            listBelanja.setJudul(cinJudul);
            listBelanja.setTanggal(waktuTanggal);

            // Melakukan input barang
            System.out.println("\nMasukkan Data Barang");

            String isLanjut = "Y";
            int countNoUrut = 0;
            while (checkUserResponse(isLanjut)) {
                DaftarBelanjaDetil detilBarang = new DaftarBelanjaDetil();
                countNoUrut++;

                System.out.println("\nMasukkan Data Barang : ");
                System.out.println(countNoUrut + ". ");

                System.out.println("Nama Barang : ");
                String cinNamabarang = cin.nextLine().trim();

                System.out.println("Jumlah      : [Angka]");
                float cinJumlah = cin.nextFloat();

                // Sentinel
                cin.nextLine();

                System.out.println("Satuan      : ");
                String cinSatuan = cin.nextLine();

                System.out.println("Memo        : ");
                String cinMemo = cin.nextLine().trim();

                System.out.println("Simpan ? (Y/N)");
                String cinSimpan = cin.nextLine().trim();

                if (checkUserResponse(cinSimpan)) {
                    detilBarang.setId(listBelanja.getId(), countNoUrut);
                    detilBarang.setNamaBarang(cinNamabarang);
                    detilBarang.setByk(cinJumlah);
                    detilBarang.setSatuan(cinSatuan);
                    detilBarang.setMemo(cinMemo);
                    listBelanja.addDaftarBarang(detilBarang);
                } else {
                    countNoUrut--;
                }

                System.out.println("Tambah Lagi ? (Y/N)");
                isLanjut = cin.nextLine().trim();
            }

            // Mengirim detil barang ke database
            repo.save(listBelanja);

            // Melakukan feedback ke user
            System.out.println("Data baru berhasil disimpan ke database\n");
            System.out.println("|\tID    : " + listBelanja.getId());
            System.out.println("|\tJudul : " + listBelanja.getJudul());
            System.out.println("|\tWaktu : " + listBelanja.getTanggal());

            lukisGarisHorizontal();
        }
    }

    public static void perbaharuiDaftarBelanja(DaftarBelanjaRepo repo, Scanner cin)
    {
        // Membaca semua list belanja yang ada
        bacaSemuaJudul(repo);

        System.out.println("Masukkan ID dari objek DaftarBelanja yang ingin diperbaharui");
        System.out.println("--> (-1 untuk batal)");
        long idDaftarBelanja = Long.parseLong(cin.nextLine());

        if (idDaftarBelanja != -1) {
            System.out.println("\nID -> " + idDaftarBelanja);
            // Mendapatkan data (DaftarBelanja) berdasarkan id
            Optional<DaftarBelanja> optDB = repo.findById(idDaftarBelanja);
            if (optDB.isPresent()) {
                // Meminta data baru ke user untuk judul dan tanggal
                DaftarBelanja listBelanja = optDB.get();
                System.out.println(
                        "\tJudul : "  + listBelanja.getJudul() +
                        "\n\tWaktu : " + listBelanja.getTanggal() +
                        "\n"
                );

                LocalDateTime waktuTanggal = LocalDateTime.now().withNano(0);
                System.out.println("Waktu Update -> " + waktuTanggal);

                System.out.println("Judul Baru -> (-2 untuk skip)");
                String cinJudul = cin.nextLine().trim();

                if (!cinJudul.equals("-2")) {
                    listBelanja.setJudul(cinJudul);
                    listBelanja.setTanggal(waktuTanggal);
                }

                List<DaftarBelanjaDetil> listBarang = listBelanja.getDaftarBarang();

                String isLanjut = "Y";
                while (checkUserResponse(isLanjut)) {
                    // Menampilkan semua barang dari list terpilih
                    bacaListBarang(listBarang);

                    System.out.println("\nMasukkan ID dari barang yang ingin diperbaharui");
                    System.out.println("--> (-1 untuk batal)");
                    int noUrutBarang = Integer.parseInt(cin.nextLine());

                    if (noUrutBarang != -1) {
                        DaftarBelanjaDetil barang = listBelanja.getBarang(noUrutBarang);
                        bacaBarang(barang);

                        // Meminta data baru mengenai barang
                        System.out.println("Data baru : ");
                        System.out.println("-> Nama Barang : ");
                        String cinNamabarang = cin.nextLine().trim();

                        System.out.println("-> Jumlah      : [Angka]");
                        float cinJumlah = cin.nextFloat();

                        // Sentinel
                        cin.nextLine();

                        System.out.println("-> Satuan      : ");
                        String cinSatuan = cin.nextLine();

                        System.out.println("-> Memo        : ");
                        String cinMemo = cin.nextLine().trim();

                        System.out.println("Simpan ? (Y/N)");
                        String cinSimpan = cin.nextLine().trim();

                        if (checkUserResponse(cinSimpan)) {
                            barang.setNamaBarang(cinNamabarang);
                            barang.setByk(cinJumlah);
                            barang.setSatuan(cinSatuan);
                            barang.setMemo(cinMemo);
                            listBelanja.setBarang(barang, noUrutBarang);
                        }

                        System.out.println("Update Lagi ? (Y/N)");
                        isLanjut = cin.nextLine().trim();
                    } else {
                        isLanjut = "N";
                    }
                }

                // Menyimpan Data Daftar Belanja ke database
                repo.save(listBelanja);

                // Melakukan feedback ke user
                System.out.println("Data berhasil diperbaharui\n");
                System.out.println("\tID    : " + listBelanja.getId());
                System.out.println("\tJudul : " + listBelanja.getJudul());
                System.out.println("\tWaktu : " + listBelanja.getTanggal());

                for (DaftarBelanjaDetil barang : listBarang) {
                    System.out.println(
                            "\t  > " + barang.getNamaBarang() +
                            " " + barang.getByk() +
                            " " + barang.getSatuan()
                    );
                }
            }
            else {
                System.out.println("List Belanja dengan id {" + idDaftarBelanja + "} tidak ditemukan.");
            }

            lukisGarisHorizontal();
        }
    }

    private static void bacaListBarang(List<DaftarBelanjaDetil> listBarang)
    {
        System.out.println();
        for (DaftarBelanjaDetil barang : listBarang) {
            System.out.println(+
                    barang.getId().getNoUrut() + ". " +
                    barang.getNamaBarang() + " " +
                    barang.getByk() + " " +
                    barang.getSatuan() + " " +
                    barang.getMemo()
            );
        }
    }

    private static void bacaBarang(DaftarBelanjaDetil barang)
    {
        System.out.println("\n" +
                barang.getId().getNoUrut() + ". " +
                barang.getNamaBarang() + " " +
                barang.getByk() + " " +
                barang.getSatuan() + " " +
                barang.getMemo() + "\n"
        );
    }

    public static void hapusDaftarBelanja(DaftarBelanjaRepo repo, DaftarBelanjaDetilRepo repoDetil, Scanner cin)
    {
        // Membaca semua list belanja yang ada
        bacaSemuaJudul(repo);

        System.out.println("Masukkan ID dari objek DaftarBelanja yang ingin dihapus");
        System.out.println("--> (-1 untuk batal)");
        long id = Long.parseLong(cin.nextLine());

        if (id != -1) {
            System.out.println("\nID -> " + id);
            // Mendapatkan data (DaftarBelanja) berdasarkan id
            Optional<DaftarBelanja> optDB = repo.findById(id);
            if (optDB.isPresent()) {
                DaftarBelanja listBelanja = optDB.get();
                System.out.println(
                        "\tJudul : "  + listBelanja.getJudul() +
                        "\n\tWaktu : " + listBelanja.getTanggal() +
                        "\n"
                );

                System.out.println("" +
                        "Apakah anda yakin ingin menghapus data ini ? " +
                        "(Y/N)\n -->");
                String yakin = cin.nextLine();

                if (checkUserResponse(yakin)) {

                    // Menghapus Detil Belanja
                    repoDetil.deleteByDaftarbelanja_id(listBelanja.getId());

                    // Menghapus Daftar Belanja
                    repo.deleteById(id);
                    System.out.println("Data berhasil dihapus");
                }
                else {
                    System.out.println("Data tidak jadi dihapus");
                }
            }
            else {
                System.out.println("List Belanja dengan id {" + id + "} tidak ditemukan.");
            }

            lukisGarisHorizontal();
        }
    }
}
