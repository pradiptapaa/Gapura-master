package id.go.b4t.gapura;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.go.b4t.gapura.Adapter.CustomAdapter;

public class TabelTamu extends AppCompatActivity {


    private List<DataTamuModel> tagList;
    //penghubung antara data dengan listview
    //ArrayAdapter adapter;
    JSONArray jsonResponse;
    JSONObject obj;
    ListView listView;
    Bundle a;
    CustomAdapter adapter;
    String url;
    SwipeRefreshLayout swLayout;
    public boolean status=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabel_tamu);

        showGuest();
        tagList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        swLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        Snackbar.make(swLayout,"Drag kebawah untuk menampilkan",Snackbar.LENGTH_SHORT).show();

        adapter = new CustomAdapter(this, tagList);
        //a = getIntent().getExtras();
        // Log.i("hasil_bundle",a.getCharSequence("username")+" "+a.getCharSequence("password"));
        //   Toast.makeText(this,a.getCharSequence("username")+" "+a.getCharSequence("password"),Toast.LENGTH_LONG).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("isi", "hasil=" + adapter.getItem(position).id);
                dialog(String.valueOf(adapter.getItem(position).nama), adapter.getItem(position).id).show();

            }
        });

        swLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("Log", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.

                        adapter.clear();
                        showGuest();
                        swLayout.setRefreshing(false);
                    }
                }
        );

    }


    private void showGuest() {

        url = "http://gapura.b4t.go.id/index.php/api/";
        //String request adalah milik dari Volley library bertujuan untuk meyimpan perintah request
        //jadi String ini adalah string yang berisi syntax
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, url, response -> {

            try {
                jsonResponse = new JSONArray(response);
                for (int i = jsonResponse.length()-1; i >= 0 ; i--) {
                    obj = jsonResponse.getJSONObject(i);

                    String id = obj.getString("id");
                    String nama = obj.getString("Nama");
                    String kartu=obj.getString("waktu");
                    String instansi=obj.getString("Instansi");
                    String noHp=obj.getString("noHp");




                    // String img = obj.getString("url");
                    Log.d("DEBUG", "showGuest: " + id + " nama : " + nama);
                    // Toast.makeText(this,id+""+nama,Toast.LENGTH_LONG).show();
                    //asuk kedalam tagList
                    tagList.add(new DataTamuModel(id, nama,kartu,instansi,noHp));
                    listView.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("DEBUG", "error: " + error)) {

        };

        //String jsonObj perintah dikirim
        NetworkRequest.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    public void klikButtonTambah(View v) {
        Intent dashboard = new Intent(this, Tambah_Tamu.class);
        startActivity(dashboard);
        this.finish();
    }

    public AlertDialog dialog(String position, String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Option:")
                .setCancelable(true)
                .setItems(new CharSequence[] {"Delete"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                Toast.makeText(getApplicationContext(),position+" Telah Dihapus",Toast.LENGTH_LONG).show();
                                klikhapus(id);
                                break;

                           /* case 1:
                                Toast.makeText(getApplicationContext(),"Pilihan 1"+which,Toast.LENGTH_LONG).show();
                                klikcheckout(position,id);
                                showGuest();
                                break;
                            default:break;*/
                        }

                    }
                });
        return builder.create();
    }

    public void  klikcheckout(String id,String kartu){
        url = "http://gapura.b4t.go.id/index.php/api/checkout?id="+id+"&label="+kartu;

        //String request adalah milik dari Volley library bertujuan untuk meyimpan perintah request
        //jadi String ini adalah string yang berisi syntax
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, url, response -> {

            try {
                Log.d("LOGIN","Response :"+response);
                obj = new JSONObject(response);

//
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("DEBUG", "error: " + error)) {

        };

        //String jsonObj perintah dikirim
        NetworkRequest.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    public void  klikhapus(String id){
        url = "http://gapura.b4t.go.id/index.php/api/hapus/"+id;
        //String request adalah milik dari Volley library bertujuan untuk meyimpan perintah request
        //jadi String ini adalah string yang berisi syntax
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, url, response -> {

            try {
                Log.d("LOGIN","Response :"+url);
                obj = new JSONObject(response);
                adapter.clear();
                showGuest();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("DEBUG", "error: " + error)) {

        };

        //String jsonObj perintah dikirim
        NetworkRequest.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    private void keyakinan(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Anda Yakin?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya untuk keluar!")
                .setIcon(R.drawable.b4t)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        TabelTamu.this.finish();
                        startActivity(new Intent(TabelTamu.this,Login.class));
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
keyakinan();
    }



}
