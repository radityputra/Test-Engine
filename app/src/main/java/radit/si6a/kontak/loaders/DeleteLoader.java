package radit.si6a.kontak.loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import radit.si6a.kontak.db.UserDataBase;

public class DeleteLoader extends AsyncTaskLoader<Integer> {
    private int userId;
    private UserDataBase db;

    public DeleteLoader(@NonNull Context context, int userId) {
        super(context);
        this.userId = userId;
        db = UserDataBase.getInstance(context);
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        return db.userDao().deleteUser(userId);
    }
}
