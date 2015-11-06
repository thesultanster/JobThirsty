package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import cs.software.engineering.jobthirsty.util.StringParser;


/**
 * Created by jon on 11/4/2015.
 */
public class JobsSection extends ProfileSection {

    //PRIVATE VARIABLES
    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;

    //CONSTRUCTOR [START] --------------------------------------------------------------------------
    public JobsSection(Context context){
        super(context);
        this.context = context;

        initialize();
    }
    //[END] ----------------------------------------------------------------------------------------



    //UTILITY FUNCTIONS [START] --------------------------------------------------------------------
    public void addElement(String educationText, boolean enabled)
    {
        //create a row layout
        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(blockLayoutParams);

        //add job to row
        rl.addView(createJobView(educationText));

        //add delete button to row
        rl.addView(createMinusButton(list.size() + 1000, enabled));

        //add the row
        this.addView(rl);
        list.add(rl);
    }

    //fetches data from activity
    public ArrayList<String> getData()
    {
        ArrayList<String> data = new ArrayList<>();

        for(int i = 0; i < list.size(); ++i) {
            RelativeLayout row = list.get(i);

            TextView jobText = (TextView) row.getChildAt(0);
            data.add(jobText.getText().toString());
        }

        return data;
    }


    //loads the data to activity
    public void setData(ArrayList<String> data)
    {
        for(int i = 0; i < data.size(); ++i) {
            //parse out each field
            String jobText = data.get(i);

            //set data
            addElement(jobText, false);
        }
    }
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
        //layout params for a row layout
        blockLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private TextView createJobView(String jobText)
    {
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        etLayoutParams.setMargins((int)(displayMetrics.widthPixels*0.025), 0, 0, 0);

        //set up EditText
        TextView tv = new EditText(context);
        tv.setLayoutParams(etLayoutParams);
        tv.setBackground(null);
        tv.setTextColor(0xFF000000);
        tv.setWidth(displayMetrics.widthPixels - (int) (displayMetrics.widthPixels * (0.27)));
        tv.setText(jobText);
        tv.setEnabled(false);
        tv.requestLayout(); //update

        return tv;
    }
    //[END] ----------------------------------------------------------------------------------------
}
