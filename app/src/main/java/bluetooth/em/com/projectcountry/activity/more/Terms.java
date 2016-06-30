package bluetooth.em.com.projectcountry.activity.more;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bluetooth.em.com.projectcountry.R;

/**
 * Created by Em on 6/30/2016.
 */
public class Terms extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_terms, container, false);
        return view;
    }
}
