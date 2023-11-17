package org.tensorflow.lite.examples.Blind_aid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedbackHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Feedback.db";
    private static final int DATABASE_VERSION = 1;

    public FeedbackHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE FeedbackEntry (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "feedback TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
