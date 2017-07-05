package joansanchez.jediapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import joansanchez.jediapp.database.MyDataBaseHelper;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class LoginActivity extends AppCompatActivity  {

    private TextView user;
    private TextView pass;
    private Button login, register;
    private String u, p;
    SharedPreferences sp;

    private final String TAG = "LoginActivity";
    private MyDataBaseHelper myDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (TextView) findViewById(R.id.editText);
        pass = (TextView) findViewById(R.id.editText2);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.button);


        myDataBaseHelper = MyDataBaseHelper.getInstance(this);
        sp = getSharedPreferences("APP", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        final Intent i = new Intent(this, DrawerActivity.class);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = user.getText().toString();
                p = pass.getText().toString();

                if (u.length() != 0 && p.length() != 0){
                    long id = myDataBaseHelper.createRow(u, p);
                    if (id != -1) {
                        editor.putString("currentUser", u);
                        editor.apply();
                        startActivity(i);
                    }
                    Log.v(TAG, ""+id);
                }
                else Log.v(TAG, "introduce usuario y pass");
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = user.getText().toString();
                p = pass.getText().toString();
                if (u.length() != 0 && p.length() != 0){
                    long id = myDataBaseHelper.query(u, p);
                    if (id != -1) {
                        editor.putString("currentUser", u);
                        editor.apply();
                        String actual = sp.getString("currentUser",null);
                        Log.v(TAG, actual);
                        startActivity(i);
                    }
                    //enviar a la pantalla de error
                    Log.v(TAG, ""+id);
                }
                else Log.v(TAG, "introduce usuario y pass");
                }

        });
    }
}






















