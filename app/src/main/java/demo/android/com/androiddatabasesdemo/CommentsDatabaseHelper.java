package demo.android.com.androiddatabasesdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aneeshjindal on 3/11/16.
 */
public class CommentsDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Comments.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CommentsContract.CommentEntry.TABLE_NAME + " (" +
                    CommentsContract.CommentEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    CommentsContract.CommentEntry.COLUMN_NAME_ITEM_NAME + TEXT_TYPE + COMMA_SEP +
                    CommentsContract.CommentEntry.COLUMN_NAME_ITEM_COMMENT + TEXT_TYPE +
                    " )";

    public CommentsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
