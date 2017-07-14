package joansanchez.jediapp.fragments;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import joansanchez.jediapp.PermissionUtils;
import joansanchez.jediapp.R;
import joansanchez.jediapp.database.MyDataBaseHelper;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment implements View.OnClickListener {
    ImageView fotoperfil;
    EditText direccion, ciudad;
    Button buscarfoto, actualizar;
    private MyDataBaseHelper myDataBaseHelper;
    private final String TAG = "EditarPerfil";
    private Boolean newfoto = false;
    String nomuse;
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        fotoperfil = (ImageView) rootView.findViewById(R.id.ImagePerfil);
        direccion = (EditText) rootView.findViewById(R.id.editText3);
        ciudad = (EditText) rootView.findViewById(R.id.editText4);
        buscarfoto = (Button) rootView.findViewById(R.id.button2);
        buscarfoto.setOnClickListener(this);
        actualizar = (Button) rootView.findViewById(R.id.buttonupdate);
        actualizar.setOnClickListener(this);
        rellenardatos();
        canWeRead = canWeRead();
        if (canWeRead == false) Log.v(TAG, "no tengo permisos");
        if(canWeRead) {
            String aux = myDataBaseHelper.getphoto(nomuse);
            Log.v(TAG, aux);
            loadImageFromString(aux);
        }
        return rootView;
        
    }

    private void loadImageFromString(String imagePath) {
        if(imagePath != "no photo"){
            Uri imageUri = Uri.parse(imagePath);
            loadImageFromUri(imageUri);
        }
    }

    private void loadImageFromUri(Uri imageUri) {
        try {
            fotoperfil.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean canWeRead() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED;
    }
    private Intent getContentIntent() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT <19){
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        return intent;
    }

    private void rellenardatos() {
        SharedPreferences sp = this.getActivity().getSharedPreferences("APP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        nomuse = sp.getString("currentUser","no name");
        String direcaux = myDataBaseHelper.direccion(nomuse);
        direccion.setHint(direcaux);
        String city = myDataBaseHelper.ciudad(nomuse);
        ciudad.setHint(city);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Como en este caso los 3 intents hacen lo mismo, si el estado es correcto recogemos el resultado
        //Aún así comprobamos los request code. Hay que tener total control de lo que hace nuestra app.
        if(resultCode == RESULT_OK){
            if(requestCode >= 1 && requestCode <= 3){
                //Líneas extras debido al usar action get content:
                data.getData();
                Uri selectedImage = data.getData();
                String selectedImagePath = selectedImage.toString();

                if(canWeRead && requestCode == 2){
                    Log.v(TAG,"Selected image uri" + selectedImage);
                    myDataBaseHelper.updatephoto(nomuse, selectedImagePath);
                }
                Log.v(TAG, String.valueOf(selectedImage));
                loadImageFromUri(selectedImage);
            }
        }else{
            Log.v("Result","Something happened");
        }
    }

    @Override
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button2: //buscar nueva foto
                newfoto = true;
                PermissionUtils.checkReadExternalStoragePermissions(getActivity(),MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                Intent pickAnImage = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickAnImage.setType("image/*");

                startActivityForResult(pickAnImage, 2);
                break;
            case R.id.buttonupdate:
                String d = direccion.getText().toString();
                if (d.length() != 0) myDataBaseHelper.updatedireccion(nomuse, d);
                String c = ciudad.getText().toString();
                if(c.length() != 0) myDataBaseHelper.updatecity(nomuse, c);
                Fragment f = new MainScreen();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, f);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }

    }
}
