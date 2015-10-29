package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.text.InputFilter;
import android.text.Layout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cs.software.engineering.jobthirsty.R;

/**
 * Created by timka on 10/18/2015.
 */
public class AwardSection extends ProfileSection {

    //PRIVATE VARIABLES
    private Context context;

    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;
    private RelativeLayout.LayoutParams etLayoutParams;
    private RelativeLayout.LayoutParams ivLayoutParams;


    //List for holding elements
    private ArrayList<RelativeLayout> list;

    //CONSTRUCTOR [START] --------------------------------------------------------------------------
    public AwardSection(Context context){
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
        et.setEms(15);
        et.requestFocus(); //put on cursor
        et.setHint("[Add Award]");
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
                //remove row
                LinearLayout ll = (LinearLayout) v.getParent().getParent();
                ll.removeView((RelativeLayout) v.getParent());

                //wrap content
                RelativeLayout rl = ((RelativeLayout) ll.getParent());
                rl.getLayoutParams().height -= 100;
                rl.requestLayout();
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
        for(int i = 0; i < list.size(); ++i) {
            list.get(i).getChildAt(0).setEnabled(false);
            list.get(i).getChildAt(1).setVisibility(INVISIBLE); //hide minus button
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
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        ivLayoutParams =  new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ivLayoutParams.setMarginEnd(7);
    }
    //[END] ----------------------------------------------------------------------------------------
}
