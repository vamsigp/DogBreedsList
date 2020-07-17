package vk.dogbreed.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import vk.dogbreed.R;
import vk.dogbreed.model.DogBreed;
import vk.dogbreed.viewmodel.ListViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    @BindView(R.id.listError)
    TextView listError;
    @BindView(R.id.loadingView)
    ProgressBar loadingView;
    @BindView(R.id.dogsList)
    RecyclerView dogsList;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private ListViewModel viewModel;
    private DogsListAdapter dogsListAdapter = new DogsListAdapter(new ArrayList<>());


    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();

        dogsList.setLayoutManager(new LinearLayoutManager(getContext()));
        dogsList.setAdapter(dogsListAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dogsList.setVisibility(View.GONE);
                listError.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
                viewModel.refreshBypassRemote();
                refreshLayout.setRefreshing(false);
            }
        });

        observeViewModel();
    }

    private void observeViewModel() {

        viewModel.dogs.observe(this, new Observer<List<DogBreed>>() {
            @Override
            public void onChanged(List<DogBreed> updatedDogsList) {
                if (updatedDogsList != null && updatedDogsList.size() > 0) {
                    dogsList.setVisibility(View.VISIBLE);
                    dogsListAdapter.updateDogsList((ArrayList<DogBreed>) updatedDogsList);
                }
            }
        });

        viewModel.dogLoadError.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null) {
                    listError.setVisibility(isError ? View.VISIBLE : View.GONE);
                }
            }
        });

        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null) {
                    loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    if (isLoading) {
                        listError.setVisibility(View.GONE);
                        dogsList.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

}