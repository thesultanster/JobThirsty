package cs.software.engineering.jobthirsty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import cs.software.engineering.jobthirsty.profile.EmployeeProfileActivity;

public class Login extends AppCompatActivity {

    EditText username;
    EditText password;


    Button login;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null){
            Intent intent = new Intent(this, EmployeeProfileActivity.class);
            startActivity(intent);
        }


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {



                ParseUser.logInInBackground(username.getText().toString(),  password.getText().toString(), new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {

                            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                            installation.put("userId", ParseUser.getCurrentUser().getObjectId());
                            installation.saveInBackground();

                            // If user exist and authenticated, send user to Welcome.class
                            Intent intent = new Intent(Login.this, EmployeeProfileActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Successfully Logged in", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    e.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }

        });


        signUp = (Button) findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });




    }
}
