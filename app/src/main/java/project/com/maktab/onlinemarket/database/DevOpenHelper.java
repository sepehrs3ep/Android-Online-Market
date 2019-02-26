package project.com.maktab.onlinemarket.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

public class DevOpenHelper extends DaoMaster.DevOpenHelper {
    public DevOpenHelper(Context context, String name) {
        super(context, name);
    }

    public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
