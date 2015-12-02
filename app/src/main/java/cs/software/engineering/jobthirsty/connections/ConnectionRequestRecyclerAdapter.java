package cs.software.engineering.jobthirsty.connections;

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

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.newsfeed.Newsfeed;

/**
 * Created by sultankhan on 10/14/15.
 */

 public class ConnectionRequestRecyclerAdapter extends RecyclerView.Adapter<ConnectionRequestRecyclerAdapter.MyViewHolder> {

        // emptyList takes care of null pointer exception
        List<ConnectionRequestRecyclerInfo> data = Collections.emptyList();
        LayoutInflater inflator;
        Context context;

        public ConnectionRequestRecyclerAdapter(Context context, List<ConnectionRequestRecyclerInfo> data) {
            this.context = context;
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        public void addRow(ConnectionRequestRecyclerInfo row){
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

            final View view = inflator.inflate(R.layout.row_connection_request_recycler_view, parent, false);
            MyViewHolder holder = new MyViewHolder(view, new MyViewHolder.MyViewHolderClicks() {
                public void RowClick(View caller, int position) {

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
            ConnectionRequestRecyclerInfo current = data.get(position);

            holder.title.setText(current.getTitle());
            holder.update.setText("Wants to connect with you!");
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        // Created my custom view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView title;
            TextView update;
            Button accept;
            Button decline;

            public MyViewHolderClicks mListener;

            // itemView will be my own custom layout View of the row
            public MyViewHolder(View itemView, MyViewHolderClicks listener) {
                super(itemView);

                mListener = listener;
                //Link the objects
                title = (TextView) itemView.findViewById(R.id.title);
                update = (TextView) itemView.findViewById(R.id.update);
                accept = (Button) itemView.findViewById(R.id.accept);
                decline = (Button) itemView.findViewById(R.id.decline);
                itemView.setOnClickListener(this);
                accept.setOnClickListener(this);
                decline.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.accept:
                        mListener.Accept(getAdapterPosition());
                        break;
                    case R.id.decline:
                        mListener.Decline(getAdapterPosition());
                        break;
                    default:
                        mListener.RowClick(v, getAdapterPosition());
                        break;
                }
            }

            public  interface MyViewHolderClicks {
                 void RowClick(View caller, int position);
                void Accept(int position);
                void Decline(int position);
            }


            void Accept(){

            }
        }



    }
