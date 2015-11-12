package cs.software.engineering.jobthirsty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SettingsActivity extends AppCompatActivity {

    EditText password;
    Button passwordButton;

    EditText email;
    Button emailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        /*
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Compose");

       */


        InflateVariables();
        SetClickListeners();
    }

    void InflateVariables(){
        password = (EditText) findViewById(R.id.password);
        passwordButton = (Button) findViewById(R.id.passwordChangeButton);
        email = (EditText) findViewById(R.id.email);
        emailButton = (Button) findViewById(R.id.emailChangeButton);
    }

    void SetClickListeners(){
        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeEmail();
            }
        });
    }

    void ChangePassword(){
        ParseUser parseUser = ParseUser.getCurrentUser();
        parseUser.setPassword(password.getText().toString());
        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (null == e) {
                    Toast.makeText(SettingsActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingsActivity.this, "Error: Password cannot be changed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void ChangeEmail(){
        ParseUser user = ParseUser.getCurrentUser();
        user.put("email",email.getText().toString());
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(SettingsActivity.this, "Email Changed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
