package joansanchez.jediapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Calculadora extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "CALCULADORA";
    Button but1, but2, but3, but4, but5, but6, but7, but8, but9, but0;
    Button butadd, butless, butmult, butdiv, butans, butdec, butbrow, butphone, butequ;
    TextView texto;
    private String result1, result2, result; //eresult1 guarda operando1, result2, el 2 y result el final
    private int op1, operand; //op1 sirve para saber si estamos en el primer operando o en el segundo y operand es el signo de la operación
    private Boolean iniciop1, iniciop2; //Sirve para saber si es el primer número que se inserta del operando
    private void setText(String text){
        texto.setText(text);
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
                c = a / b;
                break;
        }
        return String.valueOf(c);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        but0 = (Button) findViewById(R.id.b0);
        but1 = (Button) findViewById(R.id.b1);
        but2 = (Button) findViewById(R.id.b2);
        but3 = (Button) findViewById(R.id.b3);
        but4 = (Button) findViewById(R.id.b4);
        but5 = (Button) findViewById(R.id.b5);
        but6 = (Button) findViewById(R.id.b6);
        but7 = (Button) findViewById(R.id.b7);
        but8 = (Button) findViewById(R.id.b8);
        but9 = (Button) findViewById(R.id.b9);
        butadd = (Button) findViewById(R.id.bmas);
        butless = (Button) findViewById(R.id.bmenos);
        butmult = (Button) findViewById(R.id.bmult);
        butdiv = (Button) findViewById(R.id.bdiv);
        butans = (Button) findViewById(R.id.bans);
        butdec = (Button) findViewById(R.id.bdec);
        butbrow = (Button) findViewById(R.id.bbrowse);
        butphone = (Button) findViewById(R.id.bphone);
        butequ = (Button) findViewById(R.id.bequ);
        texto = (TextView) findViewById(R.id.pantalla);

        final Context context = this;
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
    }

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
                result = calcularexpre(result1, operand, result2);
                setText(result);
                op1 = 0;
                result1 = result2 = "";
                iniciop1 = true;
                operand = 4;
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
                Intent i2 =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.es/search?q="+result1));
                startActivity(i2);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG,"Se ha llamado onSaveInstanceState");
        outState.putInt("op1", op1);
        outState.putInt("operand", operand);
        outState.putBoolean("inicioop1", iniciop1);
        outState.putBoolean("inicioop2", iniciop2);
        outState.putString("result1", result1);
        if (op1 == 1) outState.putString("result2", result2);
        outState.putString("result", result);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(TAG,"Se ha llamado onRestore");
        result = savedInstanceState.getString("result");
        result1 = savedInstanceState.getString("result1");
        op1 = savedInstanceState.getInt("op1");
        if (op1 == 1) result2 = savedInstanceState.getString("result2");
        iniciop1 = savedInstanceState.getBoolean("inicioop1");
        iniciop2 = savedInstanceState.getBoolean("inicioop2");
        operand = savedInstanceState.getInt("operand");
        Log.v(TAG,"El valor del operand es "+String.valueOf(operand));
        if(op1 == 0 && operand == 4) setText(result1);
    }
}
