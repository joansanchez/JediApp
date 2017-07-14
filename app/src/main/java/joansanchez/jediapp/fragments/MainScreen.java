package joansanchez.jediapp.fragments;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import joansanchez.jediapp.PermissionUtils;
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
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_MANAGE_DOCUMENTS = 2;
    private boolean canWeRead = false;

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
        PermissionUtils.checkReadExternalStoragePermissions(getActivity(), MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        canWeRead = canWeRead();
        if(canWeRead) {
            String aux2 = myDataBaseHelper.getphoto(nomuse);
            if (aux2 != "no photo") {
                Uri imageUri = Uri.parse(aux2);
                try {
                    Log.v(TAG, "hasta aquí llego");
                    foto.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
    private boolean canWeRead() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED;
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    canWeRead = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    canWeRead = false;
                }
                return;
            }
            case  MY_PERMISSIONS_REQUEST_MANAGE_DOCUMENTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    canWeRead = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    canWeRead = false;
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
