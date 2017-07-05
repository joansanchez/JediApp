package joansanchez.jediapp;

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
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = user.getText().toString();
                String p = pass.getText().toString();
                if (u.length() != 0 && p.length() != 0){
                    long id = myDataBaseHelper.createRow(u, p);
                    Log.v(TAG, ""+id);
                }
                else Log.v(TAG, "introduce usuario y pass");
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}






















