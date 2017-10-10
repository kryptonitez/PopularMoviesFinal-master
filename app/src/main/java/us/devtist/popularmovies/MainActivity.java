package us.devtist.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * PLEASE REMEMBER TO FILL OUT THE apiKey or you will get an error
 */
public class MainActivity extends AppCompatActivity {

    protected ArrayList<String> movieImg;
    protected ArrayList<Integer> movieIds;
    protected GridView gridView;
    protected String sortBySelection;
    protected final static String apiKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridview);
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        sortBySelection = null;
        if (isConnected != false) {
            new HttpQueryTask(this).execute();
        } else {
            Toast toast = Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_LONG);
            toast.show();
        }

        if (savedInstanceState != null) {
            gridView.setSelection(savedInstanceState.getInt("gridView", 0));
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                Log.v("movie id mainactivity", String.valueOf(movieIds.get(position)));
                intent.putExtra("EXTRA_INT", movieIds.get(position));
                intent.putExtra("EXTRA_APIKEY", apiKey);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("gridView", gridView.getFirstVisiblePosition());
        Log.v("outState", String.valueOf(gridView.getFirstVisiblePosition()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mostpopular:
                sortBySelection = "mostpopular";
                new HttpQueryTask(this).execute();
                return true;
            case R.id.toprated:
                sortBySelection = "toprated";
                new HttpQueryTask(this).execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
