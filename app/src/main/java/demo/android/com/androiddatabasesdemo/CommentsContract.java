package demo.android.com.androiddatabasesdemo;

import android.provider.BaseColumns;

/**
 * Created by aneeshjindal on 3/11/16.
 */
public class CommentsContract {

    public CommentsContract() {}

    public static abstract class CommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "Comments";
        public static final String COLUMN_NAME_ITEM_NAME = "itemName";
        public static final String COLUMN_NAME_ITEM_COMMENT = "itemComment";
    }

}
