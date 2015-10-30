package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;

import cs.software.engineering.jobthirsty.R;

/**
 * Created by timka on 10/18/2015.
 */
public class ProjectsSection extends ProfileSection {

    //PRIVATE VARIABLES
    private Context context;

    //Display info
    DisplayMetrics displayMetrics;

    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;
    private RelativeLayout.LayoutParams etLayoutParams;
    private RelativeLayout.LayoutParams ivLayoutParams;


    //List for holding elements
    private ArrayList<RelativeLayout> list;

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
            list.get(i).getChildAt(1).setVisibility(VISIBLE); //show delete button
            list.get(i).getChildAt(2).setEnabled(true);
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
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
        //list holding rows
        list = new ArrayList<>();

        //display manager
        displayMetrics = getResources().getDisplayMetrics();

        //layout params for a row layout
        blockLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                250);

        etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        ivLayoutParams =  new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    }

    private EditText createProjectView()
    {
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
        EditText et = new EditText(context);
        et.setLayoutParams(tmpLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setWidth(displayMetrics.widthPixels - (int)(displayMetrics.widthPixels*(0.35)));
        et.requestFocus(); //put on cursor
        et.setHint("[Description]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        return et;
    }
    //[END] ----------------------------------------------------------------------------------------
}
