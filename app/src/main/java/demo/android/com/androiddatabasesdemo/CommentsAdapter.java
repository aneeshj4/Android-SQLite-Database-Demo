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

    /*RecyclerView Adapters do not have a pre-built way of handling Cursors that are returned from
    * queries (ListViews, the predecessor to RecyclerViews, did have something called a CursorAdapter.*/
    private Context context;
    private Cursor cursor;

    public CommentsAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public void updateCursor(Cursor cursor) {
        this.cursor = cursor;
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
        //Moves the cursor to the item who's values are being binded.
        cursor.moveToPosition(position);
        /*Getting a value from a Cursor requires you to know which columns you put in your PROJECTION array.
        * Whenever we are displaying data in this RecyclerView, the PROJECTION contains all 3 of the columns.
        * You must know the type of data in that column, and the position of that column in the PROJECTION
        * array. In this case, the PROJECTION array was essentially {ID, NAME, COMMENT}.
        * cursor.getInt(0) gets the integer in the 0th position. To get the COMMENT for this row in the database,
        * we use getString(2) because the type is TEXT (String) and it is in the 2nd position in the PROJECTION
        * array.*/
        holder.id = cursor.getInt(0);
        holder.itemNameTextView.setText(cursor.getString(1));
        holder.itemCommentTextView.setText(cursor.getString(2));
        /*You can use this to see that onBindViewHolder is only called for items that are on the screen
        * or will be on the screen soon.*/
        Log.i("onBindViewHolder", position + "");
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        /*A ViewHolder exists for every row in the RecyclerView. One of the functions it has is to
        * hold pointers to the Views so the values can be set (such as setting the text in a TextView.
        * Another function is to contain information about the row that may be needed later. The id
        * variable corresponds to the _ID of the row in the database whose values are displayed in
        * this row. This is needed because when a row is clicked and the corresponding row is updated
        * in the database, we used the id to know which row should be updated in the database.*/
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
                    MainActivity.createDeleteDialog(id);
                    return false;
                }
            });
        }
    }
}
