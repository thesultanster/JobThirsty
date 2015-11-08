package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
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
    public void addElement(String jobText, String objectId, boolean enabled)
    {
        //create a row layout
        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(blockLayoutParams);

        //add job to row
        rl.addView(createJobView(jobText));

        //add delete button to row
        rl.addView(createMinusButton(objectId, enabled));

        //add the row
        this.addView(rl);
        list.add(rl);
    }

    public void enableEdit()
    {
        for(int i = 0; i < list.size(); ++i) {
            list.get(i).getChildAt(1).setVisibility(VISIBLE); //show minus button
        }
    }

    public void disableEdit()
    {
        for(int i = 0; i < list.size(); ++i) {
            list.get(i).getChildAt(1).setVisibility(INVISIBLE); //hide minus button
        }
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
    public void setData(ArrayList<ArrayList<String>> data)
    {
        for(int i = 0; i < data.size(); ++i) {
            ArrayList<String> row = data.get(i);

            //parse out each field
            String jobText = row.get(0);
            String objectId = row.get(1);

            //set data
            addElement(jobText, objectId, false);
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

    protected ImageButton createMinusButton(final String objectId, boolean enabled)
    {
        RelativeLayout.LayoutParams ivLayoutParams =  new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        //create remove button
        ImageButton iv = new ImageButton(context);
        iv.setContentDescription(objectId);//use this as description
        ivLayoutParams.setMarginEnd((int) (displayMetrics.widthPixels * (0.015)));
        iv.setLayoutParams(ivLayoutParams);
        iv.setBackgroundResource(R.drawable.minus);
        iv.getLayoutParams().height = 80;
        iv.getLayoutParams().width = 80;

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove from database
                ParseQuery<ParseObject> q = ParseQuery.getQuery("Position");
                q.whereEqualTo("objectId", objectId);

                q.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if(list.size()!=0) {
                            list.get(0).deleteInBackground();
                        }
                    }
                });
                removeRow(v);
                RelativeLayout toRemove = (RelativeLayout) v.getParent();
                list.remove(toRemove);
            }
        });

        if(!enabled) {
            iv.setVisibility(INVISIBLE);
        }

        return iv;
    }
    //[END] ----------------------------------------------------------------------------------------
}
