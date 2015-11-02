package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by timka on 10/18/2015.
 */
public class ProjectsSection extends ProfileSection {

    //PRIVATE VARIABLES
    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;


    //CONSTRUCTOR [START] --------------------------------------------------------------------------
    public ProjectsSection(Context context){
        super(context);
        this.context = context;

        initialize();
    }
    //[END] ----------------------------------------------------------------------------------------



    //UTILITY FUNCTIONS [START] --------------------------------------------------------------------
    public void addElement()
    {
        //create a row layout
        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(blockLayoutParams);

        //add school to row
        rl.addView(createProjectView());

        //add delete button to row
        rl.addView(createMinusButton(list.size() + 1000));

        //add project description
        rl.addView(createDescriptionView());


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
            EditText projectET = (EditText) row.getChildAt(0);
            ImageButton ib = (ImageButton) row.getChildAt(1);
            EditText descriptionET = (EditText) row.getChildAt(2);

            //check if current item's bullet is empty

            if(projectET.getText().toString().equals("") && descriptionET.getText().toString().equals("")){
                //if empty, remove the entire item
                i.remove();
                removeRow(row.getChildAt(0)); //pick any random child for matching the format
            }
            else {
                //otherwise, simply hide them
                projectET.setEnabled(false);
                ib.setVisibility(INVISIBLE); //hide minus button
                descriptionET.setEnabled(false);
            }
        }
    }

    //fetches data from activity
    public ArrayList<ArrayList<String>> getData()
    {
        //          0          1
        //rows of <project, description> pairs
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for(int i = 0; i < list.size(); ++i) {
            RelativeLayout row = list.get(i);

            EditText projectET = (EditText) row.getChildAt(0);
            EditText descriptionET = (EditText) row.getChildAt(2);

            ArrayList<String> pair = new ArrayList<>();
            pair.add(projectET.getText().toString());
            pair.add(descriptionET.getText().toString());
            data.add(pair);
        }

        return data;
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

    private EditText createProjectView()
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
        et.requestFocus(); //put on cursor
        et.setHint("[Project]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        return et;
    }

    private EditText createDescriptionView()
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
        et.setHint("[Description]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        return et;
    }
    //[END] ----------------------------------------------------------------------------------------
}
