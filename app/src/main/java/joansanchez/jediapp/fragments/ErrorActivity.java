package joansanchez.jediapp.fragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import joansanchez.jediapp.DrawerActivity;
import joansanchez.jediapp.LoginActivity;
import joansanchez.jediapp.R;

public class ErrorActivity extends AppCompatActivity {
    Button volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        volver = (Button) findViewById(R.id.volver);
        final Intent i = new Intent(this, LoginActivity.class);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

    }

}
