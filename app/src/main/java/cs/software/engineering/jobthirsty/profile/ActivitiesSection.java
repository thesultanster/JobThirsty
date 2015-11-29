package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.util.TypedValue;
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
public class ActivitiesSection extends ProfileSection {

    //PRIVATE VARIABLES
    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;

    //CONSTRUCTOR [START] --------------------------------------------------------------------------
    public ActivitiesSection(Context context){
        super(context);
        this.context = context;

        initialize();
    }
    //[END] ----------------------------------------------------------------------------------------



    //UTILITY FUNCTIONS [START] --------------------------------------------------------------------
    public void addElement(String activitiesText, boolean enabled)
    {
        //create a row layout
        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(blockLayoutParams);

        //add EditText to row
        rl.addView(createActivityView(activitiesText, enabled));

        //add delete button to row
        rl.addView(createMinusButton(list.size() + 1000, enabled));


        //add the row
        this.addView(rl);
        list.add(rl);
    }

    public void enableEdit() {
        for (int i = 0; i < list.size(); ++i){
            list.get(i).getChildAt(0).setEnabled(true);
            list.get(i).getChildAt(1).setVisibility(VISIBLE); //hide minus button
        }
    }

    public void disableEdit()
    {
        //iterate through all rows
        for(Iterator<RelativeLayout> i = list.iterator(); i.hasNext(); ) {
            RelativeLayout row = i.next();
            EditText et = (EditText) row.getChildAt(0);
            ImageButton ib = (ImageButton) row.getChildAt(1);

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
            }
        }
    }

    //fetches data from activity
    public ArrayList<String> getData()
    {
        ArrayList<String> data = new ArrayList<>();

        for(int i = 0; i < list.size(); ++i) {
            RelativeLayout row = list.get(i);

            EditText et = (EditText) row.getChildAt(0);
            data.add(et.getText().toString());
        }

        return data;
    }

    //loads the data to activity
    public void setData(ArrayList<String> data)
    {
        ArrayList<ArrayList<String>> dataParsed = (new StringParser(data, false)).getParsed();
        for(int i = 0; i < dataParsed.size(); ++i) {
            //get skill row
            ArrayList<String> activity = dataParsed.get(i);

            //parse out each field
            String activitiesText = activity.get(0);

            //set data
            addElement(activitiesText, false);
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

    private EditText createActivityView(String activityText, boolean enabled)
    {
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        etLayoutParams.setMargins((int) (displayMetrics.widthPixels * 0.025), 0, 0, 0);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setSingleLine();
        et.setWidth(displayMetrics.widthPixels - (int) (displayMetrics.widthPixels * (0.25)));
        et.setEnabled(enabled);
        et.setHint("[Activity]");
        et.setHintTextColor(0xFF808080);
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);


        if(enabled) {
            et.requestFocus(); //put on cursor
        }

        if(!activityText.equals("")) {
            et.setText(activityText);
        }

        et.requestLayout(); //update

        return et;
    }
    //[END] ----------------------------------------------------------------------------------------
}
