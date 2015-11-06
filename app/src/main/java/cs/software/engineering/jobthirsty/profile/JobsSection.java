package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;

import cs.software.engineering.jobthirsty.util.StringParser;


/**
 * Created by jon on 11/4/2015.
 */
public class JobsSection extends ProfileSection {

    //PRIVATE VARIABLES
    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;
    private String firstName;
    private String lastName;

    //CONSTRUCTOR [START] --------------------------------------------------------------------------
    public JobsSection(Context context, String _firstName, String _lastName){
        super(context);
        this.context = context;
        firstName = _firstName;
        lastName = _lastName;
        initialize();
    }
    //[END] ----------------------------------------------------------------------------------------



    //UTILITY FUNCTIONS [START] --------------------------------------------------------------------
    public void addElement(String educationText, String educationMajor, boolean enabled)
    {
        //create a row layout
        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(blockLayoutParams);

        //add school to row
        rl.addView(createSchoolView(educationText, enabled));

        //add delete button to row
        rl.addView(createMinusButton(list.size() + 1000, enabled));

        //add major
        rl.addView(createMajorView(educationMajor, enabled));

        //add the row
        this.addView(rl);
        list.add(rl);
    }

    public void enableEdit() {
        for (int i = 0; i < list.size(); ++i){
            list.get(i).getChildAt(0).setEnabled(true);
            list.get(i).getChildAt(0).setVisibility(VISIBLE);
            list.get(i).getChildAt(1).setVisibility(VISIBLE); //show minus button
            list.get(i).getChildAt(2).setEnabled(true);
            list.get(i).getChildAt(2).setVisibility(VISIBLE);
        }
    }

    public void disableEdit()
    {
        //iterate through all rows
        for(Iterator<RelativeLayout> i = list.iterator(); i.hasNext(); ) {
            RelativeLayout row = i.next();
            EditText et = (EditText) row.getChildAt(0);
            ImageButton ib = (ImageButton) row.getChildAt(1);
            EditText majorEt = (EditText) row.getChildAt(2);

            //check if current item's bullet is empty

            if(et.getText().toString().equals("") && majorEt.getText().toString().equals("")){
                //if empty, remove the entire item
                i.remove();
                removeRow(row.getChildAt(0)); //pick any random child for matching the format
            }
            else {
                //otherwise, simply hide them
                et.setEnabled(false);
                ib.setVisibility(INVISIBLE); //hide minus button
                majorEt.setEnabled(false);

                //hide if empty
                if(et.getText().toString().equals(""))
                    et.setVisibility(INVISIBLE);
                if(majorEt.getText().toString().equals(""))
                    majorEt.setVisibility(INVISIBLE);
            }
        }
    }

    //fetches data from activity
    public ArrayList<ArrayList<String>> getData()
    {
        //           0       1  ,  2
        //rows of <school, major, GPA> pairs
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for(int i = 0; i < list.size(); ++i) {
            RelativeLayout row = list.get(i);

            EditText schoolET = (EditText) row.getChildAt(0);
            EditText majorET = (EditText) row.getChildAt(2);

            ArrayList<String> triplet = new ArrayList<>();
            triplet.add(schoolET.getText().toString());
            triplet.add(majorET.getText().toString());
            data.add(triplet);
        }

        return data;
    }


    //loads the data to activity
    public void setData(ArrayList<String> data)
    {
        ArrayList<ArrayList<String>> dataParsed = (new StringParser(data, false)).getParsed();
        for(int i = 0; i < dataParsed.size(); ++i) {
            //get experience row
            ArrayList<String> education = dataParsed.get(i);

            //parse out each field
            String educationText = education.get(0);
            String educationMajor = education.get(1);

            //set data
            addElement(educationText, educationMajor, false);
        }
    }
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
        //layout params for a row layout
        blockLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private EditText createSchoolView(String educationText, boolean enabled)
    {
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        etLayoutParams.setMargins((int)(displayMetrics.widthPixels*0.025), 0, 0, 0);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setSingleLine();
        et.setWidth(displayMetrics.widthPixels - (int) (displayMetrics.widthPixels * (0.27)));
        et.setEnabled(enabled);
        et.setHint("[Company]");
        et.setHintTextColor(0xFF808080);

        if(enabled) {
            et.requestFocus(); //put on cursor
        }

        if(!educationText.equals("")) {
            et.setText(educationText);
        }
        et.requestLayout(); //update

        return et;
    }



    private EditText createMajorView(String educationMajor, boolean enabled)
    {
        //set up layout params
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.BELOW, list.size() + 1000);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        etLayoutParams.setMargins((int)(displayMetrics.widthPixels*0.025), 0, 0, 0);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setWidth((int) (displayMetrics.widthPixels * 0.55));
        et.setSingleLine();
        et.setEnabled(enabled);
        et.setHint("[Job Position]");
        et.setHintTextColor(0xFF808080);

        if(!educationMajor.equals("")) {
            et.setText(educationMajor);
            et.setVisibility(VISIBLE); //set visible when loaded
        }

        return et;
    }

    //[END] ----------------------------------------------------------------------------------------
}
