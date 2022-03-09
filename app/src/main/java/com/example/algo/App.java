package com.example.algo;

import android.app.Application;
import android.content.ContentValues;
import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

public class App extends Application {
    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries().addCallback(onCreateCallback)
                .build();
    }

    public static RoomDatabase.Callback onCreateCallback = new RoomDatabase.Callback() {
        /**
         * Called when the database is created for the first time. This is called after all the
         * tables are created.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            ContentValues contentValues = new ContentValues(3);

            contentValues.put("id", 1);
            contentValues.put("name", "Принято");
            contentValues.put("color", "#7B001C");
            db.insert("status", CONFLICT_REPLACE, contentValues);

            contentValues.clear();
            contentValues.put("id", 2);
            contentValues.put("name", "Отправлено");
            contentValues.put("color", "#FFCF40");
            db.insert("status", CONFLICT_REPLACE, contentValues);

            contentValues.clear();
            contentValues.put("id", 3);
            contentValues.put("name", "Доставлено");
            contentValues.put("color", "#004524");
            db.insert("status", CONFLICT_REPLACE, contentValues);

            super.onCreate(db);
        }
    };

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
