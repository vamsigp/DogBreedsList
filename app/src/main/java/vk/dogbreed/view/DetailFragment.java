package vk.dogbreed.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import vk.dogbreed.R;
import vk.dogbreed.model.DogBreed;
import vk.dogbreed.util.Util;
import vk.dogbreed.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    @BindView(R.id.dogImage)
    ImageView dogImage;

    @BindView(R.id.dogName)
    TextView dogName;

    @BindView(R.id.dogPurpose)
    TextView dogPurpose;

    @BindView(R.id.dogTemparament)
    TextView dogTemperament;

    @BindView(R.id.dogLifespan)
    TextView dogLifeSpan;

    DetailViewModel detailViewModel;
    int uuid;


    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getArguments() != null;
        int dogUuid = DetailFragmentArgs.fromBundle(getArguments()).getDogUuid();
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailViewModel.populateDogData(dogUuid);
        loadViews();
    }

    private void loadViews() {

        detailViewModel.dogDetail.observe(this, new Observer<DogBreed>() {
            @Override
            public void onChanged(DogBreed dogBreed) {

                if (getContext() != null && !TextUtils.isEmpty(dogBreed.imageUrl)) {
                    Util.loadImage(dogImage, dogBreed.imageUrl, new CircularProgressDrawable(getContext()));
                }
                dogName.setText(dogBreed.dogBreed);
                dogPurpose.setText(dogBreed.bredFor);
                dogLifeSpan.setText(dogBreed.lifSpan);
                dogTemperament.setText(dogBreed.temperament);
            }
        });

    }

}