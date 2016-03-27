package demo.android.com.androiddatabasesdemo;

import android.provider.BaseColumns;

/**
 * Created by aneeshjindal on 3/11/16.
 */
public class CommentsContract {
    //This class defines what columns we will have in the database.

    public CommentsContract() {}

    public static abstract class CommentEntry implements BaseColumns {
        /*These are Strings that represent the names of the columns in the database. By
        * implementing BaseColumns, we automatically get
        * public static final String _ID = "_id";
        * This _ID row starts at 1 and increases for every row as one is added.*/
        public static final String TABLE_NAME = "Comments";
        public static final String COLUMN_NAME_ITEM_NAME = "itemName";
        public static final String COLUMN_NAME_ITEM_COMMENT = "itemComment";
    }

}
