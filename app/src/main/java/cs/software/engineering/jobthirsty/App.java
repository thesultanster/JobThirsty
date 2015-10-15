package cs.software.engineering.jobthirsty;

import android.app.Application;
import android.util.Log;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by sultankhan on 10/14/15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "Qq5Zb9Oqg7PwLsZuHYfU6zKCpgMHiSerWPyWOzw1", "thcBOmJDAd7gFe3AfUphGE6pt3vAIZAmu7wexgDr");

        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d("MyApp", "Anonymous login failed.");
                } else {
                    Log.d("MyApp", "Anonymous user logged in.");
                }
            }
        });
    }
}
