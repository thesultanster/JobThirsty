package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by timka on 10/18/2015.
 */
public class EducationSection extends ProfileSection {

    //PRIVATE VARIABLES
    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;


    //CONSTRUCTOR [START] --------------------------------------------------------------------------
    public EducationSection(Context context){
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
        rl.addView(createSchoolView());

        //add delete button to row
        rl.addView(createMinusButton(list.size() + 1000));

        //add major
        rl.addView(createMajorView());

        //add GPA
        rl.addView(createGPA());


        //add the row
        this.addView(rl);
        list.add(rl);
    }

    public void enableEdit() {
        for (int i = 0; i < list.size(); ++i){
            list.get(i).getChildAt(0).setEnabled(true);
            list.get(i).getChildAt(0).setVisibility(VISIBLE);
            list.get(i).getChildAt(1).setVisibility(VISIBLE); //show minus button
            list.get(i).getChildAt(2).setEnabled(true);
            list.get(i).getChildAt(2).setVisibility(VISIBLE);
            list.get(i).getChildAt(3).setEnabled(true);
            list.get(i).getChildAt(3).setVisibility(VISIBLE);
        }
    }

    public void disableEdit()
    {
        //iterate through all rows
        for(Iterator<RelativeLayout> i = list.iterator(); i.hasNext(); ) {
            RelativeLayout row = i.next();
            EditText et = (EditText) row.getChildAt(0);
            ImageButton ib = (ImageButton) row.getChildAt(1);
            EditText majorEt = (EditText) row.getChildAt(2);
            EditText gpaEt = (EditText) row.getChildAt(3);

            //check if current item's bullet is empty

            if(et.getText().toString().equals("") && majorEt.getText().toString().equals("") && gpaEt.getText().toString().equals("")){
                //if empty, remove the entire item
                i.remove();
                removeRow(row.getChildAt(0)); //pick any random child for matching the format
            }
            else {
                //otherwise, simply hide them
                et.setEnabled(false);
                ib.setVisibility(INVISIBLE); //hide minus button
                majorEt.setEnabled(false);
                gpaEt.setEnabled(false);

                //hide if empty
                if(et.getText().toString().equals(""))
                    et.setVisibility(INVISIBLE);
                if(majorEt.getText().toString().equals(""))
                    majorEt.setVisibility(INVISIBLE);
                if(gpaEt.getText().toString().equals(""))
                    gpaEt.setVisibility(INVISIBLE);
            }
        }
    }

    //fetches data from activity
    public ArrayList<ArrayList<String>> getData()
    {
        //           0       1  ,  2
        //rows of <school, major, GPA> pairs
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for(int i = 0; i < list.size(); ++i) {
            RelativeLayout row = list.get(i);

            EditText schoolET = (EditText) row.getChildAt(0);
            EditText majorET = (EditText) row.getChildAt(2);
            EditText GPAET = (EditText) row.getChildAt(3);

            ArrayList<String> triplet = new ArrayList<>();
            triplet.add(schoolET.getText().toString());
            triplet.add(majorET.getText().toString());
            triplet.add(GPAET.getText().toString());
            data.add(triplet);
        }

        return data;
    }
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
        //display manager
        displayMetrics = getResources().getDisplayMetrics();

        //layout params for a row layout
        blockLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                200);
    }


    private EditText createSchoolView()
    {
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        etLayoutParams.setMargins((int)(displayMetrics.widthPixels*0.025), 0, 0, 0);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setSingleLine();
        et.setWidth(displayMetrics.widthPixels - (int) (displayMetrics.widthPixels * (0.3)));
        et.requestFocus(); //put on cursor
        et.setHint("[School]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        return et;
    }



    private EditText createMajorView()
    {
        //set up layout params
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.BELOW, list.size() + 1000);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        etLayoutParams.setMargins((int)(displayMetrics.widthPixels*0.025), 0, 0, 0);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setWidth((int)(displayMetrics.widthPixels*0.55));
        et.setSingleLine();
        et.setHint("[Major]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        return et;
    }

    private EditText createGPA()
    {
        //set up layout params
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        etLayoutParams.addRule(RelativeLayout.BELOW, list.size() + 1000);
        etLayoutParams.setMargins(0, 0, (int)(displayMetrics.widthPixels*0.1), 0);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setEms(2);
        et.setSingleLine();
        et.setHint("[GPA]");
        et.setHintTextColor(0xFF808080);
        et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et.requestLayout(); //update

        return et;
    }
    //[END] ----------------------------------------------------------------------------------------
}
