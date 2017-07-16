package joansanchez.jediapp.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.material.joanbarroso.flipper.CoolImageFlipper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import joansanchez.jediapp.R;
import joansanchez.jediapp.database.MyDataBaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Juego extends Fragment implements View.OnClickListener {
    private static final String TAG = "JUEGO";
    private MyDataBaseHelper myDataBaseHelper;
    String nomuse;

    CoolImageFlipper flipper;
    ImageView im11, im21, im31, im41, im12, im22, im32, im42, im13, im23, im33, im43, im14, im24, im34, im44;
    TextView contador;
    Integer contadorint;
    private  Integer[] rutaimagen = {R.drawable.ic_banana, R.drawable.ic_banana, R.drawable.ic_burger, R.drawable.ic_burger, R.drawable.ic_chocolate, R.drawable.ic_chocolate, R.drawable.ic_french_fries, R.drawable.ic_french_fries, R.drawable.ic_gingerbread_man, R.drawable.ic_gingerbread_man, R.drawable.ic_pie, R.drawable.ic_pie, R.drawable.ic_rice, R.drawable.ic_rice, R.drawable.ic_taco, R.drawable.ic_taco};
    private List<Integer> listarutaimagen = Arrays.asList(rutaimagen);
    private Integer cartabase = R.drawable.ic_watermelon;
    private Integer cartas = 0; //sirve para ver si ya hemos escogido dos cartas
    private Integer pares = 8; //sirve para parar la partida cuando no queden más cartas

    ImageView imavi1 = null; //se guarda la carta 1
    ImageView imavi2 = null; //se guarda la carta 2

    boolean bloqueo = false;
    //boolean isBurger = true; //

    public Juego() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDataBaseHelper = MyDataBaseHelper.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        flipper = new CoolImageFlipper(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_juego, container, false);
        im11 = (ImageView) rootView.findViewById(R.id.im11);
        im12 = (ImageView) rootView.findViewById(R.id.im12);
        im13 = (ImageView) rootView.findViewById(R.id.im13);
        im14 = (ImageView) rootView.findViewById(R.id.im14);
        im21 = (ImageView) rootView.findViewById(R.id.im21);
        im22 = (ImageView) rootView.findViewById(R.id.im22);
        im23 = (ImageView) rootView.findViewById(R.id.im23);
        im24 = (ImageView) rootView.findViewById(R.id.im24);
        im31 = (ImageView) rootView.findViewById(R.id.im31);
        im32 = (ImageView) rootView.findViewById(R.id.im32);
        im33 = (ImageView) rootView.findViewById(R.id.im33);
        im34 = (ImageView) rootView.findViewById(R.id.im34);
        im41 = (ImageView) rootView.findViewById(R.id.im41);
        im42 = (ImageView) rootView.findViewById(R.id.im42);
        im43 = (ImageView) rootView.findViewById(R.id.im43);
        im44 = (ImageView) rootView.findViewById(R.id.im44);
        contador = (TextView) rootView.findViewById(R.id.intentosjugador);
        contador.setText("0");
        contadorint = 0;


        im11.setOnClickListener(this);
        im12.setOnClickListener(this);
        im13.setOnClickListener(this);
        im14.setOnClickListener(this);
        im21.setOnClickListener(this);
        im22.setOnClickListener(this);
        im23.setOnClickListener(this);
        im24.setOnClickListener(this);
        im31.setOnClickListener(this);
        im32.setOnClickListener(this);
        im33.setOnClickListener(this);
        im34.setOnClickListener(this);
        im41.setOnClickListener(this);
        im42.setOnClickListener(this);
        im43.setOnClickListener(this);
        im44.setOnClickListener(this);

        Collections.shuffle(listarutaimagen);
        rutaimagen = listarutaimagen.toArray(new Integer[listarutaimagen.size()]);

        im11.setTag(rutaimagen[0]);
        im12.setTag(rutaimagen[1]);
        im13.setTag(rutaimagen[2]);
        im14.setTag(rutaimagen[3]);
        im21.setTag(rutaimagen[4]);
        im22.setTag(rutaimagen[5]);
        im23.setTag(rutaimagen[6]);
        im24.setTag(rutaimagen[7]);
        im31.setTag(rutaimagen[8]);
        im32.setTag(rutaimagen[9]);
        im33.setTag(rutaimagen[10]);
        im34.setTag(rutaimagen[11]);
        im41.setTag(rutaimagen[12]);
        im42.setTag(rutaimagen[13]);
        im43.setTag(rutaimagen[14]);
        im44.setTag(rutaimagen[15]);

        SharedPreferences sp = this.getActivity().getSharedPreferences("APP", Context.MODE_PRIVATE);
        nomuse = sp.getString("currentUser","no name");

        setHasOptionsMenu(true);

        return rootView;

    }

    @Override
    public void onClick(View view) {
       if (!bloqueo) {
           switch (view.getId()) {
               case R.id.im11:
                   if (imavi1 != im11 && imavi2!=im11){
                       Drawable car1 = getResources().getDrawable(rutaimagen[0]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im11;
                           ++cartas;
                       }
                       else{
                           imavi2 = im11;
                           ++cartas;
                       }

                   }
                   break;

               case R.id.im12:
                   if (imavi1 != im12 && imavi2!=im12){
                       Drawable car1 = getResources().getDrawable(rutaimagen[1]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im12;
                           ++cartas;
                       }
                       else{
                           imavi2 = im12;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im13:
                   if (imavi1 != im13 && imavi2!=im13){
                       Drawable car1 = getResources().getDrawable(rutaimagen[2]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im13;
                           ++cartas;
                       }
                       else{
                           imavi2 = im13;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im14:
                   if (imavi1 != im14 && imavi2!=im14){
                       Drawable car1 = getResources().getDrawable(rutaimagen[3]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im14;
                           ++cartas;
                       }
                       else{
                           imavi2 = im14;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im21:
                   if (imavi1 != im21 && imavi2!=im21){
                       Drawable car1 = getResources().getDrawable(rutaimagen[4]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im21;
                           ++cartas;
                       }
                       else{
                           imavi2 = im21;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im22:
                   if (imavi1 != im22 && imavi2!=im22){
                       Drawable car1 = getResources().getDrawable(rutaimagen[5]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im22;
                           ++cartas;
                       }
                       else{
                           imavi2 = im22;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im23:
                   if (imavi1 != im23 && imavi2!=im23){
                       Drawable car1 = getResources().getDrawable(rutaimagen[6]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im23;
                           ++cartas;
                       }
                       else{
                           imavi2 = im23;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im24:
                   if (imavi1 != im24 && imavi2!=im24){
                       Drawable car1 = getResources().getDrawable(rutaimagen[7]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im24;
                           ++cartas;
                       }
                       else{
                           imavi2 = im24;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im31:
                   if (imavi1 != im31 && imavi2!=im31){
                       Drawable car1 = getResources().getDrawable(rutaimagen[8]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im31;
                           ++cartas;
                       }
                       else{
                           imavi2 = im31;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im32:
                   if (imavi1 != im32 && imavi2!=im32){
                       Drawable car1 = getResources().getDrawable(rutaimagen[9]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im32;
                           ++cartas;
                       }
                       else{
                           imavi2 = im32;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im33:
                   if (imavi1 != im33 && imavi2!=im33){
                       Drawable car1 = getResources().getDrawable(rutaimagen[10]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im33;
                           ++cartas;
                       }
                       else{
                           imavi2 = im33;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im34:
                   if (imavi1 != im34 && imavi2!=im34){
                       Drawable car1 = getResources().getDrawable(rutaimagen[11]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im34;
                           ++cartas;
                       }
                       else{
                           imavi2 = im34;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im41:
                   if (imavi1 != im41 && imavi2!=im41){
                       Drawable car1 = getResources().getDrawable(rutaimagen[12]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im41;
                           ++cartas;
                       }
                       else{
                           imavi2 = im41;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im42:
                   if (imavi1 != im42 && imavi2!=im42){
                       Drawable car1 = getResources().getDrawable(rutaimagen[13]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im42;
                           ++cartas;
                       }
                       else{
                           imavi2 = im42;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im43:
                   if (imavi1 != im43 && imavi2!=im43){
                       Drawable car1 = getResources().getDrawable(rutaimagen[14]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im43;
                           ++cartas;
                       }
                       else{
                           imavi2 = im43;
                           ++cartas;
                       }

                   }
                   break;
               case R.id.im44:
                   if (imavi1 != im44 && imavi2!=im44){
                       Drawable car1 = getResources().getDrawable(rutaimagen[15]);
                       flipper.flipImage(car1, ((ImageView) view));
                       if(cartas == 0){ //se escoge la primera
                           imavi1 = im44;
                           ++cartas;
                       }
                       else{
                           imavi2 = im44;
                           ++cartas;
                       }

                   }
                   break;
           }
           if(cartas == 2){
               bloqueo = true;
               ++contadorint;
               contador.setText(String.valueOf(contadorint));
               cartas = 0;
               Log.v(TAG, "entraaaaaaamos");
               if (imavi1.getTag().toString().equals(imavi2.getTag().toString())){
                   Log.v(TAG, "dentro de la igualdad");
                   --pares;
                   if(pares == 0) findepartida();
                   Thread thread = new Thread() {
                       @Override
                       public void run() {
                           try {

                               Thread.sleep(2000);
                               bloqueo = false;

                           } catch (InterruptedException e) {
                           }
                           getActivity().runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   imavi1.setVisibility(View.GONE);
                                   imavi2.setVisibility(View.GONE);
                                   imavi1 = imavi2 = null;
                               }
                           });

                       }
                   };
                   thread.start();


               }
               else{
                   Thread thread = new Thread() {
                       @Override
                       public void run() {
                           try {

                               Thread.sleep(1000);
                               bloqueo = false;

                           } catch (InterruptedException e) {
                           }
                           getActivity().runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   Drawable car1 = getResources().getDrawable(cartabase);
                                   flipper.flipImage(car1, (imavi1));
                                   flipper.flipImage(car1, (imavi2));
                                   imavi1 = imavi2 = null;
                               }
                           });

                       }
                   };
                   thread.start();
               }


           }
       }
       /* Drawable car1 = getResources().getDrawable(rutaimagen[0]);
                   Drawable car2 = getResources().getDrawable(cartabase);


                   imavi1 = im11;
                   imavi2 = im12;

                   if (imavi1.getTag().toString().equals(imavi2.getTag().toString()))
                       Log.v(TAG, "igualessss");
                   Drawable car2 = getResources().getDrawable(cartabase);
                   if (isBurger)
                       flipper.flipImage(car1, ((ImageView) view));
                   else
                       flipper.flipImage(car2, ((ImageView) view));

                   isBurger = !isBurger;*/

    }

    private void findepartida() {
        Log.v(TAG, "findepartida");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("partida finalizada. Puntuación "+contadorint)
                .setTitle("Memory");
        AlertDialog dialog = builder.create();
        dialog.show();
        String aux = myDataBaseHelper.getpoints(nomuse);
        if (aux != "no games"){
            if (Integer.valueOf(aux) > contadorint) myDataBaseHelper.updatepoints(nomuse, String.valueOf(contadorint));
        }
        else myDataBaseHelper.updatepoints(nomuse, String.valueOf(contadorint));
        bloqueo = true;

        Fragment f = new Juego();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, f);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menujuego, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_restart:
                Fragment f = new Juego();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, f);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

        }

        return super.onOptionsItemSelected(item);
    }


}

