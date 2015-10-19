package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
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
public class SkillsSection extends ProfileSection {

    //PRIVATE VARIABLES
    private Context context;


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
        //Expand layout
        this.getLayoutParams().height += 100;
        this.requestLayout();

        LinearLayout.LayoutParams llLP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                100);

        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(llLP);

        RelativeLayout.LayoutParams rlLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        //set up Edit Text
        EditText et = new EditText(context);
        et.setLayoutParams(rlLP);
        et.setBackgroundColor(0xFFffffff);
        et.setTextColor(0xFF000000);
        et.requestFocus(); //put on cursor
        et.setHint("[Add Skill]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        //add to layout
        rl.addView(et);

        rlLP =  new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlLP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rlLP.setMarginEnd(7);

        ImageButton iv = new ImageButton(context);
        iv.setLayoutParams(rlLP);
        iv.setBackgroundResource(R.drawable.addtmp);
        iv.getLayoutParams().height = 120;
        iv.getLayoutParams().width = 120;
        iv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                LinearLayout ll = (LinearLayout) v.getParent().getParent();
                ll.removeView((RelativeLayout) v.getParent());
                ll.getLayoutParams().height -= 100;
                ll.requestLayout();
            }
        });
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
        list = new ArrayList<>();
    }
    //[END] ----------------------------------------------------------------------------------------
}
