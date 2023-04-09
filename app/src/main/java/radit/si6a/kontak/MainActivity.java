package radit.si6a.kontak;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import radit.si6a.kontak.databinding.ActivityMainBinding;
import radit.si6a.kontak.db.User;
import radit.si6a.kontak.loaders.DeleteLoader;
import radit.si6a.kontak.loaders.GetDataLoader;

public class MainActivity extends AppCompatActivity {
    private static final int DATA_LOADER_CODE = 123;
    private static final int DELETE_LOADER_CODE = 124;

    private ActivityMainBinding binding;

    private ActivityResultLauncher<Intent> intentActvityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                userUpated();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getData() {
        showProgressBar();
        LoaderManager.getInstance(this).restartLoader(DATA_LOADER_CODE, null, new LoaderManager.LoaderCallbacks<List<User>>() {
            @NonNull
            @Override
            public Loader<List<User>> onCreateLoader(int id, @Nullable Bundle args) {
                return new GetDataLoader(MainActivity.this);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<List<User>> loader, List<User> data) {
                hideProgressBar();
                initAdapter(data);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<List<User>> loader) {

            }
        }).forceLoad();
    }

    private void initAdapter(List<User> data) {
        KontakViewAdapter kontakViewAdapter = new KontakViewAdapter();
        binding.rvKontak.setLayoutManager(new LinearLayoutManager(this));
        binding.rvKontak.setAdapter(kontakViewAdapter);
        kontakViewAdapter.setData(data);
        kontakViewAdapter.setOnClickListener(new KontakViewAdapter.OnClickListener() {
            @Override
            public void onEditClicked(User user) {
                gotoUpdateUserActivity(user);
            }

            @Override
            public void OnDeleteClicked(int userId) {
                deleteUser(userId);
            }
        });
    }

    private void gotoUpdateUserActivity(User user) {
        Intent intent = new Intent(this, InputActivity.class);
        intent.putExtra("edit", true);
        intent.putExtra("user", user);
        intentActvityResultLauncher.launch(intent);
    }

    private void deleteUser(int userId) {
        showProgressBar();
        Bundle args = new Bundle();
        args.putInt("id", userId);
        LoaderManager.getInstance(this).restartLoader(DELETE_LOADER_CODE, args, new LoaderManager.LoaderCallbacks<Integer>() {
            @NonNull
            @Override
            public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {
                return new DeleteLoader(MainActivity.this, args.getInt("id"));
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
                hideProgressBar();
                if (data != -1) {
                    itemDeleted();
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Integer> loader) {

            }
        }).forceLoad();
    }

    private void itemDeleted() {
        Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
        getData();
    }

    private void userUpated() {
        Toast.makeText(this, "User updated succesfully !", Toast.LENGTH_SHORT).show();
        getData();
    }
    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }


}