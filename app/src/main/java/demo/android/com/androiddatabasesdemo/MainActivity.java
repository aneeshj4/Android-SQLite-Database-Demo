package demo.android.com.androiddatabasesdemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    static SQLiteDatabase database;
    static CommentsAdapter adapter;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = MainActivity.this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                LinearLayout layout = new LinearLayout(builder.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nameEditText = new EditText(builder.getContext());
                nameEditText.setHint("Name");
                layout.addView(nameEditText);

                final EditText commentEditText = new EditText(builder.getContext());
                commentEditText.setHint("Comment");
                layout.addView(commentEditText);

                builder.setView(layout);

                builder.setTitle("Enter new comment")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(CommentsContract.CommentEntry.COLUMN_NAME_ITEM_NAME, nameEditText.getText().toString());
                                contentValues.put(CommentsContract.CommentEntry.COLUMN_NAME_ITEM_COMMENT, commentEditText.getText().toString());
                                database.insert(CommentsContract.CommentEntry.TABLE_NAME, null, contentValues);
                                String[] projection = {CommentsContract.CommentEntry._ID,
                                        CommentsContract.CommentEntry.COLUMN_NAME_ITEM_NAME, CommentsContract.CommentEntry.COLUMN_NAME_ITEM_COMMENT};
                                adapter.updateCursor(database.query(CommentsContract.CommentEntry.TABLE_NAME, projection, null, null, null, null, null));
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        CommentsDatabaseHelper helper = new CommentsDatabaseHelper(getApplicationContext());
        database = helper.getWritableDatabase();

        String[] projection = {CommentsContract.CommentEntry._ID,
                CommentsContract.CommentEntry.COLUMN_NAME_ITEM_NAME, CommentsContract.CommentEntry.COLUMN_NAME_ITEM_COMMENT};

        Cursor cursor = database.query(CommentsContract.CommentEntry.TABLE_NAME, projection, null, null, null, null, null);
        adapter = new CommentsAdapter(getApplicationContext(), cursor);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.search:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final EditText editText = new EditText(builder.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                editText.setLayoutParams(lp);
                builder.setView(editText);

                builder.setTitle("Enter query")
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                search(editText.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void search(String queryString) {
        String[] selectionArgs = {queryString};
        String[] projection = {CommentsContract.CommentEntry._ID,
                CommentsContract.CommentEntry.COLUMN_NAME_ITEM_NAME, CommentsContract.CommentEntry.COLUMN_NAME_ITEM_COMMENT};

        Cursor cursor = database.query(CommentsContract.CommentEntry.TABLE_NAME,
                projection,
                CommentsContract.CommentEntry.COLUMN_NAME_ITEM_NAME + "=?",
                selectionArgs,
                null, null, null);

        adapter.updateCursor(cursor);
        adapter.notifyDataSetChanged();
    }

    public static void createUpdateDialog(final int id, String name, String comment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LinearLayout layout = new LinearLayout(builder.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameEditText = new EditText(builder.getContext());
        nameEditText.setText(name);
        nameEditText.setSelection(name.length());
        layout.addView(nameEditText);

        final EditText commentEditText = new EditText(builder.getContext());
        commentEditText.setText(comment);
        commentEditText.setSelection(comment.length());
        layout.addView(commentEditText);

        builder.setView(layout);

        builder.setTitle("Enter new data")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] whereArgs = {Integer.toString(id)};
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(CommentsContract.CommentEntry.COLUMN_NAME_ITEM_NAME, nameEditText.getText().toString());
                        contentValues.put(CommentsContract.CommentEntry.COLUMN_NAME_ITEM_COMMENT, commentEditText.getText().toString());
                        database.update(CommentsContract.CommentEntry.TABLE_NAME,
                                contentValues,
                                CommentsContract.CommentEntry._ID + "=?",
                                whereArgs);
                        String[] projection = {CommentsContract.CommentEntry._ID,
                                CommentsContract.CommentEntry.COLUMN_NAME_ITEM_NAME, CommentsContract.CommentEntry.COLUMN_NAME_ITEM_COMMENT};
                        adapter.updateCursor(database.query(CommentsContract.CommentEntry.TABLE_NAME, projection, null, null, null, null, null));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public static void createDeleteDialog(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning")
                .setMessage("Are you sure you would like to delete this comment?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] whereArgs = {Integer.toString(id)};
                        database.delete(CommentsContract.CommentEntry.TABLE_NAME,
                                CommentsContract.CommentEntry._ID + "=?",
                                whereArgs);
                        String[] projection = {CommentsContract.CommentEntry._ID,
                                CommentsContract.CommentEntry.COLUMN_NAME_ITEM_NAME, CommentsContract.CommentEntry.COLUMN_NAME_ITEM_COMMENT};
                        adapter.updateCursor(database.query(CommentsContract.CommentEntry.TABLE_NAME, projection, null, null, null, null, null));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
