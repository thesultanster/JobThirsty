package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;


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
    public void addElement()
    {
        //create a row layout
        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(blockLayoutParams);

        //add EditText to row
        rl.addView(createSkillView());

        //Add ImageView for increasing/decreasing endorse
        // should only be visible to everyone else except for you


        //add delete button to row
        rl.addView(createMinusButton(list.size() + 1000));

        //Add TextView for endorse count
        rl.addView(createEndorseView());


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
            EditText endorseET = (EditText) row.getChildAt(2);

            ArrayList<String> pair = new ArrayList<>();
            pair.add(skillsET.getText().toString());
            pair.add(endorseET.getText().toString());
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
            100);
    }

    private EditText createSkillView()
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
        et.requestFocus(); //put on cursor
        et.setHint("[Skill]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        return et;
    }

    private TextView createEndorseView()
    {
        RelativeLayout.LayoutParams endorseCountLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        endorseCountLayoutParams.addRule(RelativeLayout.LEFT_OF, list.size() + 1000);

        TextView endorseEt = new EditText(context);
        endorseEt.setLayoutParams(endorseCountLayoutParams);
        endorseEt.setEms(1);
        endorseEt.setTextColor(0xFF000000);
        endorseEt.setText("0");
        endorseEt.setEnabled(false);
        endorseEt.setBackground(null);
        endorseEt.setGravity(Gravity.CENTER);
        endorseEt.setVisibility(INVISIBLE); //initially invisible
        endorseEt.requestLayout();

        return endorseEt;
    }
    //[END] ----------------------------------------------------------------------------------------
}
