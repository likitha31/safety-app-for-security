package latrobesafety.mad.security;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class requestAdapter extends RecyclerView.Adapter<requestAdapter.RequestViewHolder> {

    private ArrayList<Request> mRequestList;
    private Context mContext;



    public  class RequestViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;
        public Button start;

        public RequestViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.name);
            mTextView2 = itemView.findViewById(R.id.description);
            mTextView3 = itemView.findViewById(R.id.text_view_priority);
            mTextView4 = itemView.findViewById(R.id.date);
            start = itemView.findViewById(R.id.start);
        }
    }

    public requestAdapter(ArrayList<Request> requestList, Context context) {

        mRequestList = requestList;
        this.mContext = context;
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_cardview, parent, false);
        RequestViewHolder rvh = new RequestViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        final Request currentItem = mRequestList.get(position);

        holder.mTextView1.setText(currentItem.getName());
        holder.mTextView2.setText(currentItem.getMessage());
        holder.mTextView3.setText(currentItem.getPriority() + "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        final Date currentDate = currentItem.getCurrentDate();
        String dateAndTime = sdf.format(currentDate);
        holder.mTextView4.setText(dateAndTime);

        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentItem instanceof Emergency) {
                    Emergency req = (Emergency) currentItem;
                    Intent intent = new Intent(mContext, emergencyOverview.class);
                    intent.putExtra("Item", req);
                    mContext.startActivity(intent);
                }
                else{
                    PickUp req = (PickUp) currentItem;
                    Intent intent = new Intent(mContext, PickUpOverview.class);
                    intent.putExtra("Item", req);
                    mContext.startActivity(intent);
                }

            }
        }
        );
    }

    @Override
    public int getItemCount()
    {
        return mRequestList.size();
    }
}
