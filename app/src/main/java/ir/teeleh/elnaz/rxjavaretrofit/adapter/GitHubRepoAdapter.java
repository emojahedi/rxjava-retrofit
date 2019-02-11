package ir.teeleh.elnaz.rxjavaretrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;
import ir.teeleh.elnaz.rxjavaretrofit.R;
import ir.teeleh.elnaz.rxjavaretrofit.model.GitHubRepo;


public class GitHubRepoAdapter extends RecyclerView.Adapter<GitHubRepoAdapter.CustomViewHolder> {
    private List<GitHubRepo> mGitHubRepos = new ArrayList<>();

    private Context mContext;

    public GitHubRepoAdapter(Context context) {
        mContext = context;
    }


    public void setGitHubRepos(@Nullable List<GitHubRepo> repos) {
        if (repos == null) {
            return;
        }
        mGitHubRepos.clear();
        mGitHubRepos.addAll(repos);
        notifyDataSetChanged();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView repoName, repoDesc, repoStars, repoLang;

        CustomViewHolder(View view) {
            super(view);
            repoName = view.findViewById(R.id.text_repo_name);
            repoDesc = view.findViewById(R.id.text_repo_description);
            repoStars = view.findViewById(R.id.text_repo_stars);
            repoLang = view.findViewById(R.id.text_language);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_github_repo, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        GitHubRepo gitHubRepo = mGitHubRepos.get(position);
        holder.repoName.setText(gitHubRepo.name);
        holder.repoDesc.setText(gitHubRepo.description);
        holder.repoStars.setText("Stars: " + gitHubRepo.stargazersCount);
        holder.repoLang.setText("Language: " + gitHubRepo.language);
    }

    @Override
    public int getItemCount() {
        return (mGitHubRepos != null) ? mGitHubRepos.size() : 0;
    }
}