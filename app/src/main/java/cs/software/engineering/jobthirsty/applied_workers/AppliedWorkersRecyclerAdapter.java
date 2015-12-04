package cs.software.engineering.jobthirsty.applied_workers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;

import java.util.Collections;
import java.util.List;

import cs.software.engineering.jobthirsty.job_position.ViewJobPosition;
import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.profile.EmployeeProfileActivity;

/**
 * Created by sultankhan on 10/14/15.
 */

 public class AppliedWorkersRecyclerAdapter extends RecyclerView.Adapter<AppliedWorkersRecyclerAdapter.MyViewHolder> {

        // emptyList takes care of null pointer exception
        List<AppliedWorkersRecyclerInfo> data = Collections.emptyList();
        LayoutInflater inflator;
        Context context;

        public AppliedWorkersRecyclerAdapter(Context context, List<AppliedWorkersRecyclerInfo> data) {
            this.context = context;
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        public void addRow(AppliedWorkersRecyclerInfo row){
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

            final View view = inflator.inflate(R.layout.row_applied_workers_recycler_view, parent, false);
            MyViewHolder holder = new MyViewHolder(view, new MyViewHolder.MyViewHolderClicks() {
                public void RowClick(View caller, int position) {

                    Intent intent = new Intent(context, EmployeeProfileActivity.class);
                    intent.putExtra("isYourself",false);
                    intent.putExtra("userId",data.get(position).getUserId());
                    view.getContext().startActivity(intent);

                }

                @Override
                public void Accept(int position) {
                    data.get(position).Accept();
                    deleteRow(position);
                }

                @Override
                public void Decline(int position) {
                    data.get(position).Decline();
                    deleteRow(position);
                }


            });

            return holder;
        }

        // Setting up the data for each row
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            // This gives us current information list object
            AppliedWorkersRecyclerInfo current = data.get(position);

            holder.name.setText(current.getName());
            holder.position.setText(current.getPosition());
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

            TextView name;
            TextView position;
            TextView quote;

            Button accept;
            Button decline;

            ParseImageView profileImage;

            public MyViewHolderClicks mListener;

            // itemView will be my own custom layout View of the row
            public MyViewHolder(View itemView, MyViewHolderClicks listener) {
                super(itemView);

                mListener = listener;
                //Link the objects
                name = (TextView) itemView.findViewById(R.id.name);
                position = (TextView) itemView.findViewById(R.id.position);
                quote = (TextView) itemView.findViewById(R.id.quote);
                accept = (Button) itemView.findViewById(R.id.accept);
                decline = (Button) itemView.findViewById(R.id.decline);
                profileImage = (ParseImageView) itemView.findViewById(R.id.profileImage);

                itemView.setOnClickListener(this);
                accept.setOnClickListener(this);
                decline.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.decline:
                        mListener.Decline(getAdapterPosition());
                        break;
                    case R.id.accept:
                        mListener.Accept(getAdapterPosition());
                        break;
                    default:
                        mListener.RowClick(v, getAdapterPosition());
                        break;
                }
            }

            public interface MyViewHolderClicks {
                void RowClick(View caller, int position);
                void Accept( int position);
                void Decline( int position);

            }
        }
    }
