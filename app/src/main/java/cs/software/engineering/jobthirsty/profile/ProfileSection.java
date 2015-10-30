package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

        layoutParams.setMargins(100, height, 0, 0);

        this.setLayoutParams(layoutParams);
        this.setOrientation(VERTICAL);
        this.requestLayout();

    }
    //[END] ----------------------------------------------------------------------------------------
}
