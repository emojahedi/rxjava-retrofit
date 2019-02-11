package ir.teeleh.elnaz.rxjavaretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ir.teeleh.elnaz.rxjavaretrofit.adapter.GitHubRepoAdapter;
import ir.teeleh.elnaz.rxjavaretrofit.model.GitHubRepo;
import ir.teeleh.elnaz.rxjavaretrofit.network.GitHubClient;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GitHubRepoAdapter mGitHubRepoAdapter = new GitHubRepoAdapter(this);
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.custom_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mGitHubRepoAdapter);


        final EditText editTextUsername = findViewById(R.id.edit_text_username);
        final Button buttonSearch = findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editTextUsername.getText().toString();
                if (!TextUtils.isEmpty(username)) {
                    getStarredRepos(username);
                }
            }
        });

    }


    private void getStarredRepos(String username) {
        Observable<List<GitHubRepo>> observable = GitHubClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        DisposableObserver<List<GitHubRepo>> disposableObserver = observable.subscribeWith(new DisposableObserver<List<GitHubRepo>>() {
            @Override
            public void onNext(List<GitHubRepo> gitHubRepos) {
                mGitHubRepoAdapter.setGitHubRepos(gitHubRepos);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        compositeDisposable.add(disposableObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}