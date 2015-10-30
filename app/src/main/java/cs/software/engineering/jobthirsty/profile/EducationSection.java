package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
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

    //Layout parameter variables
    private LinearLayout.LayoutParams blockLayoutParams;
    private RelativeLayout.LayoutParams etLayoutParams;
    private RelativeLayout.LayoutParams endorseCountLayoutParams;
    private RelativeLayout.LayoutParams ivLayoutParams;


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

        //add EditText to row
        rl.addView(createSchoolView());

        //add delete button to row
        rl.addView(createMinusButton());

        rl.addView(createMajorView());


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

                //hide empty field for major
                if(majorEt.getText().equals(""))
                    majorEt.setVisibility(INVISIBLE); //show visibility
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
                200);

        etLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        etLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        endorseCountLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        ivLayoutParams =  new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        ivLayoutParams.setMarginEnd(7);
    }

    //removes the relative layout
    private void removeRow(View v)
    {
        if(v.getParent() != null && v.getParent().getParent() != null) {
            //remove row
            LinearLayout ll = (LinearLayout) v.getParent().getParent();
            RelativeLayout rowToRemove = (RelativeLayout) v.getParent();
            int rowHeight = rowToRemove.getHeight();
            ll.removeView(rowToRemove);

            //wrap content
            RelativeLayout rl = ((RelativeLayout) ll.getParent());
            rl.getLayoutParams().height -= rowHeight;
            rl.requestLayout();
        }
    }

    private EditText createSchoolView()
    {
        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(etLayoutParams);
        et.setBackgroundColor(0xFFffffff);
        et.setTextColor(0xFF000000);
        et.setEms(12);
        et.setSingleLine();
        et.requestFocus(); //put on cursor
        et.setHint("[Add School]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        return et;
    }

    private ImageButton createMinusButton()
    {
        //create remove button
        ImageButton iv = new ImageButton(context);
        iv.setId(list.size() + 1000); //use id that is higher than 0
        iv.setLayoutParams(ivLayoutParams);
        iv.setBackgroundResource(R.drawable.minus);
        iv.getLayoutParams().height = 120;
        iv.getLayoutParams().width = 120;

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRow(v);
            }
        });

        return iv;
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
        et.setBackgroundColor(0xFFffffff);
        et.setTextColor(0xFF000000);
        et.setEms(12);
        et.setSingleLine();
        et.requestFocus(); //put on cursor
        et.setHint("[Add Major]");
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
        tmpLayoutParams.addRule(RelativeLayout.BELOW, list.size() + 1000);
        tmpLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        //RelativeLayout.MarginLayoutParams tmpMarginLayoutParams = new RelativeLayout.MarginLayoutParams()

        //set up EditText
        EditText et = new EditText(context);
        et.setLayoutParams(tmpLayoutParams);
        et.setBackgroundColor(0xFFffffff);
        et.setTextColor(0xFF000000);
        et.setEms(12);
        et.setSingleLine();
        et.requestFocus(); //put on cursor
        et.setHint("[Add GPA]");
        et.setHintTextColor(0xFF808080);
        et.requestLayout(); //update

        return et;
    }
    //[END] ----------------------------------------------------------------------------------------
}
