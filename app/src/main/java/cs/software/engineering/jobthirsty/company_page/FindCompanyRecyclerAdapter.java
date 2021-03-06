package cs.software.engineering.jobthirsty.company_page;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import cs.software.engineering.jobthirsty.R;

/**
 * Created by sultankhan on 10/14/15.
 */

 public class FindCompanyRecyclerAdapter extends RecyclerView.Adapter<FindCompanyRecyclerAdapter.MyViewHolder> {

        // emptyList takes care of null pointer exception
        List<FindCompanyRecyclerInfo> data = Collections.emptyList();
        LayoutInflater inflator;
        Context context;

        public FindCompanyRecyclerAdapter(Context context, List<FindCompanyRecyclerInfo> data) {
            this.context = context;
            inflator = LayoutInflater.from(context);
            this.data = data;
        }

        public void addRow(FindCompanyRecyclerInfo row){
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

            final View view = inflator.inflate(R.layout.row_find_company, parent, false);
            MyViewHolder holder = new MyViewHolder(view, new MyViewHolder.MyViewHolderClicks() {
                public void RowClick(View caller, int position) {

                    Intent intent = new Intent(context, ViewCompanyPage.class);
                    intent.putExtra("selectedId", data.get(position).getParseObjectId());
                    intent.putExtra("companyName", data.get(position).getCompanyTitle());
                    view.getContext().startActivity(intent);


                }


            });

            return holder;
        }

        // Setting up the data for each row
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            // This gives us current information list object
            FindCompanyRecyclerInfo current = data.get(position);

            holder.name.setText(current.getCompanyTitle());
            holder.tagline.setText(current.getCompanyTagline());
            //holder.body.setText(current.getBody());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        // Created my custom view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView name;
            TextView tagline;
            //TextView body;

            public MyViewHolderClicks mListener;

            // itemView will be my own custom layout View of the row
            public MyViewHolder(View itemView, MyViewHolderClicks listener) {
                super(itemView);

                mListener = listener;
                //Link the objects
                name = (TextView) itemView.findViewById(R.id.companyName);
                tagline = (TextView) itemView.findViewById(R.id.companyTagline);
                //body = (TextView) itemView.findViewById(R.id.body);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    default:
                        mListener.RowClick(v, getAdapterPosition());
                        break;
                }
            }

            public  interface MyViewHolderClicks {
                void RowClick(View caller, int position);
            }
        }
    }
