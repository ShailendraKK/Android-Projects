package com.shailendra.photonotesgallery.providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.shailendra.photonotesgallery.BuildConfig;
import com.shailendra.photonotesgallery.accounts.Account;
import com.shailendra.photonotesgallery.providers.base.ProviderBase;

/**
 * @author Shirwa Mohamed (shirwaM)
 */
public abstract class OldAccountProvider extends ProviderBase {

    public final static Uri CONTENT_URI = Uri.parse("content://" + BuildConfig.APPLICATION_ID + ".accounts");
    private final static String COLUMNS = "_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, type INTEGER";

    public OldAccountProvider() {
        super("account", COLUMNS);
    }

    public static Account add(Context context, String name, int type) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("type", type);
        context.getContentResolver().insert(CONTENT_URI, values);
        Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        cursor.moveToLast();
        int id = cursor.getInt(0);
        Account acc = null;
        switch (type) {
        }
        cursor.close();
        return acc;
    }

    public static void remove(Context context, Account account) {
        context.getContentResolver().delete(CONTENT_URI, "_id = " + account.getId(), null);
    }
}