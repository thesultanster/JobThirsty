package cs.software.engineering.jobthirsty.profile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class EmployeeProfileActivity extends NavigationDrawerFramework {

    //PRIVATE VARIABLES
    //Toolbar Variables
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;


    //UI Variables
    //FAB
    private FloatingActionButton fab;

    //Parent layout
    private RelativeLayout skillsParent;
    private RelativeLayout experienceParent;
    private RelativeLayout projectsParent;
    private RelativeLayout educationParent;
    private RelativeLayout activitiesParent;
    private RelativeLayout awardsParent;

    //Child layout
    private SkillsSection skillsSection;
    private ExperienceSection experienceSection;
    private ProjectsSection projectsSection;
    private EducationSection educationSection;
    private ActivitiesSection activitiesSection;
    private AwardSection awardsSection;

    // Profile Image Variables
    private static final int SELECT_PICTURE = 1;
    private ParseImageView profileImage;
    private static ParseFile parseFile;

    //Entire Edit button
    private TextView editProfileBtn;
    private boolean editable;

    //Edit button for each section
    private ImageButton skillsEditBtn;
    private ImageButton experienceEditBtn;
    private ImageButton projectsEditBtn;
    private ImageButton educationEditBtn;
    private ImageButton activitiesEditBtn;
    private ImageButton awardsEditBtn;

    //Employee Data Variables
    private String userId;
    private String firstName;
    private String lastName;
    private EditText contact;
    private ImageView contactIcon;
    private EditText location;
    private EditText biography;
    private String dataId;
    private ParseUser user;
    private ParseUser currentUser;
    private String currentUserId;
    private String currentUserFullName;
    private ParseObject dataObject;
    private ParseObject currentUserDataObject ;
    private boolean isOwnerUser;

    private enum Connection {
        NONE, SELF, INTENDER, RECEIVER, ESTABLISHED
    }
    private Connection connectionStatus;
    private ParseObject connectionObject;

    private String intenderFullName;
    private String receiverFullName;
    private boolean newsFeedFound;

    //Debug Variables
    String TAG = "datacheck";

    //OVERRIDE [START] -----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

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

        LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinear);
        mainLinearLayout.setMinimumHeight(screenHeight - actionBarHeight);

        //Toolbar
        toolbar = getToolbar();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setCollapsedTitleTextColor(0xFFffffff);
        collapsingToolbarLayout.setExpandedTitleColor(0xFFffffff);

        // Floating Action Bar
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //Parent layouts
        skillsParent = (RelativeLayout) findViewById(R.id.skillsParent);
        experienceParent = (RelativeLayout) findViewById(R.id.experienceParent);
        projectsParent = (RelativeLayout) findViewById(R.id.projectsParent);
        educationParent = (RelativeLayout) findViewById(R.id.educationParent);
        activitiesParent = (RelativeLayout) findViewById(R.id.activitiesParent);
        awardsParent = (RelativeLayout) findViewById(R.id.awardsParent);

        //Child/Section layouts
        skillsSection = new SkillsSection(getApplicationContext());
        experienceSection = new ExperienceSection(getApplicationContext());
        projectsSection = new ProjectsSection(getApplicationContext());
        educationSection = new EducationSection(getApplicationContext());
        activitiesSection = new ActivitiesSection(getApplicationContext());
        awardsSection = new AwardSection(getApplicationContext());

        profileImage = (ParseImageView) findViewById(R.id.profileImage);

        //Edit button
        editProfileBtn = (TextView) findViewById(R.id.editProfileBtn);
        editable = false;

        //Section edit buttons
        skillsEditBtn = (ImageButton) findViewById(R.id.skillsEditBtn);
        experienceEditBtn = (ImageButton) findViewById(R.id.experienceEditBtn);
        projectsEditBtn = (ImageButton) findViewById(R.id.projectsEditBtn);
        educationEditBtn = (ImageButton) findViewById(R.id.educationEditBtn);
        activitiesEditBtn = (ImageButton) findViewById(R.id.activitiesEditBtn);
        awardsEditBtn = (ImageButton) findViewById(R.id.awardsEditBtn);

        //Employee data variables
        //Editable views
        contact = (EditText) findViewById(R.id.contact);
        contactIcon = (ImageView) findViewById(R.id.contact_icon);
        location = (EditText) findViewById(R.id.location);
        biography = (EditText) findViewById(R.id.biography);
        userId = getIntent().getExtras().getString("userId");
        ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", userId);
        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (e == null) {
                    user = object;
                }
            }
        });
        currentUser = ParseUser.getCurrentUser();
        currentUserId = currentUser.getObjectId();
        currentUserFullName = currentUser.getString("firstName") + " " + currentUser.getString("lastName");

        connectionStatus = Connection.NONE;
    }

    private void setListeners() {
        //Overall edit button
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toggle
                editable = !editable;

                //if editable
                if(editable) {
                    //enable edits for EditTexts
                    contact.setEnabled(true);
                    location.setEnabled(true);
                    location.setInputType(InputType.TYPE_CLASS_TEXT);
                    biography.setEnabled(true);

                    //show edit buttons for sections
                    showEditButtons();

                    //enable inside materials
                    skillsSection.enableEdit();
                    experienceSection.enableEdit();
                    projectsSection.enableEdit();
                    educationSection.enableEdit();
                    awardsSection.enableEdit();
                    activitiesSection.enableEdit();

                } else {

                    //disable edits for EditTexts
                    contact.setEnabled(false);
                    location.setEnabled(false);
                    biography.setEnabled(false);

                    //hide edit buttons for sections
                    hideEditButtons();

                    //disable inside materials
                    skillsSection.disableEdit();
                    experienceSection.disableEdit();
                    projectsSection.disableEdit();
                    educationSection.disableEdit();
                    awardsSection.disableEdit();
                    activitiesSection.disableEdit();

                    sendDataToParse();
                }
            }
        });

        //Section edit buttons
        skillsEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillsParent.removeView(skillsSection);
                skillsSection.addElement("", true, true);
                skillsParent.addView(skillsSection);
            }
        });

        experienceEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                experienceParent.removeView(experienceSection);
                experienceSection.addElement("", "", true);
                experienceParent.addView(experienceSection);
            }
        });

        projectsEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projectsParent.removeView(projectsSection);
                projectsSection.addElement("", "", true);
                projectsParent.addView(projectsSection);
            }
        });

        educationEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationParent.removeView(educationSection);
                educationSection.addElement("", "", "", true);
                educationParent.addView(educationSection);
            }
        });

        activitiesEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activitiesParent.removeView(activitiesSection);
                activitiesSection.addElement("", true);
                activitiesParent.addView(activitiesSection);
            }
        });

        awardsEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                awardsParent.removeView(awardsSection);
                awardsSection.addElement("", true);
                awardsParent.addView(awardsSection);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (connectionStatus == Connection.NONE) {
                    //send connection request
                    ParseObject newConnection = new ParseObject("Connections");
                    newConnection.put("intenderId", currentUserId);
                    newConnection.put("intenderFullName", currentUserFullName);
                    newConnection.put("receiverId", userId);
                    newConnection.put("handshake", false);
                    newConnection.saveInBackground();
                } else if (connectionStatus == Connection.RECEIVER) {
                    //accept request
                    connectionObject.put("handshake", true);
                    connectionObject.saveInBackground();

                    dataObject.addUnique("connections", currentUserId);
                    dataObject.saveInBackground();
                    currentUserDataObject.addUnique("connections", userId);
                    currentUserDataObject.saveInBackground();

                    viewContactInfo();
                }
            }
        });

        //profile image upload
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

    private void showEditButtons() {
        skillsEditBtn.setVisibility(View.VISIBLE);
        experienceEditBtn.setVisibility(View.VISIBLE);
        projectsEditBtn.setVisibility(View.VISIBLE);
        educationEditBtn.setVisibility(View.VISIBLE);
        activitiesEditBtn.setVisibility(View.VISIBLE);
        awardsEditBtn.setVisibility(View.VISIBLE);
    }

    private void hideEditButtons() {
        skillsEditBtn.setVisibility(View.INVISIBLE);
        experienceEditBtn.setVisibility(View.INVISIBLE);
        projectsEditBtn.setVisibility(View.INVISIBLE);
        educationEditBtn.setVisibility(View.INVISIBLE);
        activitiesEditBtn.setVisibility(View.INVISIBLE);
        awardsEditBtn.setVisibility(View.INVISIBLE);
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
                File file = new File(selectedImagePath);
                file.delete();

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
        final String contactData = contact.getText().toString();
        final String locationData = location.getText().toString();
        final String biographyData = biography.getText().toString();

        final ArrayList<String> skillsData = skillsSection.getData();
        final ArrayList<String> experienceData = (new StringParser(experienceSection.getData())).getConcated();
        final ArrayList<String> projectData = (new StringParser(projectsSection.getData())).getConcated();
        final ArrayList<String> educationData = (new StringParser(educationSection.getData())).getConcated();
        final ArrayList<String> activitiesData = activitiesSection.getData();
        final ArrayList<String> awardsData = awardsSection.getData();

        dataObject.put("contact", contactData);
        dataObject.put("location", locationData);
        dataObject.put("biography", biographyData);

        dataObject.put("skills", skillsData);
        dataObject.put("experience", experienceData);
        dataObject.put("projects", projectData);
        dataObject.put("education", educationData);
        dataObject.put("activities", activitiesData);
        dataObject.put("awards", awardsData);
        dataObject.saveInBackground();
    }

    private void viewContactInfo() {
        contact.setVisibility(View.VISIBLE);
        contactIcon.setVisibility(View.VISIBLE);
    }

    private void setConnectionStatus() {

        if (isOwnerUser) {
            connectionStatus = Connection.SELF;
            connectionObject = null;
            viewContactInfo();
            return;
        }

        ParseQuery<ParseObject> queryIntender = ParseQuery.getQuery("Connections");
        queryIntender.whereEqualTo("intenderId", currentUserId);
        queryIntender.whereEqualTo("receiverId", userId);

        ParseQuery<ParseObject> queryReceiver = ParseQuery.getQuery("Connections");
        queryReceiver.whereEqualTo("intenderId", userId);
        queryReceiver.whereEqualTo("receiverId", currentUserId);

        List<ParseQuery<ParseObject>> queryList = new ArrayList<ParseQuery<ParseObject>>();
        queryList.add(queryIntender);
        queryList.add(queryReceiver);

        final ParseQuery<ParseObject> queryConnections = ParseQuery.or(queryList);
        queryConnections.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> connections, ParseException e) {
                if (e == null) {
                    boolean has_connection = false;
                    for (ParseObject c : connections) {
                        connectionObject = c;
                        if (c.getBoolean("handshake")) {
                            connectionStatus = Connection.ESTABLISHED;
                        } else {
                            if (c.get("intenderId").equals(currentUserId)) {
                                //do nothing
                                connectionStatus = Connection.INTENDER;
                            } else if (c.get("receiverId").equals(currentUserId)) {
                                //accept request
                                connectionStatus = Connection.RECEIVER;
                            }
                        }
                        has_connection = true;
                        break;
                    }

                    if (!has_connection) {
                        connectionStatus = Connection.NONE;
                    }
                    if (connectionStatus == Connection.SELF || connectionStatus == Connection.ESTABLISHED) {
                        viewContactInfo();
                    }
                }
            }
        });
    }

    //dataId need to be passed in to distinguish who's profile to load up
    private void retrieveDataFromParse(final String dataId) {

        ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployeeData");
        q.getInBackground(dataId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dataRow, ParseException e) {
                dataObject = dataRow;

                String contactData = dataRow.get("contact").toString();
                String locationData = dataRow.get("location").toString();
                String biographyData = dataRow.get("biography").toString();

                ArrayList<String> skillsData = (ArrayList<String>) dataRow.get("skills");
                ArrayList<String> experienceData = (ArrayList<String>) dataRow.get("experience");
                ArrayList<String> projectData = (ArrayList<String>) dataRow.get("projects");
                ArrayList<String> educationData = (ArrayList<String>) dataRow.get("education");
                ArrayList<String> activitiesData = (ArrayList<String>) dataRow.get("activities");
                ArrayList<String> awardsData = (ArrayList<String>) dataRow.get("awards");

                contact.setText(contactData);
                contact.setEnabled(false);
                location.setText(locationData);
                location.setEnabled(false);
                biography.setText(biographyData);
                biography.setEnabled(false);

                skillsParent.removeView(skillsSection);
                //only enabled when viewing someone else's profile
                skillsSection.setDataObject(dataRow);
                skillsSection.setData(skillsData, isOwnerUser);
                skillsParent.addView(skillsSection);

                experienceParent.removeView(experienceSection);
                experienceSection.setData(experienceData);
                experienceParent.addView(experienceSection);

                projectsParent.removeView(projectsSection);
                projectsSection.setData(projectData);
                projectsParent.addView(projectsSection);

                educationParent.removeView(educationSection);
                educationSection.setData(educationData);
                educationParent.addView(educationSection);

                activitiesParent.removeView(activitiesSection);
                activitiesSection.setData(activitiesData);
                activitiesParent.addView(activitiesSection);

                awardsParent.removeView(awardsSection);
                awardsSection.setData(awardsData);
                awardsParent.addView(awardsSection);

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

                setConnectionStatus();
            }
        });
    }

    private void retrieveCurrentUserDataFromParse(final String dataId) {

        ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployeeData");
        q.getInBackground(dataId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dataRow, ParseException e) {
                currentUserDataObject = dataRow;
            }
        });
    }

    //Load profile page
    private void loadProfilePage() {
//        Bundle extras = getIntent().getExtras();
//        userId = extras.getString("userId");
        userId = getIntent().getExtras().getString("userId");

        //fetch all user variables with userId
        ParseQuery<ParseUser> q = ParseUser.getQuery();
        q.getInBackground(userId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                firstName = parseUser.get("firstName").toString();
                lastName = parseUser.get("lastName").toString();
                dataId = parseUser.get("dataId").toString();

                //set profile owner's data id
                skillsSection.setDataId(dataId);

                isOwnerUser = currentUserId.equals(userId);

                // if user is seeing their own profile then hide connection fab
                if (isOwnerUser) {
                    fab.setVisibility(View.INVISIBLE);
                }
                // else if user is seeing someone else's profile, hide editProfile button
                else {
                    editProfileBtn.setVisibility(View.INVISIBLE);
                }

                //display profile's name
                collapsingToolbarLayout.setTitle(firstName + " " + lastName);

                retrieveDataFromParse(dataId);
            }
        });

        String currentUserDataId = currentUser.getString("dataId");
        retrieveCurrentUserDataFromParse(currentUserDataId);
    }
    //[END] ----------------------------------------------------------------------------------------
}
