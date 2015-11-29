package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;

import cs.software.engineering.jobthirsty.util.StringParser;

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
    public void addElement(String educationText, String educationMajor, String educationGPA, boolean enabled)
    {
        //create a row layout
        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(blockLayoutParams);

        //add school to row
        rl.addView(createSchoolView(educationText, enabled));

        //add delete button to row
        rl.addView(createMinusButton(list.size() + 1000, enabled));

        //add major
        rl.addView(createMajorView(educationMajor, enabled));

        //add GPA
        rl.addView(createGPA(educationGPA, enabled));


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


    //loads the data to activity
    public void setData(ArrayList<String> data)
    {
        ArrayList<ArrayList<String>> dataParsed = (new StringParser(data, false)).getParsed();
        for(int i = 0; i < dataParsed.size(); ++i) {
            //get experience row
            ArrayList<String> education = dataParsed.get(i);

            //parse out each field
            String educationText = education.get(0);
            String educationMajor = education.get(1);
            String educationGPA = education.get(2);

            //set data
            addElement(educationText, educationMajor, educationGPA, false);
        }
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


    private EditText createSchoolView(String educationText, boolean enabled)
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
        et.setWidth(displayMetrics.widthPixels - (int) (displayMetrics.widthPixels * (0.35)));
        et.setEnabled(enabled);
        et.setHint("[School]");
        et.setHintTextColor(0xFF808080);
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        et.setTypeface(Typeface.DEFAULT_BOLD);


        if(enabled) {
            et.requestFocus(); //put on cursor
        }

        if(!educationText.equals("")) {
            et.setText(educationText);
        }
        et.requestLayout(); //update

        return et;
    }



    private EditText createMajorView(String educationMajor, boolean enabled)
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
        et.setWidth((int) (displayMetrics.widthPixels * 0.55));
        et.setSingleLine();
        et.setEnabled(enabled);
        et.setHint("[Major]");
        et.setHintTextColor(0xFF808080);
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        if(!educationMajor.equals("")) {
            et.setText(educationMajor);
            et.setVisibility(VISIBLE); //set visible when loaded
        }

        return et;
    }

    private EditText createGPA(String educationGPA, boolean enabled)
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
        et.setEms(4);
        et.setSingleLine();
        et.setEnabled(enabled);
        et.setHint("[GPA]");
        et.setHintTextColor(0xFF808080);
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        if(!educationGPA.equals("")) {
            et.setText(educationGPA);
            et.setVisibility(VISIBLE); //set visible when loaded
        }

        return et;
    }
    //[END] ----------------------------------------------------------------------------------------
}
