package cs.software.engineering.jobthirsty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Arrays;

import cs.software.engineering.jobthirsty.profile.EmployeeProfileActivity;
import cs.software.engineering.jobthirsty.profile.EmployerProfileActivity;

public class SignUp extends AppCompatActivity {


    EditText username;
    EditText password;
    EditText firstName;
    EditText lastName;
    EditText email;
    RadioGroup radioButtonGroup;
    RadioButton selectedRadio;

    Button signUp;
    Button login;
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
        radioButtonGroup = (RadioGroup) findViewById(R.id.employerOrEmployee);

        signUp = (Button) findViewById(R.id.signUpButton);
        login = (Button) findViewById(R.id.loginButton);



        // Sign Up Button Listener
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioButtonGroup.getCheckedRadioButtonId();
                selectedRadio = (RadioButton) findViewById(selectedId);
                String selectedRadioText = (String) selectedRadio.getText();
                final Boolean isEmployer = selectedRadioText.equals("Employer");
                String dataId = "";
                try {
                    if (isEmployer) {
                        ParseObject data = new ParseObject("EmployerData");
                        data.save();
                        dataId = data.getObjectId();
                    } else {
                        ParseObject data = new ParseObject("EmployeeData");
                        data.put("location", "");
                        data.put("biography", "");
                        data.put("experience", Arrays.asList(""));
                        data.put("projects", Arrays.asList(""));
                        data.put("education", Arrays.asList(""));
                        data.put("activities", Arrays.asList(""));
                        data.put("awards", Arrays.asList(""));
                        data.put("skills", Arrays.asList(""));
//                        data.put("userId", );
                        data.save();
                        dataId = data.getObjectId();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.w("<SIGNUP>", selectedRadioText);
                // Create New Parse User
                user = new ParseUser();
                user.put("username", username.getText().toString());
                user.put("password", password.getText().toString());
                user.put("firstName", firstName.getText().toString());
                user.put("lastName", lastName.getText().toString());
                user.put("isBoss", isEmployer);
                user.put("dataId", dataId);
                user.setEmail(email.getText().toString());

                // Sign Up Parse User
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {

                        // Login When Sign Up Done

                        try {
                            //add new row
                            //ParseObject row = new ParseObject("")
                            ParseUser.logIn(username.getText().toString(), password.getText().toString());

                            // Go to Main Page when successfully logged in
                            Intent intent;
                            if (isEmployer) {
                                intent = new Intent(SignUp.this, EmployerProfileActivity.class);
                            } else {
                                intent = new Intent(SignUp.this, EmployeeProfileActivity.class);
                            }
                            intent.putExtra("firstName", firstName.getText().toString());
                            intent.putExtra("lastName", lastName.getText().toString());
                            intent.putExtra("dataId", ParseUser.getCurrentUser().get("dataId").toString());

                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Successfully Signed Up", Toast.LENGTH_SHORT).show();
                            finish();

                        } catch (ParseException e1) {
                            e1.printStackTrace();
                            Toast.makeText( getApplicationContext(), e1.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }


        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });


    }
}
