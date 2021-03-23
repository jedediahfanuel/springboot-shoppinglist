package shoppinglist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;
import shoppinglist.entity.DaftarBelanjaDetilId;
import shoppinglist.repository.DaftarBelanjaDetilRepo;
import shoppinglist.repository.DaftarBelanjaRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class controller
{
    public static void lukisGarisHorizontal()
    {
        final String garisHorizontal = "\n================================================================================================================\n\n";
        System.out.println(garisHorizontal);
    }

    public static void bacaSemuaRecord(DaftarBelanjaRepo repo)
    {
        System.out.println("Mendapatkan Semua Record DaftarBelanja");

        // Mendapatkan semua data (DaftarBelanja)
        List<DaftarBelanja> all = repo.findAll(Sort.by(Sort.Direction.ASC, "id"));

        for (DaftarBelanja db : all) {
            System.out.println(db.getId() + " - " + db.getJudul());

            List<DaftarBelanjaDetil> listBarang = db.getDaftarBarang();
            for (DaftarBelanjaDetil barang : listBarang) {
                System.out.println("\t  " + barang.getNamaBarang() + " " + barang.getByk() + " " + barang.getSatuan());
            }
        }

        lukisGarisHorizontal();
    }

    public static void bacaSemuaJudul(DaftarBelanjaRepo repo)
    {
        System.out.println("");

        // Mendapatkan semua data (DaftarBelanja)
        List<DaftarBelanja> all = repo.findAll(Sort.by(Sort.Direction.ASC, "id"));

        for (DaftarBelanja db : all) {
            System.out.println(db.getId() + " - " + db.getJudul());
        }

        lukisGarisHorizontal();
    }

    public static void bacaRecordBerdasarkanID(DaftarBelanjaRepo repo, Scanner cin)
    {
        System.out.println("\nMasukkan ID dari objek DaftarBelanja yang ingin ditampilkan\n");
        System.out.println("--> (-1 untuk batal)");

        long id = Long.parseLong(cin.nextLine());

        if (id != -1) {
            System.out.println("ID -> " + id);

            // Mendapatkan data (DaftarBelanja) berdasarkan id
            Optional<DaftarBelanja> optDB = repo.findById(id);
            if (optDB.isPresent()) {
                DaftarBelanja db = optDB.get();
                System.out.println("\tJudul : "  + db.getJudul() + "\n\tWaktu : " + db.getTanggal());
            }
            else {
                System.out.println("List Belanja dengan id {" + id + "} tidak ditemukan.");
            }

            lukisGarisHorizontal();
        }
    }

    public static void bacaRecordBerdasarkanKemiripanString(DaftarBelanjaRepo repo, Scanner cin)
    {
        System.out.println("\nMasukkan kemiripan Judul dari objek DaftarBelanja yang ingin ditampilkan\n");
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
        System.out.println("\nInput Data :\n");
        System.out.println("Judul   : (-1 untuk batal)");
        String cinJudul = cin.nextLine().trim();

        if (!cinJudul.equals("-1")) {
            LocalDateTime waktuTanggal = LocalDateTime.now().withNano(0);

            DaftarBelanja listBelanja = new DaftarBelanja();
            listBelanja.setJudul(cinJudul);
            listBelanja.setTanggal(waktuTanggal);

            // Menyimpan Data Daftar Belanja ke database
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
        bacaSemuaJudul(repo);

        System.out.println("Masukkan ID dari objek DaftarBelanja yang ingin diperbaharui\n");
        System.out.println("--> (-1 untuk batal)");
        long id = Long.parseLong(cin.nextLine());

        if (id != -1) {
            System.out.println("\nID -> " + id);
            // Mendapatkan data (DaftarBelanja) berdasarkan id
            Optional<DaftarBelanja> optDB = repo.findById(id);
            if (optDB.isPresent()) {
                DaftarBelanja listBelanja = optDB.get();
                System.out.println("\tJudul : "  + listBelanja.getJudul() + "\n\tWaktu : " + listBelanja.getTanggal() + "\n");

                LocalDateTime waktuTanggal = LocalDateTime.now().withNano(0);
                System.out.println("Waktu -> " + waktuTanggal);

                System.out.println("Judul -> ");
                String inJudul = cin.nextLine().trim();

                listBelanja.setJudul(inJudul);
                listBelanja.setTanggal(waktuTanggal);

                // Menyimpan Data Daftar Belanja ke database
                repo.save(listBelanja);

                // Melakukan feedback ke user
                System.out.println("Data berhasil diperbaharui\n");
                System.out.println("\tID    : " + listBelanja.getId());
                System.out.println("\tJudul : " + listBelanja.getJudul());
                System.out.println("\tWaktu : " + listBelanja.getTanggal());
            }
            else {
                System.out.println("List Belanja dengan id {" + id + "} tidak ditemukan.");
            }

            lukisGarisHorizontal();
        }
    }

    public static void hapusDaftarBelanja(DaftarBelanjaRepo repo, DaftarBelanjaDetilRepo repoDetil, Scanner cin)
    {
        bacaSemuaJudul(repo);

        System.out.println("Masukkan ID dari objek DaftarBelanja yang ingin dihapus\n");
        System.out.println("--> (-1 untuk batal)");
        long id = Long.parseLong(cin.nextLine());

        if (id != -1) {
            System.out.println("\nID -> " + id);
            // Mendapatkan data (DaftarBelanja) berdasarkan id
            Optional<DaftarBelanja> optDB = repo.findById(id);
            if (optDB.isPresent()) {
                DaftarBelanja listBelanja = optDB.get();
                System.out.println("\tJudul : "  + listBelanja.getJudul() + "\n\tWaktu : " + listBelanja.getTanggal() + "\n");

                System.out.println("" +
                        "Apakah anda yakin ingin menghapus data ini ? " +
                        "(Y/N)\n -->");
                String yakin = cin.nextLine();

                if (yakin.equals("y") || yakin.equals("Y")) {

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
