package in.plash.trunext.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.plash.trunext.R;
import in.plash.trunext.util.L;


/**
 * A simple {@link Fragment} subclass.
 */
public class SwipeFragment extends Fragment {

    ViewPager viewPager;
    SwipeAdapter adapter;
    public static Bundle gottenBundel;
    public static int ListSize;
    public static List articleIDsList;
    public static int issueID, categoryID, articleID;
    public static int COUNT = 0;

    boolean flag = false;
    public static int OLDpos, NEWpos;

    public SwipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        articleIDsList = new ArrayList();

        if (getArguments() != null) {
            ListSize = getArguments().getInt("SIZE");
            articleIDsList = getArguments().getIntegerArrayList("ARTICLEIDs");


            int[] jsonBodyPrams = getArguments().getIntArray("articleRequestDetails");
            if (jsonBodyPrams != null) {
                issueID = jsonBodyPrams[0];
                categoryID = jsonBodyPrams[1];
                articleID = jsonBodyPrams[2];
            }

            for (int i = 0; i < articleIDsList.size(); i++) {
                L.m("swipe", " forloop " + articleID + " " + articleIDsList.get(i));
                String id = articleIDsList.get(i).toString();
                int artID = Integer.parseInt(id);

                if (articleID == artID) {
                    COUNT = i;
                    //  viewPager.setCurrentItem(i);
                    NEWpos = i;

                    setFragment();
                    break;
                }
            }
        }

        adapter = new SwipeAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            viewPager.setCurrentItem(NEWpos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setFragment() {
        int artID = Integer.parseInt(articleIDsList.get(COUNT).toString());
        L.m("swipe", " generated ID " + artID + " ID " + articleIDsList.get(COUNT));
        int[] jsonBodyPrams = {issueID, categoryID, artID};
        gottenBundel = new Bundle();
        gottenBundel.putIntArray("articleRequestDetails", jsonBodyPrams);
    }


    public static class SwipeAdapter extends FragmentStatePagerAdapter {
        public SwipeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            L.m("swipe ", "position " + position + " count " + COUNT);
            COUNT = position;
            setFragment();
            Fragment fragment = new ArticleBodyFragment();
            Bundle bundle = gottenBundel;
            fragment.setArguments(bundle);
            return fragment;
        }


        @Override
        public int getCount() {
            return ListSize;
        }
    }
}
