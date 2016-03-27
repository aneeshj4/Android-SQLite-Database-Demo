package demo.android.com.androiddatabasesdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aneeshjindal on 3/11/16.
 */
public class CommentsDatabaseHelper extends SQLiteOpenHelper {

    /*When you make changes to the format of the database, such as adding a new column,
    * you must change DATABASE_VERSION. Then, onUpgrade is called and there are SQL commands
    * you use to add the column. This demo does not show this.*/
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Comments.db";

    /*If you are familiar with SQL, you know that commands are typed out in a long String. To create
    * a table, you must create a String that contains the info for it. The String SQL_CREATE_ENTRIES
    * starts with "CREATE_TABLE" (so that the system knows that a table is being created) followed by
    * the table name. Then, the columns are defined. The first _ID is a special one that we want to
    * auto increment as a new item is added, starting at 1, and the type "INTEGER PRIMARY KEY" does
    * exactly that. The other columns are just TEXT types (you can have INTEGER or BLOB too). Each
    * column is defined in the format "<COLUMN_NAME> <TYPE>, " until the last one, which doesn't have
    * a comma.*/
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
        //We give it the String that we created above.
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //onUpgrade is not implemented in this demo
    }
}
