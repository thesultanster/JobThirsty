package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Iterator;

import cs.software.engineering.jobthirsty.util.StringParser;


/**
 * Created by timka on 10/18/2015.
 */
public class SkillsSection extends ProfileSection {

    //PRIVATE VARIABLES
    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;

    private String TAG = "checkEndorse";


    //CONSTRUCTOR [START] --------------------------------------------------------------------------
    public SkillsSection(Context context){
        super(context);
        this.context = context;

        initialize();
    }
    //[END] ----------------------------------------------------------------------------------------



    //UTILITY FUNCTIONS [START] --------------------------------------------------------------------
    public void addElement(String skill, boolean isOwnerUser, boolean enabled)
    {
        //create a row layout
        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(blockLayoutParams);

        //add EditText to row
        rl.addView(createSkillView(skill, enabled));

        //Add ImageView for increasing/decreasing endorse
        // should only be visible to everyone else except for you


        //add delete button to row
        rl.addView(createMinusButton(list.size() + 1000, enabled));

        //Add TextView for endorse count
        rl.addView(createEndorseView(isOwnerUser, enabled));


        //add the row
        this.addView(rl);
        list.add(rl);
    }

    public void enableEdit() {
        for (int i = 0; i < list.size(); ++i){
            list.get(i).getChildAt(0).setEnabled(true);
            list.get(i).getChildAt(1).setVisibility(VISIBLE); //hide minus button
            list.get(i).getChildAt(2).setVisibility(INVISIBLE); //hide endorse count
        }
    }

    public void disableEdit()
    {
        //iterate through all rows
        for(Iterator<RelativeLayout> i = list.iterator(); i.hasNext(); ) {
            RelativeLayout row = i.next();
            EditText et = (EditText) row.getChildAt(0);
            ImageButton ib = (ImageButton) row.getChildAt(1);
            TextView tv = (TextView) row.getChildAt(2);

            //check if current item's bullet is empty
            if(et.getText().toString().equals("")){
                //if empty, remove the entire item
                i.remove();
                removeRow(row.getChildAt(0)); //pick any random child for matching the format
            }
            else {
                //otherwise, simply hide them
                et.setEnabled(false);
                ib.setVisibility(INVISIBLE); //hide minus button
                tv.setVisibility(VISIBLE); //show visibility
            }
        }
    }

    //fetches data from activity
    public ArrayList<String> getData()
    {
        final ArrayList<String> data = new ArrayList<>();

        for(int i = 0; i < list.size(); ++i) {
            RelativeLayout row = list.get(i);

            EditText et = (EditText) row.getChildAt(0);
            TextView tv = (TextView) row.getChildAt(2);

            final String skillName = et.getText().toString();

            //no endorsement
            if(tv.getText().equals("0")) {
                data.add(et.getText().toString());
            }
            //need to fetch from db if there are any endorsements
            else {
                ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployeeData");
                //q.whereEqualTo("dataId", dataId);
                try {
                    ParseObject dataRow = q.get(dataId);
                    //TODO: continue
                    ArrayList<String> skills = (ArrayList<String>) dataRow.get("skills");
                    ArrayList<ArrayList<String>> skillsList = (new StringParser(skills, false)).getParsed();
                    for(int j = 0; j < skillsList.size(); ++j) {
                        ArrayList<String> skillRow = skillsList.get(j);
                        String cmpName = skillRow.get(0).toString();
                        if (skillName.equals(cmpName)) {
                            data.add(skills.get(i));
                            break;
                        }
                    }

                }
                catch (ParseException e) {

                }
/*
                q.getInBackground(dataId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject dataRow, ParseException e) {
                        //String skill = dataRow.get("skills").toString();
                        ArrayList<String> skills = (ArrayList<String>) dataRow.get("skills");
                        ArrayList<ArrayList<String>> skillsList = (new StringParser(skills, false)).getParsed();
                        for(int i = 0; i < skillsList.size(); ++i) {
                            ArrayList<String> skillRow = skillsList.get(i);
                            String cmpName = skillRow.get(0).toString();
                            if(skillName.equals(cmpName)) {
                                data.add(skills.get(i));
                                break;
                            }
                        }
                    }
                });
*/
            }
        }

        return data;
    }

    //loads the data to activity
    public void setData(ArrayList<String> data, boolean isOwnerUser)
    {
        ArrayList<ArrayList<String>> dataParsed = (new StringParser(data, false)).getParsed();
        for(int i = 0; i < dataParsed.size(); ++i) {
            //get skill row
            ArrayList<String> skill = dataParsed.get(i);

            //parse out each field
            String skillText = skill.get(0);

            //set data
            addElement(skillText, isOwnerUser, false);
        }

        //update the endorses
        ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployeeData");
        q.getInBackground(dataId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dataRow, ParseException e) {
                ArrayList<String> data = (ArrayList<String>) dataRow.get("skills");
                updateEndorse(data);
            }
        });
    }

    //setter for dataId
    public void setDataId(String dataId) { this.dataId = dataId; }
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
        //layout params for a row layout
        blockLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private EditText createSkillView(String skill, boolean enabled)
    {
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        etLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        etLayoutParams.setMargins((int)(displayMetrics.widthPixels*0.025), 0, 0, 0);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setSingleLine();
        et.setPadding(0, 0, 0, 0);
        et.setWidth(displayMetrics.widthPixels - (int) (displayMetrics.widthPixels * (0.35)));
        et.setEnabled(enabled);
        et.setHint("[Skill]");
        et.setHintTextColor(0xFF808080);
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);


        if(enabled) {
            et.requestFocus(); //put on cursor
        }

        if(!skill.equals("")) {
            et.setText(skill);
        }

        et.requestLayout(); //update

        return et;
    }

    private TextView createEndorseView(boolean isOwnerUser, boolean enabled)
    {
        RelativeLayout.LayoutParams endorseCountLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        endorseCountLayoutParams.setMargins(0, 10, 0, 10);
        endorseCountLayoutParams.addRule(RelativeLayout.LEFT_OF, list.size() + 1000);
        endorseCountLayoutParams.addRule(RelativeLayout.LEFT_OF, list.size() + 1000);


        final TextView endorseEt = new TextView(context);
        endorseEt.setLayoutParams(endorseCountLayoutParams);
        endorseEt.setEms(1);
        endorseEt.setBackgroundColor(0xFF53DED1);
        endorseEt.setWidth(100);
        endorseEt.setHeight(100);
        endorseEt.setTextColor(0xffffffff);
        endorseEt.setText("0");
        endorseEt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        endorseEt.setGravity(Gravity.CENTER);
        endorseEt.setTypeface(Typeface.DEFAULT_BOLD);

        if(enabled) {
            endorseEt.setVisibility(INVISIBLE); //initially invisible
        }
        else {
            endorseEt.setVisibility(VISIBLE); //set visible when loaded
        }

        endorseEt.requestLayout();

        //only enable it if you are not the owner of this profile
        if(!isOwnerUser) {
            endorseEt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout currentRow = (RelativeLayout) v.getParent();
                    int foundIndex = 0;
                    for (int i = 0; i < list.size(); ++i) {
                        if (list.get(i).equals(currentRow)) {
                            foundIndex = i;
                            break;
                        }
                    }
                    //final to put it in getInBackground
                    final int rowCount = foundIndex;

                    //get dataRow
                    ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployeeData");
                    q.getInBackground(dataId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject dataRow, ParseException e) {
                            ArrayList<String> data = (ArrayList<String>) dataRow.get("skills");
                            ArrayList<ArrayList<String>> endorsedUsers = getEndorsedUsers(data);

                            //remove if this user exists, unendorse
                            ArrayList<String> row = endorsedUsers.get(rowCount);
                            boolean removed = false;
                            for (int i = 0; i < row.size(); ++i) {
                                //found
                                if (row.get(i).equals(ParseUser.getCurrentUser().get("dataId").toString())) {
                                    row.remove(row.get(i));
                                    removed = true;
                                    break;
                                }
                            }

                            //this user wasn't listed under the endorse list, endorse
                            if (!removed) {
                                row.add(ParseUser.getCurrentUser().get("dataId").toString());
                            }

                            data = updateData(data, endorsedUsers);
                            updateEndorse(data);
                        }
                    });
                }
            });
        }

        return endorseEt;
    }

    //get updated endorsedUsers
    private ArrayList<ArrayList<String>> getEndorsedUsers(ArrayList<String> skillsData)
    {
        //need to fetch data
        ArrayList<ArrayList<String>> data = (new StringParser(skillsData, false)).getParsed();
        ArrayList<ArrayList<String>> endorsedUsers = new ArrayList<>();

        for(int i = 0; i < data.size(); ++i) {
            ArrayList<String> row = data.get(i);

            //check if it has endorsement
            if(row.size() > 1) {
                ArrayList<String> newList = new ArrayList<>(row.subList(1, row.size()));
                endorsedUsers.add(newList);
            }
            //add new list if this skill has no endorsement
            else {
                endorsedUsers.add(new ArrayList<String>());
            }
        }
        return endorsedUsers;
    }

    private ArrayList<String> updateData(ArrayList<String> data, ArrayList<ArrayList<String>> endorserList)
    {
        //parse out skill name from data and push it to front of endorserList
        ArrayList<ArrayList<String>> parsedData = (new StringParser(data, false)).getParsed();
        for(int i = 0; i < endorserList.size(); ++i) {
            ArrayList<String> row = endorserList.get(i);
            ArrayList<String> dataRow = parsedData.get(i);
            row.add(0, dataRow.get(0)); //push front skill name
        }
        ArrayList<String> toReturn = (new StringParser(endorserList)).getConcated();
        return toReturn;
    }

    //updates the endorse change to database
    private void updateEndorse(final ArrayList<String> data)
    {
        ArrayList<ArrayList<String>> parsedData = (new StringParser(data, false)).getParsed();

        if(parsedData.size() != list.size()) {
            Log.d(TAG, "updateEndorse: size different");
        }

        //first update all the textviews
        for(int i = 0; i < parsedData.size(); ++i) {
            ArrayList<String> row = parsedData.get(i);

            //get skill's layout to get textview for endorse
            RelativeLayout rowLayout = list.get(i);

            //endorse textview is 3rd child
            TextView tv = (TextView) rowLayout.getChildAt(2);
            tv.setText((row.size()-1) + ""); //size should be 1 + size of list of endorsers
        }

        //update Parse
        ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployeeData");
        q.getInBackground(dataId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dataRow, ParseException e) {
                dataRow.put("skills", data);
                dataRow.saveInBackground();
            }
        });
    }
    //[END] ----------------------------------------------------------------------------------------
}
