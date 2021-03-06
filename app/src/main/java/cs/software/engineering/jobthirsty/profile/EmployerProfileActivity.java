package cs.software.engineering.jobthirsty.profile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;
import cs.software.engineering.jobthirsty.util.StringParser;

public class EmployerProfileActivity extends NavigationDrawerFramework {

    //PRIVATE VARIABLES
    //Toolbar Variables
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private TextView editProfileBtn;
    private boolean editable;

    //UI Variables
    private EditText location;
    private EditText biography;

    // Profile Image Variables
    private static final int SELECT_PICTURE = 1;
    private ParseImageView profileImage;
    private static ParseFile parseFile;

    // Parent layout
    private RelativeLayout jobsParent;

    // Child layout
    private JobsSection jobsSection;

    //Employer Data Variables
    private String userId;
    private String firstName;
    private String lastName;
    private ParseUser user;
    private ParseObject dataObject;

    //OVERRIDE [START] -----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        initialize();
        loadProfilePage();
        setListeners();
    }
    //[END] ----------------------------------------------------------------------------------------


    //HELPER FUNCTIONS[START] ----------------------------------------------------------------------
    private void initialize() {
        //set minimum height for bottom half to enable scrolling
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;

        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLinear);
        mainLayout.setMinimumHeight(screenHeight - actionBarHeight);

        //Tool bar
        toolbar = getToolbar();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setCollapsedTitleTextColor(0xFFffffff);
        collapsingToolbarLayout.setExpandedTitleColor(0xFFffffff);

        //Edit button
        editProfileBtn = (TextView) findViewById(R.id.editProfileBtn);
        editable = false;

        //Editable views
        location = (EditText) findViewById(R.id.location);
        biography = (EditText) findViewById(R.id.biography);
        profileImage = (ParseImageView) findViewById(R.id.profileImage);

        //Parent layouts
        jobsParent = (RelativeLayout) findViewById(R.id.jobsParent);

        //Section layouts
        jobsSection = new JobsSection(getApplicationContext());
    }

    private void setListeners() {
        //Overall edit button
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toggle
                editable = !editable;

                //if editable
                if (editable) {
                    //enable edits for EditTexts
                    location.setEnabled(true);
                    location.setInputType(InputType.TYPE_CLASS_TEXT);
                    biography.setEnabled(true);
                    jobsSection.enableEdit();

                    editProfileBtn.setText("Save");

                } else {
                    //disable edits for EditTexts
                    location.setEnabled(false);
                    biography.setEnabled(false);
                    sendDataToParse();

                    jobsSection.disableEdit();

                    editProfileBtn.setText("Edit");
                }
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editable) {
                    //edit profile image
                    selectProfileImage();
                }
            }
        });
    }

    private void selectProfileImage() {
        Log.d("<selectImage>", "Select image");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 600);
        intent.putExtra("aspectY", 834);
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 834);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                ,"uploadPicture.jpg");
        intent.putExtra("output", Uri.fromFile(file));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = (Uri) data.getData();
                String selectedImagePath = selectedImageUri.getPath();
                System.out.println("Image Path : " + selectedImagePath);
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);

                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

                parseFile = new ParseFile("test_photo.jpg", image);
                profileImage.setParseFile(parseFile);
                profileImage.loadInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        // The image is loaded and displayed!
                        dataObject.put("profileImage", parseFile);
                        dataObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                // upload all images (for testing)
                                final ParseObject imageUpload = new ParseObject("ImageUpload");
                                imageUpload.put("ImageName", "Test Image");
                                imageUpload.put("ImageFile", parseFile);
                                imageUpload.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                    }
                                });
                            }
                        });
                    }
                });

            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void sendDataToParse() {
        final String locationData = location.getText().toString();
        final String biographyData = biography.getText().toString();
        final ArrayList<String> jobsData = jobsSection.getData();

        dataObject.put("location", locationData);
        dataObject.put("biography", biographyData);
        dataObject.put("jobPostings", jobsData);

        dataObject.saveInBackground();

    }


    //dataId need to be passed in to distinguish who's profile to load up
    private void retrieveDataFromParse(String dataId) {
        ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployerData");
        q.getInBackground(dataId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dataRow, ParseException e) {
                dataObject = dataRow;

                String locationData = dataRow.get("location") == null ? "" : dataRow.get("location").toString();
                String biographyData = dataRow.get("biography") == null ? "" : dataRow.get("biography").toString();

                final ArrayList<ArrayList<String>> jobsData = new ArrayList<>();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Position");
                query.whereEqualTo("bossId", user.getObjectId());
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> rows, ParseException e) {
                        if (e == null) {
                            for (int i = 0; i < rows.size(); ++i) {
                                ParseObject po = rows.get(i);
                                String jobTitle = po.get("positionTitle").toString();
                                String objectId = po.getObjectId();

                                ArrayList<String> row = new ArrayList<>();
                                row.add(jobTitle);
                                row.add(objectId);
                                jobsData.add(row);
                            }

                            jobsParent.removeView(jobsSection);
                            jobsSection.setData(jobsData);
                            jobsParent.addView(jobsSection);
                        } else {
                            // error
                        }
                    }
                });

                //download and display profile image
                parseFile = dataRow.getParseFile("profileImage");
                if (parseFile != null) {
                    profileImage.setParseFile(parseFile);
                    profileImage.loadInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            // The image is downloaded and displayed
                        }
                    });
                } else {
                    profileImage.setImageResource(R.drawable.profile_placeholder);
                }

                location.setText(locationData);
                location.setEnabled(false);
                biography.setText(biographyData);
                biography.setEnabled(false);
            }
        });
    }

    //Load profile page
    private void loadProfilePage() {

        userId = getIntent().getExtras().getString("userId");

        //fetch all user variables with userId
        ParseQuery<ParseUser> q = ParseUser.getQuery();
        q.getInBackground(userId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e)
            {
                user = parseUser;
                firstName = parseUser.get("firstName").toString();
                lastName = parseUser.get("lastName").toString();
                String dataId = parseUser.get("dataId").toString();

                //display profile's name
                collapsingToolbarLayout.setTitle(firstName + " " + lastName);

                retrieveDataFromParse(dataId);
            }
        });
        //[END] ----------------------------------------------------------------------------------------
    }
}
