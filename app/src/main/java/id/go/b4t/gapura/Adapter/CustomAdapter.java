package id.go.b4t.gapura.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import id.go.b4t.gapura.DataTamuModel;
import id.go.b4t.gapura.R;

/**
 * Created by Abraham Lundy on 3/22/2018
 */

public class CustomAdapter extends ArrayAdapter<DataTamuModel> {

    public CustomAdapter(Context context, List<DataTamuModel> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // View mengambil convertView
        View v = convertView;

        if (v == null) {
            // Deklarasi inflater agar hasil dari view bentukan bisa dipompa ke dalam tampilan
            LayoutInflater vi;
            // Deklarasi agar dipompa ke dalam context tampilan yang diambil
            vi = LayoutInflater.from(getContext());
            //Tampilan adalah pompaan layout dari item list_item
            v = vi.inflate(R.layout.list_item, null);
        }

        DataTamuModel p = getItem(position);

        if (p != null) {
            TextView tt1 = v.findViewById(R.id.id);
            TextView tt2 = v.findViewById(R.id.name);
            TextView tt3 = v.findViewById(R.id.noHpGet);
            TextView tt4 = v.findViewById(R.id.instansiGet);
           // TextView tt3 = v.findViewById(R.id.kartu);
           //ini kalau ngambilnya gambar
            // ImageView view = v.findViewById(R.id.img);

/*
            if(view != null){
//                view.setImageResource(p.getImg());
  //              Picasso.get().load(p.getImg()).into(view);
            }
*/
            if (tt1 != null) {
                tt1.setText(p.getKartu());
            }

            if (tt2 != null) {
                tt2.setText(p.getNama());
            }
            if (tt2 != null) {
                tt3.setText(p.getNoHp());
            }
            if (tt2 != null) {
                tt4.setText(p.getInstansi());
            }
        }

        return v;
    }


}