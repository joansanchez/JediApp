package joansanchez.jediapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import joansanchez.jediapp.database.MyDataBaseHelper;
import joansanchez.jediapp.fragments.ErrorActivity;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView user;
    private TextView pass;
    private Button login ;
    private Button register;
    private String u, p;
    //Shared preferences
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private final String TAG = "LoginActivity123";
    private MyDataBaseHelper myDataBaseHelper;
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton mSignInButton;
    Intent i;
    Intent e;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (TextView) findViewById(R.id.editText);
        pass = (TextView) findViewById(R.id.editText2);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        register = (Button) findViewById(R.id.button);
        register.setOnClickListener(this);
        initGoogleLogin();
        initUIComponents();
        myDataBaseHelper = MyDataBaseHelper.getInstance(this);
        sp = getSharedPreferences("APP", Context.MODE_PRIVATE);
        editor = sp.edit();


    }

    private void initGoogleLogin() {
        //https://developers.google.com/identity/sign-in/android/sign-in

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(),"Connection failed",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    } //se conecta con google y inicializa la api

    private void initUIComponents(){
        mSignInButton = (SignInButton) findViewById(R.id.googlebutton);
        mSignInButton.setOnClickListener(this);
    } //inicializa el botón de google sign in

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.googlebutton:
                signInGoogle();
                break;
            case R.id.button: //botón registro
                u = user.getText().toString();
                p = pass.getText().toString();

                if (u.length() != 0 && p.length() != 0){
                    long id = myDataBaseHelper.createRow(u, p);
                    if (id != -1) {
                        editor.putString("currentUser", u);
                        editor.apply();
                        i = new Intent(this, DrawerActivity.class);
                        startActivity(i);
                    }
                    else Toast.makeText(this, "usuario ya en uso", Toast.LENGTH_LONG).show();
                    Log.v(TAG, ""+id);
                }
                else Log.v(TAG, "introduce usuario y pass");
                break;
            case R.id.login:
                u = user.getText().toString();
                p = pass.getText().toString();
                if (u.length() != 0 && p.length() != 0){
                    long id = myDataBaseHelper.query(u, p);
                    if (id != -1) {
                        editor.putString("currentUser", u);
                        editor.apply();
                        i = new Intent(this, DrawerActivity.class);
                        startActivity(i);
                    }
                    else {
                        e =  new Intent(this, ErrorActivity.class);
                        startActivity(e);
                    }
                }
                else Log.v(TAG, "introduce usuario y pass");
                break;
        }
        String actual = sp.getString("currentUser","no user");
        Log.v(TAG, actual);
    }

    private void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String user = acct.getEmail();
            Log.v(TAG, user);
            if (MyDataBaseHelper.existe(user) != -1){
                editor.putString("currentUser", user);
                editor.apply();
                i = new Intent(this, DrawerActivity.class);
                startActivity(i);
            }
            else{
                long id = MyDataBaseHelper.createRowGoogle(user);
                if (id != -1) {
                    editor.putString("currentUser", user);
                    editor.apply();
                    i = new Intent(this, DrawerActivity.class);
                    startActivity(i);
                }
            }
        } else {
            // Signed out, show unauthenticated UI.
        }
    }


}






















