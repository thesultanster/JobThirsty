package cs.software.engineering.jobthirsty.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.util.Collections;
import java.util.List;

import cs.software.engineering.jobthirsty.R;

/**
 * Created by sultankhan on 10/14/15.
 */

 public class NewsfeedRecyclerAdapter extends RecyclerView.Adapter<NewsfeedRecyclerAdapter.MyViewHolder> {

        // emptyList takes care of null pointer exception
        List<NewsfeedRecyclerInfo> data = Collections.emptyList();
        LayoutInflater inflator;
        Context context;

        public NewsfeedRecyclerAdapter(Context context, List<NewsfeedRecyclerInfo> data) {
            this.context = context;
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        public void addRow(NewsfeedRecyclerInfo row){
            data.add(row);
            notifyItemInserted(getItemCount() - 1);
        }

        public void deleteRow(int position) {
            data.remove(position);
            notifyItemRemoved(position);
        }

        // Called when the recycler view needs to create a new row
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            final View view = inflator.inflate(R.layout.row_newsfeed_recycler_view, parent, false);
            MyViewHolder holder = new MyViewHolder(view, new MyViewHolder.MyViewHolderClicks() {
                public void RowClick(View caller, int position) {

                    Intent intent = new Intent(context, Newsfeed.class);
                    intent.putExtra("selectedId", data.get(position).getParseObjectId());
                    view.getContext().startActivity(intent);
                }

            });

            return holder;
        }

        // Setting up the data for each row
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            // This gives us current information list object
            NewsfeedRecyclerInfo current = data.get(position);

            if (current.getNumInvolved() >= 1) {
                ParseFile parseFile = current.getProfileImage(0);
                if (parseFile != null) {
                    holder.involvedProfileImage0.setParseFile(parseFile);
                    holder.involvedProfileImage0.loadInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            // The image is downloaded and displayed
                        }
                    });
                } else {
                    holder.involvedProfileImage0.setImageResource(R.drawable.profile_placeholder);
                }
                holder.involvedProfileImage0.setVisibility(View.VISIBLE);
            }
            if (current.getNumInvolved() >= 2) {
                ParseFile parseFile = current.getProfileImage(1);
                if (parseFile != null) {
                    holder.involvedProfileImage1.setParseFile(parseFile);
                    holder.involvedProfileImage1.loadInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            // The image is downloaded and displayed
                        }
                    });
                } else {
                    holder.involvedProfileImage1.setImageResource(R.drawable.profile_placeholder);
                }
                holder.involvedProfileImage1.setVisibility(View.VISIBLE);
            }

            holder.update.setText(current.getUpdate());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        // Created my custom view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView update;
            ParseImageView involvedProfileImage0;
            ParseImageView involvedProfileImage1;
            public MyViewHolderClicks mListener;

            // itemView will be my own custom layout View of the row
            public MyViewHolder(View itemView, MyViewHolderClicks listener) {
                super(itemView);

                mListener = listener;
                //Link the objects
                update = (TextView) itemView.findViewById(R.id.update);
                involvedProfileImage0 = (ParseImageView) itemView.findViewById(R.id.involvedProfileImage0);
                involvedProfileImage1 = (ParseImageView) itemView.findViewById(R.id.involvedProfileImage1);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
//                switch(v.getId()) {
//                    default:
//                        mListener.RowClick(v, getAdapterPosition());
//                        break;
//                }
            }

            public  interface MyViewHolderClicks {
                 void RowClick(View caller, int position);
            }
        }
    }
