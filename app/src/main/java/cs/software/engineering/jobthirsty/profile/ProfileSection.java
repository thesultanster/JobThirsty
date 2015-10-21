package cs.software.engineering.jobthirsty.profile;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cs.software.engineering.jobthirsty.R;

/**
 * Created by timka on 10/18/2015.
 */
public class ProfileSection extends LinearLayout {

    //PRIVATE VARIABLES
    private Context context;


    //CONSTRUCTORS [START] -------------------------------------------------------------------------
    public ProfileSection(Context context){
        super(context);
        this.context = context;

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(100, 120, 0, 0);

        this.setLayoutParams(layoutParams);
        this.setOrientation(VERTICAL);
        this.requestLayout();

        initialize();
    }
    //[END] ----------------------------------------------------------------------------------------



    //UTILITY FUNCTIONS [START] --------------------------------------------------------------------
    //[END] ----------------------------------------------------------------------------------------



    //HELPER FUNCTIONS [START] ---------------------------------------------------------------------
    private void initialize()
    {
    }
    //[END] ----------------------------------------------------------------------------------------
}
