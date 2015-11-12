package cs.software.engineering.jobthirsty.company_page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import cs.software.engineering.jobthirsty.R;

public class CreateCompanyPage extends AppCompatActivity {


    EditText companyTitle;
    EditText companyTagline;
    EditText companyDescription;
    EditText companyLocation;

    Button postPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_company_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        companyTitle = (EditText) findViewById(R.id.companyTitle);
        companyTagline = (EditText) findViewById(R.id.companyTagline);
        companyDescription = (EditText) findViewById(R.id.companyDescription);
        companyLocation = (EditText) findViewById(R.id.companyLocation);
        postPosition = (Button) findViewById(R.id.postPosition);

        postPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject position = new ParseObject("Company");
                position.put("companyTitle", companyTitle.getText().toString());
                position.put("companyTagline",companyTagline.getText().toString());
                position.put("companyDescription",companyDescription.getText().toString());
                position.put("companyLocation", companyLocation.getText().toString());
                position.put("bossId", ParseUser.getCurrentUser().getObjectId().toString());
                position.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        onBackPressed();
                    }
                });

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.home:
                onBackPressed();
                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(menuItem);
        }

    }

}
