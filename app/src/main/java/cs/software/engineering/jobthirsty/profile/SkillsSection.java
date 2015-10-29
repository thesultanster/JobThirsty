package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.media.Image;
import android.text.InputFilter;
import android.text.Layout;
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
public class SkillsSection extends ProfileSection {

    //PRIVATE VARIABLES
    private Context context;

    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;
    private RelativeLayout.LayoutParams etLayoutParams;
    private RelativeLayout.LayoutParams ivLayoutParams;


    //List for holding elements
    private ArrayList<RelativeLayout> list;

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

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackgroundColor(0xFFffffff);
        et.setTextColor(0xFF000000);
        et.setSingleLine();

        //limiting the width
        et.setEms(15);
        int maxLength = 15;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        et.setFilters(fArray);

        et.requestFocus(); //put on cursor
        et.setHint("[Add Skill]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        //add EditText to row
        rl.addView(et);

        //create remove button
        ImageButton iv = new ImageButton(context);
        iv.setLayoutParams(ivLayoutParams);
        iv.setBackgroundResource(R.drawable.minus);
        iv.getLayoutParams().height = 120;
        iv.getLayoutParams().width = 120;

        iv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                removeRow(v);
            }
        });

        //add delete button to row
        rl.addView(iv);


        //add the skill
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
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
        //list holding rows
        list = new ArrayList<>();
        
        //layout params for a row layout
        blockLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            100);

        etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        
        ivLayoutParams =  new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ivLayoutParams.setMarginEnd(7);
    }

    //removes the relative layout
    private void removeRow(View v)
    {
        if(v.getParent() != null && v.getParent().getParent() != null) {
            //remove row
            LinearLayout ll = (LinearLayout) v.getParent().getParent();
            ll.removeView((RelativeLayout) v.getParent());

            //wrap content
            RelativeLayout rl = ((RelativeLayout) ll.getParent());
            rl.getLayoutParams().height -= 100;
            rl.requestLayout();
        }
    }
    //[END] ----------------------------------------------------------------------------------------
}
