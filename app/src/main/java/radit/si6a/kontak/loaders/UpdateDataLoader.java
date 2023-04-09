package radit.si6a.kontak.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import radit.si6a.kontak.db.User;
import radit.si6a.kontak.db.UserDataBase;

public class UpdateDataLoader extends AsyncTaskLoader<Integer> {
    private User user;
    private UserDataBase db;

    public UpdateDataLoader(@NonNull Context context, User user) {
        super(context);
        this.user = this.user;
        db = UserDataBase.getInstance(context);
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        return db.userDao().updateUser(user);
    }
}
