package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.media.Image;
import android.text.InputFilter;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private RelativeLayout.LayoutParams endorseCountLayoutParams;
    private RelativeLayout.LayoutParams ivLayoutParams;


    //List for holding elements
    private ArrayList<RelativeLayout> list;
    private ArrayList<Integer> endorseList;

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
        et.setEms(8);
        et.setSingleLine();
        et.requestFocus(); //put on cursor
        et.setHint("[Add Skill]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        //add EditText to row
        rl.addView(et);

        //Add ImageView for increasing/decreasing endorse
        // should only be visible to everyone else except for you


        //create remove button
        ImageButton iv = new ImageButton(context);
        iv.setId(list.size()+1000); //use id that is higher than 0
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


        //Add TextView for endorse count
        TextView endorseEt = new EditText(context);
        endorseCountLayoutParams.addRule(RelativeLayout.LEFT_OF, iv.getId());
        endorseEt.setLayoutParams(endorseCountLayoutParams);
        endorseEt.setEms(1);
        endorseEt.setTextColor(0xFF000000);
        endorseEt.setText("0");
        endorseEt.setEnabled(false);
        endorseEt.setBackgroundColor(0xFFFFFFFF);
        endorseEt.setGravity(Gravity.CENTER);
        endorseEt.setVisibility(INVISIBLE); //initially invisible
        endorseEt.requestLayout();

        //add TextView to row
        rl.addView(endorseEt);


        //add the skill
        this.addView(rl);
        list.add(rl);
        endorseList.add(0);
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
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
        //list holding rows
        list = new ArrayList<>();
        endorseList = new ArrayList<>();
        
        //layout params for a row layout
        blockLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            100);

        etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        endorseCountLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

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
