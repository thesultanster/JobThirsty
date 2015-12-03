package cs.software.engineering.jobthirsty.find_workers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;


import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;

import java.util.Collections;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.profile.EmployeeProfileActivity;

/**
 * Created by Haasith on 10/3/2015.
 */
                                                                                                                         public class FindWorkerRecyclerAdapter extends RecyclerView.Adapter<FindWorkerRecyclerAdapter.MyViewHolder> {

    // emptyList takes care of null pointer exception
    List<FindWorkerRecyclerInfo> data = Collections.emptyList();
    LayoutInflater inflator;
    Context context;
    //List<FindWorkerRecyclerInfo>mDataSet;

    public FindWorkerRecyclerAdapter(FindWorker context, List<FindWorkerRecyclerInfo> data) {
        this.context = context;
        inflator = LayoutInflater.from(context);
        this.data = data;
    }


    /*picture = (Bitmap) ex.get("data");
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    picture.compress(Bitmap.CompressFormat.JPEG, 100, stream);
    // get byte array here
    bytearray= stream.toByteArray();*/

    public void addRow(FindWorkerRecyclerInfo row) {
        data.add(row);
        notifyItemInserted(getItemCount() - 1);
    }

    public void clearData() {
        int size = this.data.size();

        data.clear();

        this.notifyItemRangeRemoved(0, size);
    }

    // Called when the recycler view needs to create a new row
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = inflator.inflate(R.layout.row_find_worker, parent, false);
        MyViewHolder holder = new MyViewHolder(view, new MyViewHolder.MyViewHolderClicks() {

            public void rowClick(View caller, int position) {
                android.util.Log.d("rowClick", "rowClicks");

                Intent intent = new Intent(context, EmployeeProfileActivity.class);
                intent.putExtra("isYourself",false);
                intent.putExtra("userId",data.get(position).getParseObjectId());
                view.getContext().startActivity(intent);
            }


        });
        return holder;
    }

    // Setting up the data for each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // This gives us current information list object
        FindWorkerRecyclerInfo current = data.get(position);

        //holder.userName.setText(current.getUsername());
        holder.firstName.setText(current.getFirstName());
        holder.lastName.setText(current.getLastName());
        ParseFile parseFile = current.getProfileImage();
        if (parseFile != null) {
            holder.profileImage.setParseFile(parseFile);
            holder.profileImage.loadInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    // The image is downloaded and displayed
                }
            });
        } else {
            holder.profileImage.setImageResource(R.drawable.profile_placeholder);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    // Created my custom view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName;
        TextView firstName;
        TextView lastName;
        ParseImageView profileImage;

        public MyViewHolderClicks mListener;

        // itemView will be my own custom layout View of the row
        public MyViewHolder(View itemView, MyViewHolderClicks listener) {
            super(itemView);

            mListener = listener;
            //Link the objects
            //userName = (TextView) itemView.findViewById(R.id.username);
            lastName = (TextView) itemView.findViewById(R.id.lastname);
            firstName = (TextView) itemView.findViewById(R.id.firstname);
            profileImage = (ParseImageView) itemView.findViewById(R.id.profileImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    mListener.rowClick(v, getAdapterPosition());
                    break;
            }
        }

        public interface MyViewHolderClicks {
            void rowClick(View caller, int position);

        }
    }
}