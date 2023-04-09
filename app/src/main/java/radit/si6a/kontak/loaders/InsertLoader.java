package radit.si6a.kontak.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import radit.si6a.kontak.db.User;
import radit.si6a.kontak.db.UserDataBase;

public class InsertLoader extends AsyncTaskLoader<Boolean> {
    private User user;
    private UserDataBase db;

    public InsertLoader (@NonNull Context context, User user) {
        super(context);
        this.user = user;
        db = UserDataBase.getInstance(context);
    }

    @Nullable
    @Override
    public Boolean loadInBackground() {
        db.userDao().insertUser(user);
        return null;
    }
}
