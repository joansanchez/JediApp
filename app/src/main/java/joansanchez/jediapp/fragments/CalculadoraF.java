package joansanchez.jediapp.fragments;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import joansanchez.jediapp.DrawerActivity;
import joansanchez.jediapp.LoginActivity;
import joansanchez.jediapp.R;
import joansanchez.jediapp.database.MyDataBaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculadoraF extends Fragment implements View.OnClickListener{

    private static final String TAG = "CALCULADORA";
    Button but1, but2, but3, but4, but5, but6, but7, but8, but9, but0;
    Button butadd, butless, butmult, butdiv, butans, butdec, butequ;
    TextView texto;
    private String result1, result2, result; //eresult1 guarda operando1, result2, el 2 y result el final
    private int op1, operand; //op1 sirve para saber si estamos en el primer operando o en el segundo y operand es el signo de la operación
    private Boolean iniciop1, iniciop2; //Sirve para saber si es el primer número que se inserta del operando
    private void setText(String text){
        texto.setText(text);
    }
    ImageButton butbrow, butphone;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String notiactualactual;
    private MyDataBaseHelper myDataBaseHelper;
    String user;

    private void notifierror(String noti){

        myDataBaseHelper.updatenoti(noti, user);
        switch (notiactualactual){
            case "toast":
                Toast.makeText(this.getActivity(), noti, Toast.LENGTH_LONG).show();
                break;
            case "estado":
                //Entero que nos permite identificar la notificación
                int mId = 1;
                //Instanciamos Notification Manager
                NotificationManager mNotificationManager =
                        (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);


                // Para la notificaciones, en lugar de crearlas directamente, lo hacemos mediante
                // un Builder/contructor.
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getActivity().getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Calculadora")
                                .setContentText(noti);


                // Creamos un intent explicito, para abrir la app desde nuestra notificación
                Intent resultIntent = new Intent(getActivity().getApplicationContext(), DrawerActivity.class);

                //El objeto stack builder contiene una pila artificial para la Acitivty empezada.
                //De esta manera, aseguramos que al navegar hacia atrás
                //desde la Activity nos lleve a la home screen.

                //Desde donde la creamos
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity().getApplicationContext());
                // Añade la pila para el Intent,pero no el intent en sí
                stackBuilder.addParentStack(DrawerActivity.class);
                // Añadimos el intent que empieza la activity que está en el top de la pila
                stackBuilder.addNextIntent(resultIntent);

                //El pending intent será el que se ejecute cuando la notificación sea pulsada
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);

                // mId nos permite actualizar las notificaciones en un futuro
                // Notificamos
                mNotificationManager.notify(mId, mBuilder.build());
                break;

        }
    }

    private String calcularexpre(String result1, int op, String result2){
        double a, b, c;
        a = Double.valueOf(result1);
        b = Double.valueOf(result2);
        c = 0; //el caso 4 es el comodín que indica que no se ha usado en esta operación
        switch (op){
            case 0:
                c = a + b;
                break;
            case 1:
                c = a - b;
                break;
            case 2:
                c = a * b;
                break;
            case 3:
                if (b == 0) notifierror(getString(R.string.error_div_0));
                else c = a / b;
                break;
        }
        return String.valueOf(c);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDataBaseHelper = MyDataBaseHelper.getInstance(getActivity());
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menucalc, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_call:
                Intent i =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+result1));
                startActivity(i);
                break;
            case R.id.action_browse:
                Intent i2;
                if (result != "00") {
                    i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.es/search?q=" + result1));
                    startActivity(i2);
                }
                else{
                    i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lmgtfy.com/?q=qu%C3%A9+hago+en+julio+haciendo+un+curso?" ));
                    startActivity(i2);
                }
                break;
            case R.id.Notifica_toast:
                myDataBaseHelper.updatetiponoti("toast", user);
                notiactualactual = "toast";
                break;
            case R.id.Notifica_estado:
                myDataBaseHelper.updatetiponoti("estado", user);
                notiactualactual = "estado";
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO cambiar el menu de setting para tema notificaciones
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calculadora, container, false);
        but0 = (Button) rootView.findViewById(R.id.b0);
        but1 = (Button) rootView.findViewById(R.id.b1);
        but2 = (Button) rootView.findViewById(R.id.b2);
        but3 = (Button) rootView.findViewById(R.id.b3);
        but4 = (Button) rootView.findViewById(R.id.b4);
        but5 = (Button) rootView.findViewById(R.id.b5);
        but6 = (Button) rootView.findViewById(R.id.b6);
        but7 = (Button) rootView.findViewById(R.id.b7);
        but8 = (Button) rootView.findViewById(R.id.b8);
        but9 = (Button) rootView.findViewById(R.id.b9);
        butadd = (Button) rootView.findViewById(R.id.bmas);
        butless = (Button) rootView.findViewById(R.id.bmenos);
        butmult = (Button) rootView.findViewById(R.id.bmult);
        butdiv = (Button) rootView.findViewById(R.id.bdiv);
        butans = (Button) rootView.findViewById(R.id.bans);
        butdec = (Button) rootView.findViewById(R.id.bdec);
        butbrow = (ImageButton) rootView.findViewById(R.id.bbrowse);
        butphone = (ImageButton) rootView.findViewById(R.id.bphone);
        butequ = (Button) rootView.findViewById(R.id.bequ);
        texto = (TextView) rootView.findViewById(R.id.pantalla);


        but0.setOnClickListener(this);
        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);
        but4.setOnClickListener(this);
        but5.setOnClickListener(this);
        but6.setOnClickListener(this);
        but7.setOnClickListener(this);
        but8.setOnClickListener(this);
        but9.setOnClickListener(this);
        butadd.setOnClickListener(this);
        butless.setOnClickListener(this);
        butmult.setOnClickListener(this);
        butdiv.setOnClickListener(this);
        butans.setOnClickListener(this);
        butdec.setOnClickListener(this);
        butbrow.setOnClickListener(this);
        butphone.setOnClickListener(this);
        butequ.setOnClickListener(this);
        iniciop1 = iniciop2 = true;
        operand = 4;
        result1 = "0";
        result = "00";
        sp = this.getActivity().getSharedPreferences("APP", Context.MODE_PRIVATE);
        editor = sp.edit();
        user = sp.getString("currentUser","no name");
        notiactualactual = myDataBaseHelper.tipodenoti(user);

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            texto.setTextSize(70);
        } else {
            texto.setTextSize(40);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b1:
                Log.v(TAG, "Soy el Button 1");
                if(op1 != 1){
                    if (iniciop1) {
                        result1 = "1";
                        iniciop1 = false;
                    }
                    else result1 += "1";
                    setText(result1);
                }
                else{
                    if (iniciop2){
                        result2 = "1";
                        iniciop2 = false;
                    }
                    else result2 += 1;
                    setText(result2);
                }

                break;

            case R.id.b2:
                Log.v(TAG, "Soy el Button 2");
                if(op1 != 1){
                    if (iniciop1) {
                        result1 = "2";
                        iniciop1 = false;
                    }
                    else result1 += "2";
                    setText(result1);
                }
                else{
                    if (iniciop2){
                        result2 = "2";
                        iniciop2 = false;
                    }
                    else result2 += "2";
                    setText(result2);
                }

                break;

            case R.id.b3:
                Log.v(TAG, "Soy el Button 3");
                if(op1 != 1){
                    if (iniciop1) {
                        result1 = "3";
                        iniciop1 = false;
                    }
                    else result1 += "3";
                    setText(result1);
                }
                else{
                    if (iniciop2){
                        result2 = "3";
                        iniciop2 = false;
                    }
                    else result2 += "3";
                    setText(result2);
                }

                break;
            case R.id.b4:
                Log.v(TAG, "Soy el Button 4");
                if(op1 != 1){
                    if (iniciop1) {
                        result1 = "4";
                        iniciop1 = false;
                    }
                    else result1 += "4";
                    setText(result1);
                }
                else{
                    if (iniciop2){
                        result2 = "4";
                        iniciop2 = false;
                    }
                    else result2 += "4";
                    setText(result2);
                }
                break;
            case R.id.b5:
                Log.v(TAG, "Soy el Button 5");
                if(op1 != 1){
                    if (iniciop1) {
                        result1 = "5";
                        iniciop1 = false;
                    }
                    else result1 += "5";
                    setText(result1);
                }
                else{
                    if (iniciop2){
                        result2 = "5";
                        iniciop2 = false;
                    }
                    else result2 += "5";
                    setText(result2);
                }

                break;
            case R.id.b6:
                Log.v(TAG, "Soy el Button 6");
                if(op1 != 1){
                    if (iniciop1) {
                        result1 = "6";
                        iniciop1 = false;
                    }
                    else result1 += "6";
                    setText(result1);
                }
                else{
                    if (iniciop2){
                        result2 = "6";
                        iniciop2 = false;
                    }
                    else result2 += "6";
                    setText(result2);
                }

                break;
            case R.id.b7:
                Log.v(TAG, "Soy el Button 7");
                if(op1 != 1){
                    if (iniciop1) {
                        result1 = "7";
                        iniciop1 = false;
                    }
                    else result1 += "7";
                    setText(result1);
                }
                else{
                    if (iniciop2){
                        result2 = "7";
                        iniciop2 = false;
                    }
                    else result2 += "7";
                    setText(result2);
                }

                break;
            case R.id.b8:
                Log.v(TAG, "Soy el Button 8");
                if(op1 != 1){
                    if (iniciop1) {
                        result1 = "8";
                        iniciop1 = false;
                    }
                    else result1 += "8";
                    setText(result1);
                }
                else{
                    if (iniciop2){
                        result2 = "8";
                        iniciop2 = false;
                    }
                    else result2 += "8";
                    setText(result2);
                }

                break;
            case R.id.b9:
                Log.v(TAG, "Soy el Button 9");
                if(op1 != 1){
                    if (iniciop1) {
                        result1 = "9";
                        iniciop1 = false;
                    }
                    else result1 += "9";
                    setText(result1);
                }
                else{
                    if (iniciop2){
                        result2 = "9";
                        iniciop2 = false;
                    }
                    else result2 += "9";
                    setText(result2);
                }

                break;
            case R.id.b0:
                Log.v(TAG, "Soy el Button 0");
                if(op1 != 1){
                    if (iniciop1) {
                        result1 = "0";
                        iniciop1 = false;
                    }
                    else result1 += "0";
                    setText(result1);
                }
                else{
                    if (iniciop2){
                        result2 = "0";
                        iniciop2 = false;
                    }
                    else result2 += "0";
                    setText(result2);
                }

                break;

            case R.id.bmas:
                Log.v(TAG, "Soy el Button mas");
                op1 = 1;
                iniciop2 = true;
                operand = 0;
                setText("+");
                break;

            case R.id.bmenos:
                Log.v(TAG, "Soy el Button menos");
                op1 = 1;
                iniciop2 = true;
                operand = 1;
                setText("-");
                break;

            case R.id.bmult:
                Log.v(TAG, "Soy el Button mult");
                op1 = 1;
                iniciop2 = true;
                operand = 2;
                setText("*");
                break;

            case R.id.bdiv:
                Log.v(TAG, "Soy el Button div");
                op1 = 1;
                iniciop2 = true;
                operand = 3;
                setText("/");
                break;

            case R.id.bequ:
                Log.v(TAG, "Soy el Button =");
                if (!iniciop2) result = calcularexpre(result1, operand, result2);
                //else result = result1;
                setText(result);
                op1 = 0;
                result1 = result;
                result2 = "";
                result = "00";
                iniciop1 = iniciop2 = true;

                operand = 4;
                Log.v(TAG, "res1 "+result1);
                Log.v(TAG, "res2 "+result2);
                Log.v(TAG, "res "+result);
                break;
            case R.id.bans:
                Log.v(TAG, "Soy el Button ans");
                if(op1 != 1){
                    result1 = result;
                    setText(result1);
                }
                else {
                    result2 = result;
                    setText(result2);
                }
                break;
            case R.id.bphone:
                Intent i =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+result1));
                startActivity(i);
                break;
            case R.id.bbrowse:
                Intent i2;
                if (result != "00") {
                    i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.es/search?q=" + result1));
                    startActivity(i2);
                }
                else{
                    i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lmgtfy.com/?q=qu%C3%A9+hago+en+julio+haciendo+un+curso" ));
                    startActivity(i2);
                }
                break;
            case R.id.bdec:
                Log.v(TAG, "Soy el Button decimal");
                if(op1 != 1){
                    if (iniciop1) {
                        result1 = "0.";
                        iniciop1 = false;
                    }
                    else result1 += ".";
                    setText(result1);
                }
                else{
                    if (iniciop2){
                        result2 = "0.";
                        iniciop2 = false;
                    }
                    else result2 += ".";
                    setText(result2);
                }
                break;
        }
    }


}
