package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by timka on 10/18/2015.
 */
public class ProfileSection extends LinearLayout {

    //PRIVATE VARIABLES
    private Context context;

    //List for holding elements
    private ArrayList<EditText> list;

    //CONSTRUCTORS [START] -------------------------------------------------------------------------
    public ProfileSection(Context context){
        super(context);
        this.context = context;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        this.setLayoutParams(layoutParams);
        this.setOrientation(VERTICAL);

        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) this.getLayoutParams();
        p.setMargins(100, 500, 0, 0);
        this.requestLayout();

        initialize();
    }
    //[END] ----------------------------------------------------------------------------------------



    //UTILITY FUNCTIONS [START] --------------------------------------------------------------------
    public void addElement()
    {
        //Expand layout
        this.getLayoutParams().height += 100;
        this.requestLayout();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //set up Edit Text
        EditText et = new EditText(context);
        et.setLayoutParams(layoutParams);
        et.setBackgroundColor(0xFFffffff);
        et.setId(list.size());
        et.setTextColor(0xFF000000);
        et.setText("ASDF");
        et.requestLayout();
        et.clearFocus();
        et.requestFocus(); //put on cursor


        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) et.getLayoutParams();
        p.setMargins(100, 0, 0, 0);
        et.requestLayout(); //update



        //add the skill
        this.addView(et);
        list.add(et);
    }
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
        list = new ArrayList<>();
    }
    //[END] ----------------------------------------------------------------------------------------
}
