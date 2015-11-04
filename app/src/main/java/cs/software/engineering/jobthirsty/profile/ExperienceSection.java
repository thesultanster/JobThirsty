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
 * Created by timka on 10/18/2015.
 */
public class ExperienceSection extends ProfileSection {

    //PRIVATE VARIABLES
    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;


    //CONSTRUCTOR [START] --------------------------------------------------------------------------
    public ExperienceSection(Context context){
        super(context);
        this.context = context;

        initialize();
    }
    //[END] ----------------------------------------------------------------------------------------



    //UTILITY FUNCTIONS [START] --------------------------------------------------------------------
    public void addElement(String experienceText, String experienceDescription, boolean enabled)
    {
        //create a row layout
        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(blockLayoutParams);

        //add school to row
        rl.addView(createExperienceView(experienceText, enabled));

        //add delete button to row
        rl.addView(createMinusButton(list.size() + 1000, enabled));

        //add project description
        rl.addView(createDescriptionView(experienceDescription, enabled));


        //add the row
        this.addView(rl);
        list.add(rl);
    }

    public void enableEdit() {
        for (int i = 0; i < list.size(); ++i){
            list.get(i).getChildAt(0).setEnabled(true);
            list.get(i).getChildAt(0).setVisibility(VISIBLE);
            list.get(i).getChildAt(1).setVisibility(VISIBLE); //show delete button
            list.get(i).getChildAt(2).setEnabled(true);
            list.get(i).getChildAt(2).setVisibility(VISIBLE);
        }
    }

    public void disableEdit()
    {
        //iterate through all rows
        for(Iterator<RelativeLayout> i = list.iterator(); i.hasNext(); ) {
            RelativeLayout row = i.next();
            EditText experienceET = (EditText) row.getChildAt(0);
            ImageButton ib = (ImageButton) row.getChildAt(1);
            EditText descriptionET = (EditText) row.getChildAt(2);

            //check if current item's bullet is empty

            if(experienceET.getText().toString().equals("") && descriptionET.getText().toString().equals("")){
                //if empty, remove the entire item
                i.remove();
                removeRow(row.getChildAt(0)); //pick any random child for matching the format
            }
            else {
                //otherwise, simply hide them
                experienceET.setEnabled(false);
                ib.setVisibility(INVISIBLE); //hide minus button
                descriptionET.setEnabled(false);

                if(experienceET.getText().toString().equals(""))
                    experienceET.setVisibility(INVISIBLE);
                if(descriptionET.getText().toString().equals(""))
                    descriptionET.setVisibility(INVISIBLE);
            }
        }
    }

    //fetches data from activity
    public ArrayList<ArrayList<String>> getData()
    {
        //          0          1
        //rows of <experience, description> pairs
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for(int i = 0; i < list.size(); ++i) {
            RelativeLayout row = list.get(i);

            EditText experienceET = (EditText) row.getChildAt(0);
            EditText descriptionET = (EditText) row.getChildAt(2);

            ArrayList<String> pair = new ArrayList<>();
            pair.add(experienceET.getText().toString());
            pair.add(descriptionET.getText().toString());
            data.add(pair);
        }

        return data;
    }

    //loads the data to activity
    public void setData(ArrayList<String> data)
    {
        ArrayList<ArrayList<String>> dataParsed = (new StringParser(data, false)).getParsed();
        for(int i = 0; i < dataParsed.size(); ++i) {
            //get experience row
            ArrayList<String> experience = dataParsed.get(i);

            //parse out each field
            String experienceText = experience.get(0);
            String experienceDescription = experience.get(1);

            //set data
            addElement(experienceText, experienceDescription, false);
        }
    }
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
        //layout params for a row layout
        blockLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private EditText createExperienceView(String experienceText, boolean enabled)
    {
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        etLayoutParams.setMargins((int) (displayMetrics.widthPixels * 0.025), 0, 0, 0);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setSingleLine();
        et.setWidth(displayMetrics.widthPixels - (int) (displayMetrics.widthPixels * (0.25)));
        et.setEnabled(enabled);
        et.setHint("[Experience]");
        et.setHintTextColor(0xFF808080);

        if(enabled) {
            et.requestFocus(); //put on cursor
        }

        if(!experienceText.equals("")) {
            et.setText(experienceText);
        }
        et.requestLayout(); //update

        return et;
    }

    private EditText createDescriptionView(String experienceDescription, boolean enabled)
    {
        RelativeLayout.LayoutParams tmpLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tmpLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        tmpLayoutParams.addRule(RelativeLayout.BELOW, list.size() + 1000);
        tmpLayoutParams.setMargins((int)(displayMetrics.widthPixels*(0.05)), 0, 0, 0);

        //set up EditText
        final EditText et = new EditText(context);
        et.setLayoutParams(tmpLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setWidth(displayMetrics.widthPixels - (int) (displayMetrics.widthPixels * (0.35)));
        et.setEnabled(enabled);
        et.setHint("[Description]");
        et.setHintTextColor(0xFF808080);

        if(!experienceDescription.equals("")) {
            et.setText(experienceDescription);
            et.setVisibility(VISIBLE); //set visible when loaded
        }

        et.requestLayout(); //update

        return et;
    }
    //[END] ----------------------------------------------------------------------------------------
}
