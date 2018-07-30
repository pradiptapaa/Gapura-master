package id.go.b4t.gapura;

/**
 * Created by Abraham_Lundy on 20/03/2018.
 */

public class konfigurasi {
        //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
        //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
        //dimana File PHP tersebut berada
        //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
        public static final String URL_ADD="http://192.168.1.9/Android/pegawai/tambahPgw.php";
        public static final String URL_GET_ALL = "gapura.b4t.go.id/index.php/api";
        public static final String URL_GET_EMP = "http://gapura.b4t.go.id/index.php/api/";
        public static final String URL_UPDATE_EMP = "http://192.168.1.9/Android/pegawai/updatePgw.php";
        public static final String URL_DELETE_EMP = "http://gapura.b4t.go.id/index.php/api/hapus/";

        //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
        public static final String KEY_EMP_ID = "id";
        public static final String KEY_EMP_NAMA = "name";
        public static final String KEY_EMP_POSISI = "card"; //desg itu variabel untuk posisi
        public static final String KEY_EMP_GAJIH = "salary"; //salary itu variabel untuk gajih

        //JSON Tags
        public static final String TAG_JSON_ARRAY="result";
        public static final String TAG_ID = "id";
        public static final String TAG_NAMA = "name";
        public static final String TAG_POSISI = "card";
        public static final String TAG_GAJIH = "salary";

        //ID karyawan
        //emp itu singkatan dari Employee
        public static final String EMP_ID = "emp_id";
}
