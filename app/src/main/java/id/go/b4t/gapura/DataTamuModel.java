package id.go.b4t.gapura;

/**
 * Created by Abraham_Lundy on 21/03/2018.
 */

public class DataTamuModel {

    String id;
    String nama;
    String kartu, instansi, noHp;

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getKartu() {
        return kartu;
    }

    public void setKartu(String kartu) {
        this.kartu = kartu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public DataTamuModel(String id, String nama, String kartu, String instansi, String noHp) {
        this.id = id;
        this.nama = nama;
        this.kartu= kartu;
        this.instansi= instansi;
        this.noHp= noHp;


    }


}
