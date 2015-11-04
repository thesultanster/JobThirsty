package cs.software.engineering.jobthirsty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import cs.software.engineering.jobthirsty.profile.EmployeeProfileActivity;

public class SignUp extends AppCompatActivity {


    EditText username;
    EditText password;
    EditText firstName;
    EditText lastName;
    EditText email;
    Button signUp;
    ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // Inflate All Objects
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        signUp = (Button) findViewById(R.id.signUpButton);

        // Sign Up Button Listener
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create New Parse User
                user = new ParseUser();
                user.put("username", username.getText().toString());
                user.put("password", password.getText().toString());
                user.put("firstName", firstName.getText().toString());
                user.put("lastName", lastName.getText().toString());
                user.setEmail(email.getText().toString());

                // Sign Up Parse User
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {

                        // Login When Sign Up Done

                        try {
                            ParseUser.logIn(username.getText().toString(), password.getText().toString());

                            // Go to Main Page when successfully logged in
                            Intent intent = new Intent(SignUp.this, EmployeeProfileActivity.class);
                            intent.putExtra("firstname", ParseUser.getCurrentUser().get("firstname").toString());
                            intent.putExtra("lastname", ParseUser.getCurrentUser().get("lastname").toString());

                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Successfully Logged in", Toast.LENGTH_SHORT).show();
                            finish();

                        } catch (ParseException e1) {
                            e1.printStackTrace();
                            Toast.makeText( getApplicationContext(), e1.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }


                });
            }


        });


    }
}
