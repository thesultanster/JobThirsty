package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import cs.software.engineering.jobthirsty.util.StringParser;


/**
 * Created by timka on 10/18/2015.
 */
public class SkillsSection extends ProfileSection {

    //PRIVATE VARIABLES
    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;


    //CONSTRUCTOR [START] --------------------------------------------------------------------------
    public SkillsSection(Context context){
        super(context);
        this.context = context;

        initialize();
    }
    //[END] ----------------------------------------------------------------------------------------



    //UTILITY FUNCTIONS [START] --------------------------------------------------------------------
    public void addElement(String skill, String endorseCount, boolean isOwnerUser, boolean enabled)
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
        rl.addView(createEndorseView(endorseCount, isOwnerUser, enabled));


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
    public ArrayList<ArrayList<String>> getData()
    {
        //          0          1
        //rows of <skill, endorseCount> pairs
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for(int i = 0; i < list.size(); ++i) {
            RelativeLayout row = list.get(i);

            EditText skillsET = (EditText) row.getChildAt(0);
            TextView endorseET = (TextView) row.getChildAt(2);

            ArrayList<String> pair = new ArrayList<>();
            pair.add(skillsET.getText().toString());
            pair.add(endorseET.getText().toString());
            data.add(pair);
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
            String endorseCount = skill.get(1);

            //set data
            addElement(skillText, endorseCount, isOwnerUser, false);
        }
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
        etLayoutParams.setMargins((int)(displayMetrics.widthPixels*0.025), 0, 0, 0);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setSingleLine();
        et.setWidth(displayMetrics.widthPixels - (int) (displayMetrics.widthPixels * (0.35)));
        et.setEnabled(enabled);
        et.setHint("[Skill]");
        et.setHintTextColor(0xFF808080);

        if(enabled) {
            et.requestFocus(); //put on cursor
        }

        if(!skill.equals("")) {
            et.setText(skill);
        }

        et.requestLayout(); //update

        return et;
    }

    private TextView createEndorseView(final String endorseCount, boolean isOwnerUser, boolean enabled)
    {
        RelativeLayout.LayoutParams endorseCountLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        endorseCountLayoutParams.addRule(RelativeLayout.LEFT_OF, list.size() + 1000);

        final TextView endorseEt = new TextView(context);
        endorseEt.setLayoutParams(endorseCountLayoutParams);
        endorseEt.setEms(1);
        endorseEt.setTextColor(0xFF000000);
        endorseEt.setText(endorseCount);
        //endorseEt.setEnabled(enabled);
        endorseEt.setGravity(Gravity.CENTER);

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
                    String count = endorseEt.getText().toString();
                    final int countInt = Integer.parseInt(count) + 1; //increment
                    endorseEt.setText((countInt + ""));

                    RelativeLayout parentRL = (RelativeLayout) v.getParent();
                    EditText skillNameET = (EditText) parentRL.getChildAt(0);
                    final String skillName = skillNameET.getText().toString();

                    ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployeeData");
                    q.getInBackground(dataId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject dataRow, ParseException e) {
                            ArrayList<ArrayList<String>> parsedSkillsData = getData();
                            for(int i = 0; i < parsedSkillsData.size(); ++i) {
                                ArrayList<String> row = parsedSkillsData.get(i);
                                String skill = row.get(0);

                                if(skill.equals(skillName)) {
                                    parsedSkillsData.get(i).set(1, countInt + "");
                                }
                            }

                            dataRow.put("skills", (new StringParser(parsedSkillsData)).getConcated());
                        }
                    });
                }
            });
        }

        return endorseEt;
    }
    //[END] ----------------------------------------------------------------------------------------
}
