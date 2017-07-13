package joansanchez.jediapp.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import joansanchez.jediapp.R;
import joansanchez.jediapp.database.MyDataBaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreen extends Fragment implements View.OnClickListener {
    ImageView foto;
    TextView nom, direccion, ciudad, bestpunt, ultimanoti;
    Button editprof;
    private MyDataBaseHelper myDataBaseHelper;
    private final String TAG = "MainScreen";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDataBaseHelper = MyDataBaseHelper.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
        foto = (ImageView) rootView.findViewById(R.id.fotoperfil);
        nom = (TextView) rootView.findViewById(R.id.nombreuser);
        direccion = (TextView) rootView.findViewById(R.id.direccionuser);
        ciudad = (TextView) rootView.findViewById(R.id.ciudaduser);
        bestpunt = (TextView) rootView.findViewById(R.id.bestpuntua);
        ultimanoti = (TextView) rootView.findViewById(R.id.lastnoti);
        editprof = (Button) rootView.findViewById(R.id.editprof);
        editprof.setOnClickListener(this);
        rellenardatos();
        return rootView;
    }

    private void rellenardatos() {
        SharedPreferences sp = this.getActivity().getSharedPreferences("APP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String nomuse = sp.getString("currentUser","no name");
        nom.setText(nomuse);
        String lastnot = myDataBaseHelper.lastnotifi(nomuse);
        ultimanoti.setText(lastnot);
        String address = myDataBaseHelper.direccion(nomuse);
        direccion.setText(address);
        String city = myDataBaseHelper.ciudad(nomuse);
        ciudad.setText(city);
        String puntua = myDataBaseHelper.bestpoints(nomuse);
        Log.v(TAG, puntua);
        bestpunt.setText(puntua);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editprof:
                Fragment f = new Profile();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, f);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }
}
