package id.go.b4t.gapura;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    EditText username,password;
    Bundle bundle ;
    JSONArray jsonResponse;
    JSONObject obj;
    Intent dashboard;
    AlertDialog pDialog;
    Boolean state=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        dashboard = new Intent(this, TabelTamu.class);
        username=(EditText) findViewById(R.id.editUname);
        password=(EditText) findViewById(R.id.editPassWd);
        bundle =  new Bundle();

        if(state==true){
            Intent i = new Intent(this, TabelTamu.class);
            startActivity(i);
            this.finish();
        }

    }
    public void klikButtonLogin(View v) {
        bundle.putString("username",username.getText().toString());
        bundle.putString("password",password.getText().toString());


        if (username.getText().toString().length()==0&&password.getText().toString().length()==0){
            username.setError("pastikan tidak ada yang salah");
            password.setError("pastikan password benar");
            Toast.makeText(this, "Data Masih Ada Kosong",Toast.LENGTH_LONG).show();
        }else {

            login(username.getText().toString(), password.getText().toString());
        }
        //login_2(username.getText().toString(),password.getText().toString());
        //startActivity(dashboard);
//        this.finish();
    }

    private void login(String username,String password) {
        String url = "http://gapura.b4t.go.id/index.php/api/login?username="+username+"&password="+password;
        //String request adalah milik dari Volley library bertujuan untuk meyimpan perintah request
        //jadi String ini adalah string yang berisi syntax
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, url, response -> {

            try {
                Log.d("LOGIN","Response :"+response);
                obj = new JSONObject(response);
                //for (int i = 0; i < jsonResponse.length(); i++) {
                //obj = jsonResponse.getJSONObject(1);
                Log.d("LOGIN","obj :"+obj);
                String status = String.valueOf(response.contains("status"));
                //                  String message = obj.getString("message");
                if (obj.getString("logged_in").equals("true")==true) {
                    state=true;
                    bundle.putBoolean("state", state);
                    dashboard.putExtras(bundle);
                    this.finish();
                    startActivity(dashboard);
                    // String img = obj.getString("url");
                    Log.d("LOGIN", "status: " + status + " pesan :");
                    Toast.makeText(this, "Selamat Datang", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                Log.d("LOGIN","ggagall");
                Toast.makeText(this, "Login gagal.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> Log.d("LOGIN", "error_login: " + error)) {
            //Jika gagal maka coba pindah ke dalam mode MAP / pemetaan pdengan string parameter
            @Override
            protected Map<String, String> getParams(){
                //String username = username;
                //String password = password;
                Map<String, String> map = new HashMap<>();
                //masukkan nilai ke dalam varibel id
                //  map.put("username", username);
                //map.put("password", password);
                //return null;
                Log.d("LOGIN", "DATA MAP: " + map);
                return null;
            }
        };

        //String jsonObj perintah dikirim
        NetworkRequest.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    @Override
    public void onBackPressed() {

    }
}
