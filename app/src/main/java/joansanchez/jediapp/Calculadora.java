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
    private String result1, result2, result;
    private int op1, operand;
    private Boolean iniciop1, iniciop2;
    private void setText(String text){
        texto.setText(text);
    }

    private String calcularexpre(String result1, int op, String result2){
        double a, b, c;
        a = Double.valueOf(result1);
        b = Double.valueOf(result2);
        c = 0;
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
        }
    }
}
