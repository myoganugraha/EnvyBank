package com.example.aqil.envygreen2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<TrashEntitiy>> {
    RecyclerView rvBottle;
    RecyclerView rvCan;
    RecyclerView rvPaper;
    rvAdapter rvAdapterBottle;
    rvAdapter rvAdapterCan;
    rvAdapter rvAdapterPaper;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rvBottle = rootView.findViewById(R.id.home_rvbottle);
        rvCan = rootView.findViewById(R.id.home_rvcan);
        rvPaper = rootView.findViewById(R.id.home_rvpaper);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvAdapterPaper = new rvAdapter(getActivity());
        rvAdapterCan = new rvAdapter(getActivity());
        rvAdapterBottle = new rvAdapter(getActivity());
        rvPaper.setAdapter(rvAdapterPaper);
        rvCan.setAdapter(rvAdapterCan);
        rvBottle.setAdapter(rvAdapterBottle);
        rvBottle.setLayoutManager(linearLayoutManager1);
        rvCan.setLayoutManager(linearLayoutManager2);
        rvPaper.setLayoutManager(linearLayoutManager3);
        rvAdapterPaper.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<ArrayList<TrashEntitiy>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new HTTPClientLoader(getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<TrashEntitiy>> loader, ArrayList<TrashEntitiy> trashEntitiys) {
        ArrayList listBottle = new ArrayList();
        ArrayList listCan = new ArrayList();
        ArrayList listPaper = new ArrayList();

        for (int i = 0; i < trashEntitiys.size(); i++) {
            Log.d("TAG", "onLoadFinished: " + trashEntitiys.get(i).category);
            switch (trashEntitiys.get(i).category) {

                case "botol":
                    listBottle.add(trashEntitiys.get(i));
                    break;
                case "kertas":
                    listPaper.add(trashEntitiys.get(i));
                    break;
                case "kaleng":
                    listCan.add(trashEntitiys.get(i));
                    break;

            }
        }
        rvAdapterPaper.setListMvContent(listPaper);
        rvAdapterCan.setListMvContent(listCan);
        rvAdapterBottle.setListMvContent(listBottle);
        rvAdapterCan.notifyDataSetChanged();
        rvAdapterBottle.notifyDataSetChanged();
        rvAdapterPaper.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<TrashEntitiy>> loader) {
        rvAdapterPaper.setListMvContent(null);
    }

    public void onRefresh() {
        getLoaderManager().restartLoader(0, null, this);
    }
}
