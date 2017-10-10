package us.devtist.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by chris on 10/8/2017.
 */
public class HttpQueryTask extends AsyncTask<String, Void, String> {


    private MainActivity mainActivity;

    public HttpQueryTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    protected String doInBackground(String... params) {
        String searchResults = null;
        searchResults = getSortByStatus();
        return searchResults;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mainActivity.movieImg = new JsonUtils().saveToStringArray(s, "results", "poster_path");
        for (int i = 0; i < mainActivity.movieImg.size(); i++) {
            mainActivity.movieImg.set(i, "https://image.tmdb.org/t/p/w500" + mainActivity.movieImg.get(i));
            Log.v("Movie URLs", mainActivity.movieImg.get(i));
        }
        mainActivity.gridView.setAdapter(new GridViewAdapater(mainActivity, mainActivity.movieImg));
    }

    private String getSortByStatus() {
        String searchResults = null;
        if (mainActivity.sortBySelection != null) {
            switch (mainActivity.sortBySelection) {
                case "mostpopular":
                    Uri builtUri = Uri.parse("https://api.themoviedb.org/3/movie/popular").buildUpon()
                            .appendQueryParameter("api_key", MainActivity.apiKey)
                            .appendQueryParameter("language", "en-US")
                            .build();

                    URL popularMovieUrl = NetworkUtils.buildUrl(builtUri);

                    try {
                        searchResults = NetworkUtils.HttpMethod("GET", popularMovieUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("Search Results", searchResults);
                    mainActivity.movieIds = new JsonUtils().saveToIntArray(searchResults, "results", "id");
                    return searchResults;

                case "toprated":
                    Uri topRatedUri = Uri.parse("https://api.themoviedb.org/3/movie/top_rated").buildUpon()
                            .appendQueryParameter("api_key", MainActivity.apiKey)
                            .appendQueryParameter("language", "en-US")
                            .build();

                    URL topRatedUrl = NetworkUtils.buildUrl(topRatedUri);

                    try {
                        searchResults = NetworkUtils.HttpMethod("GET", topRatedUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("Search Results", searchResults);
                    mainActivity.movieIds = new JsonUtils().saveToIntArray(searchResults, "results", "id");
                    return searchResults;
            }
        }
        Uri defaultUri = Uri.parse("https://api.themoviedb.org/3/movie/popular").buildUpon()
                .appendQueryParameter("api_key", MainActivity.apiKey)
                .appendQueryParameter("language", "en-US")
                .build();

        URL defaultUrl = NetworkUtils.buildUrl(defaultUri);

        try {
            searchResults = NetworkUtils.HttpMethod("GET", defaultUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("Search Results", searchResults);
        mainActivity.movieIds = new JsonUtils().saveToIntArray(searchResults, "results", "id");
        return searchResults;
    }
}
