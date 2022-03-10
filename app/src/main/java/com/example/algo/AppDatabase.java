package com.example.algo;

import android.content.ContentValues;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.algo.models.Order;
import com.example.algo.models.OrderDao;
import com.example.algo.models.Status;
import com.example.algo.models.StatusDao;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;
import static com.example.algo.App.onCreateCallback;

@Database(entities = {Order.class, Status.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract OrderDao orderDao();
    public abstract StatusDao statusDao();

    public static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database")
                            .allowMainThreadQueries().addCallback(onCreateCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
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
}
