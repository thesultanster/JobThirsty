package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cs.software.engineering.jobthirsty.R;

/**
 * Created by timka on 10/18/2015.
 */
public class ProfileSection extends LinearLayout {

    //PRIVATE VARIABLES
    private Context context;

    //Display info
    DisplayMetrics displayMetrics;

    //CONSTRUCTORS [START] -------------------------------------------------------------------------
    public ProfileSection(Context context){
        super(context);
        this.context = context;

        initialize();
    }
    //[END] ----------------------------------------------------------------------------------------



    //UTILITY FUNCTIONS [START] --------------------------------------------------------------------
    protected ImageButton createMinusButton(int id)
    {
        RelativeLayout.LayoutParams ivLayoutParams =  new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ivLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        //create remove button
        ImageButton iv = new ImageButton(context);
        iv.setId(id); //use id that is higher than 0
        ivLayoutParams.setMarginEnd((int) (displayMetrics.widthPixels * (0.015)));
        iv.setLayoutParams(ivLayoutParams);
        iv.setBackgroundResource(R.drawable.minus);
        iv.getLayoutParams().height = 80;
        iv.getLayoutParams().width = 80;

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRow(v);
            }
        });

        return iv;
    }

    //removes the relative layout
    protected void removeRow(View v)
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
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
        //display manager
        displayMetrics = getResources().getDisplayMetrics();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        int height = (int) (displayMetrics.heightPixels*0.0625);

        layoutParams.setMargins(50, height, 25, 0);

        this.setLayoutParams(layoutParams);
        this.setOrientation(VERTICAL);
        this.requestLayout();
    }

    //[END] ----------------------------------------------------------------------------------------
}
