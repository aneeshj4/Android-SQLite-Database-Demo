package demo.android.com.androiddatabasesdemo;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by aneeshjindal on 3/11/16.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CustomViewHolder> {

    private Context context;
    private Cursor cursor;

    public CommentsAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        cursor.moveToFirst();
    }

    //Why was it needed that I have this, instead of just doing adapter.cursor = cursor?
    public void updateCursor(Cursor cursor) {
        this.cursor = cursor;
        cursor.moveToFirst();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        cursor.moveToPosition(position); //Why did I have to do this line?
        holder.id = cursor.getInt(0);
        holder.itemNameTextView.setText(cursor.getString(1));
        holder.itemCommentTextView.setText(cursor.getString(2));
        cursor.moveToNext();
        Log.i("test", "" + position);

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        int id;
        TextView itemNameTextView;
        TextView itemCommentTextView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.itemNameTextView = (TextView) itemView.findViewById(R.id.itemNameTextView);
            this.itemCommentTextView = (TextView) itemView.findViewById(R.id.itemCommentTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = itemNameTextView.getText().toString();
                    String comment = itemCommentTextView.getText().toString();
                    MainActivity.createUpdateDialog(id, name, comment);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    MainActivity.createDeleteDialog(cursor.getInt(0));
                    MainActivity.createDeleteDialog(id);
//                    Same issue with cursor.getInt persists, because the cursor row changes
                    return false;
                }
            });
        }
    }
}
