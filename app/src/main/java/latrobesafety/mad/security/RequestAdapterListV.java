package latrobesafety.mad.security;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RequestAdapterListV extends ArrayAdapter<Request> {

    public RequestAdapterListV(Context context, List<Request> object){
        super(context,0, object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.request_listview,parent,false);
        }

        TextView nameTextView = convertView.findViewById(R.id.name);
        TextView descTextView =  convertView.findViewById(R.id.description);
        TextView priorityTextView =  convertView.findViewById(R.id.priority);
        TextView dateTextView =  convertView.findViewById(R.id.date);

        Request request = getItem(position);

        nameTextView.setText(request.getName());
        descTextView.setText(request.getMessage());
        dateTextView.setText("" +request.getCurrentDate());
        priorityTextView.setText(request.getPriority()+"");


        return convertView;
    }
}
