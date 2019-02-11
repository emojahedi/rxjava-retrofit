package ir.teeleh.elnaz.rxjavaretrofit.network;

import java.util.List;

import io.reactivex.Observable;
import ir.teeleh.elnaz.rxjavaretrofit.model.GitHubRepo;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("users/{user}/starred")
    Observable<List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);
}
