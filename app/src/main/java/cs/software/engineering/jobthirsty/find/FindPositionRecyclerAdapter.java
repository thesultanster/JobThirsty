package cs.software.engineering.jobthirsty.find;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import cs.software.engineering.jobthirsty.JobPosition;
import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.mail.Mail;

/**
 * Created by sultankhan on 10/14/15.
 */

 public class FindPositionRecyclerAdapter extends RecyclerView.Adapter<FindPositionRecyclerAdapter.MyViewHolder> {

        // emptyList takes care of null pointer exception
        List<FindPositionRecyclerInfo> data = Collections.emptyList();
        LayoutInflater inflator;
        Context context;

        public FindPositionRecyclerAdapter(Context context, List<FindPositionRecyclerInfo> data) {
            this.context = context;
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        public void addRow(FindPositionRecyclerInfo row){
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

            final View view = inflator.inflate(R.layout.row_find_position_recycler_view, parent, false);
            MyViewHolder holder = new MyViewHolder(view, new MyViewHolder.MyViewHolderClicks() {
                public void RowClick(View caller, int position) {

                    Intent intent = new Intent(context, JobPosition.class);
                    intent.putExtra("selectedId", data.get(position).getParseObjectId());
                    intent.putExtra("positionTitle", data.get(position).getPositionTitle());
                    view.getContext().startActivity(intent);

                }

                public void Apply(int position){



                }

            });

            return holder;
        }

        // Setting up the data for each row
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            // This gives us current information list object
            FindPositionRecyclerInfo current = data.get(position);

            holder.sender.setText(current.getPositionTitle());
            holder.subject.setText(current.getSubject());
            holder.body.setText(current.getBody());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        // Created my custom view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView sender;
            TextView subject;
            TextView body;

            Button apply;

            public MyViewHolderClicks mListener;

            // itemView will be my own custom layout View of the row
            public MyViewHolder(View itemView, MyViewHolderClicks listener) {
                super(itemView);

                mListener = listener;
                //Link the objects
                sender = (TextView) itemView.findViewById(R.id.sender);
                subject = (TextView) itemView.findViewById(R.id.subject);
                body = (TextView) itemView.findViewById(R.id.body);
                apply = (Button) itemView.findViewById(R.id.apply);

                itemView.setOnClickListener(this);
                apply.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.apply:
                        mListener.Apply(getAdapterPosition());
                        break;
                    default:
                        mListener.RowClick(v, getAdapterPosition());
                        break;
                }
            }

            public  interface MyViewHolderClicks {
                void RowClick(View caller, int position);
                void Apply(int position);
            }
        }
    }
