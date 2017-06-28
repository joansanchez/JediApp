package joansanchez.jediapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Calculadora extends AppCompatActivity {

    private static final String TAG = "CALCULADORA";
    Button but1, but2, but3, but4, but5, but6, but7, but8, but9, but0;
    Button butadd, butless, butmult, butdiv, butans, butdec, butbrow, butphone, butequ;
    TextView texto;

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
    }
}
