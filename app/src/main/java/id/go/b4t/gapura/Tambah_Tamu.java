package id.go.b4t.gapura;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tambah_Tamu extends AppCompatActivity implements View.OnClickListener,Spinner.OnItemSelectedListener {

    //Deklarasi atribut manifest
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    private JSONArray result;
    //deklarasi atribut properti
    Button scanBtn;
    Button chooseFile;
    Button Back, Send;
    Spinner Card;
    RadioGroup genderRadioGroup;
    RadioGroup nationalityRadioGroup;
    EditText nama;
    EditText instansi;
    EditText nomor;
    EditText kartu;
    Bundle hasil;
    RadioButton genderRadio;
    RadioButton nationalityRadio;
    JSONArray jsonResponse;
    JSONObject obj;
    ImageView photoImage;
    ImageView cardImage;
    private String imagePath;
    private boolean isSelfie;
    private ArrayList<String> label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah__tamu);
        label = new ArrayList<String>();

        scanBtn = (Button) findViewById(R.id.scanButton);
        Card = (Spinner) findViewById(R.id.Card);
        chooseFile = (Button) findViewById(R.id.chooseFileButton);
        Back = (Button) findViewById(R.id.backButton);
        Send = (Button) findViewById(R.id.addButton);

        getData();

        chooseFile.setOnClickListener(this);
        scanBtn.setOnClickListener(this);
        Card.setOnItemSelectedListener(this);


        //  Card.setOnClickListener(this);
        Back.setOnClickListener(this::klikButtonBalik);
        Send.setOnClickListener(this::klik_kirim);
        hasil = new Bundle();
        //deklarasi varibel dan properti tampilan
        findAllViewsId();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
/*        scanBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent openCam = new Intent(getApplicationContext(),ScanActivity.class);
                startActivityForResult(openCam,REQUEST_CODE);
            }
        });*/
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chooseFileButton:
                isSelfie = true;
                ImagePicker.create(this)
                        .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                        .single() // single mode
                        .limit(1) // max images can be selected (99 by default)
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .enableLog(false) // disabling log
                        .start(); // start image picker activity with request code
                break;
            case R.id.scanButton:
                isSelfie = false;
                ImagePicker.create(this)
                        .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                        .single() // single mode
                        .limit(1) // max images can be selected (99 by default)
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .enableLog(false) // disabling log
                        .start(); // start image picker activity with request code
                break;

        }
    }


    private void findAllViewsId() {
        //button = (Button) findViewById(R.id.kirim);
        nama = (EditText) findViewById(R.id.Nama);
        instansi = (EditText) findViewById(R.id.Instansi);
        //kartu = (EditText) findViewById(R.id.Card);
        nomor = (EditText) findViewById(R.id.Nomor_Hp);
        genderRadioGroup = (RadioGroup) findViewById(R.id.jenisKelamin);
        nationalityRadioGroup = (RadioGroup) findViewById(R.id.nationality);

        photoImage = findViewById(R.id.photoImage);
        cardImage = findViewById(R.id.cardImage);
    }

    public void klik_kirim(View v) {

        hasil.putString("nama", nama.getText().toString());
        hasil.putString("instansi", instansi.getText().toString());
        hasil.putString("nomor", nomor.getText().toString());
        hasil.putString("id", "4");
        genderRadio = (RadioButton) findViewById(genderRadioGroup.getCheckedRadioButtonId());
        nationalityRadio = (RadioButton) findViewById(nationalityRadioGroup.getCheckedRadioButtonId());
        hasil.putString("JenisKelamin", genderRadio.getText().toString());
        hasil.putString("Wargaan", nationalityRadio.getText().toString());

        Log.d("instance 1", String.valueOf(hasil));
        // Intent Kotak = new Intent(this,Hasil.class);
        //Kotak.putExtras(hasil);
        //startActivity(Kotak);
        if (nama.getText().toString().length() == 0 && nomor.getText().toString().length() == 0 && instansi.getText().toString().length() == 0 && cardImage.getDrawable() == null && photoImage.getDrawable() ==null) {
            Toast.makeText(this, "Data Masih Ada Kosong", Toast.LENGTH_LONG).show();
        } else {
            showDialog();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                Card.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        }
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) { // jika ada data dipilih
            Image image = ImagePicker.getFirstImageOrNull(data); //ambil first image
            File imgFile = new File(image.getPath()); // dapatkan lokasi gambar yang dipilih
            imagePath = image.getPath();
            if (imgFile.exists()) { //jika ditemukan
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath()); //convert file ke bitmap
                rotateImage(myBitmap);
                //photoImage.setImageBitmap(myBitmap); //set imageview dengan gambar yang dipilih
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void rotateImage(Bitmap bitmap) {
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(270);
                break;
        }
        Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (!isSelfie) {
            cardImage.setVisibility(View.VISIBLE);
            cardImage.setImageBitmap(rotateBitmap);
        } else
            photoImage.setImageBitmap(rotateBitmap);

    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Apakah Data Anda sudah Benar?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Tekan Ya jika sudah yakin!")
                .setIcon(R.drawable.b4t)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        tambah();
                        finish();
                        startActivity(new Intent(Tambah_Tamu.this, Tambah_Tamu.class)); //panggil activity main
                        Toast.makeText(Tambah_Tamu.this, "Tambah Tamu Berhasil", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
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

    /*
   * The method is taking Bitmap as an argument
   * then it will return the byte[] array for the given bitmap
   * and we will send this array to the server
   * here we are using PNG Compression with 80% quality
   * you can give quality between 0 to 100
   * 0 means worse quality
   * 100 means best quality
   * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void tambah() {
        String url1 = "http://gapura.b4t.go.id/index.php/api_pa";
        String url = url1.replace(" ", "+");
        Bitmap image = Bitmap.createScaledBitmap((((BitmapDrawable) cardImage.getDrawable()).getBitmap()),400,300,true);
        Bitmap images = Bitmap.createScaledBitmap((((BitmapDrawable) photoImage.getDrawable()).getBitmap()),300,400,true);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                //masukkan nilai ke dalam varibel id
                map.put("nama", hasil.getString("nama"));
                map.put("idAkses", hasil.getString("id"));
                map.put("label", hasil.getString("kartu"));
                map.put("instansi", hasil.getString("instansi"));
                map.put("noHp", hasil.getString("nomor"));
                map.put("gender", hasil.getString("JenisKelamin"));
                map.put("wn", hasil.getString("Wargaan"));

                Log.d("hasil", String.valueOf(map));
                return map;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                String imageName = hasil.getString("nama").trim();
                params.put("image", new DataPart(imageName + "Kartu" + ".png", getFileDataFromDrawable(image)));
                params.put("images", new DataPart(imageName + "Selfie" + ".png", getFileDataFromDrawable(images)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public void klikButtonBalik(View v) {
        Intent dashboard = new Intent(this, TabelTamu.class);
        startActivity(dashboard);
        this.finish();
    }


    private void getData() {
        String urlz = "http://gapura.b4t.go.id/index.php/api_pa_2/";
        //String request adalah milik dari Volley library bertujuan untuk meyimpan perintah request
        //jadi String ini adalah string yang berisi syntax
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, urlz, response -> {

            try {
                jsonResponse = new JSONArray(response);
                for (int i = jsonResponse.length()-1; i >= 0 ; i--) {
                    obj = jsonResponse.getJSONObject(i);

                    String id = obj.getString("label");

                    // String img = obj.getString("url");
                    Log.d("DEBUG", "showGuest: " + id + " nama : " + nama);
                    label.add(id);
                }
                Card.setAdapter(new ArrayAdapter<String>(Tambah_Tamu.this, android.R.layout.simple_spinner_dropdown_item, label));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> Log.d("DEBUG", "error: " + error)) {

        };

        //String jsonObj perintah dikirim
        NetworkRequest.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        hasil.putString("kartu", item);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

}