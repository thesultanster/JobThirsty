package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.text.InputType;
import android.util.DisplayMetrics;
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
public class EducationSection extends ProfileSection {

    //PRIVATE VARIABLES
    private Context context;

    //Display info
    DisplayMetrics displayMetrics;

    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;


    //List for holding elements
    private ArrayList<RelativeLayout> list;

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
            list.get(i).getChildAt(1).setVisibility(VISIBLE); //hide minus button
            list.get(i).getChildAt(2).setVisibility(VISIBLE); //hide endorse count
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
                if(majorEt.getText().equals(""))
                    majorEt.setVisibility(INVISIBLE);
                if(gpaEt.getText().equals(""))
                    gpaEt.setVisibility(INVISIBLE);
            }
        }
    }
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
        //list holding rows
        list = new ArrayList<>();

        //display manager
        displayMetrics = getResources().getDisplayMetrics();

        //layout params for a row layout
        blockLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                250);
    }


    private EditText createSchoolView()
    {
        RelativeLayout.LayoutParams etLayoutParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setSingleLine();
        et.setWidth(displayMetrics.widthPixels - (int) (displayMetrics.widthPixels * (0.25)));
        et.requestFocus(); //put on cursor
        et.setHint("[School]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        return et;
    }



    private EditText createMajorView()
    {
        //set up layout params
        RelativeLayout.LayoutParams tmpLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tmpLayoutParams.addRule(RelativeLayout.BELOW, list.size() + 1000);
        tmpLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(tmpLayoutParams);
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
        RelativeLayout.LayoutParams tmpLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tmpLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tmpLayoutParams.addRule(RelativeLayout.BELOW, list.size() + 1000);
        tmpLayoutParams.setMargins(0, 0, 135, 0);

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(tmpLayoutParams);
        et.setBackground(null);
        et.setTextColor(0xFF000000);
        et.setEms(2);
        et.setSingleLine();
        et.setHint("[GPA]");
        et.setHintTextColor(0xFF808080);
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et.requestLayout(); //update

        return et;
    }
    //[END] ----------------------------------------------------------------------------------------
}
