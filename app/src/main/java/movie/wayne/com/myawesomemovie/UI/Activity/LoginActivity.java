package movie.wayne.com.myawesomemovie.UI.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import movie.wayne.com.myawesomemovie.Firebase.Database;
import movie.wayne.com.myawesomemovie.Firebase.DatabaseCallback;
import movie.wayne.com.myawesomemovie.Model.Account;
import movie.wayne.com.myawesomemovie.Model.FavoriteMovie;
import movie.wayne.com.myawesomemovie.Model.Genre;
import movie.wayne.com.myawesomemovie.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {
    public EditText mEditUsername;
    public EditText mEditPassword;
    public Button mBtnLogin;
    public CheckBox mCheckShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEditUsername = findViewById(R.id.edit_username);
        mEditPassword = findViewById(R.id.edit_password);
        mBtnLogin = findViewById(R.id.btn_sign_in);
        mBtnLogin.setOnClickListener(this);
        testDatabase();
        mCheckShowPassword = findViewById(R.id.check_show_password);
        mCheckShowPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckShowPassword.isChecked())
                    mEditPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                else
                    mEditPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        setTitle("LOGIN");
    }

    public void testDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Account");
        Account std_1 = new Account("Tri", "1234", "1234");
        Account std_2 = new Account("Ha", "1111", "0987");
        myRef.child("1234567890").setValue(std_1);
        myRef.child("0987654321").setValue(std_2);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in: {
                login();
                break;
            }
        }
    }

    private void login() {
        mBtnLogin.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Progressing...");
        progressDialog.show();
        final String sStudentID = mEditUsername.getText().toString();
        final String sPassword = mEditPassword.getText().toString();
        Database db = new Database();
        db.searchInDatabase(sStudentID, sPassword, new DatabaseCallback() {
            @Override
            public void onUserSuccess(Account value) {
                onLoginSuccess();
                progressDialog.dismiss();
            }


            @Override
            public void onFail(String message) {
                onLoginFailed(message);
                progressDialog.dismiss();
            }

            @Override
            public void onGenresSuccess(ArrayList<Genre> genresList) {

            }
        });
    }

    public void onLoginSuccess() {
        // TODO: move to next Activity
        Toast.makeText(getBaseContext(), "Congrat, Login Success", Toast.LENGTH_LONG).show();
        mBtnLogin.setEnabled(true);
        finish();
        //startActivity(TrailerListActivity.getInstance(this, mResult));

        Intent intent = new Intent(this, TrueMainActivity.class);
        intent.putExtra("userID", mEditUsername.getText().toString());
        LoginActivity.this.startActivity(intent);
    }

    public void onLoginFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        mBtnLogin.setEnabled(true);
    }
}

