package bluetooth.em.com.projectcountry.activity.remittance;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import bluetooth.em.com.projectcountry.R;

/**
 * Created by Em on 6/15/2016.
 */
public class SearchClient  extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.remittance_search);
//        // get the action bar
//        ActionBar actionBar = getActionBar();

        // Enabling Back navigation on Action Bar icon
//        actionBar.setDisplayHomeAsUpEnabled(true);

        //txtQuery = (TextView) findViewById(R.id.txtQuery);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }
     @Override
       public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

         // Associate searchable configuration with the SearchView
         SearchManager searchManager =
                 (SearchManager) getSystemService(Context.SEARCH_SERVICE);
         SearchView searchView =(SearchView) menu.findItem(R.id.action_search).getActionView();
         searchView.setSearchableInfo(
                 searchManager.getSearchableInfo(getComponentName()));
        return true;
    }




    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

         System.out.println("Search Query: " + query);

        }}

}
