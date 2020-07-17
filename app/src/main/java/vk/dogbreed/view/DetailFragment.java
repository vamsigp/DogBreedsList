package vk.dogbreed.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import vk.dogbreed.R;
import vk.dogbreed.databinding.FragmentDetailBinding;
import vk.dogbreed.model.DogBreed;
import vk.dogbreed.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    private DetailViewModel detailViewModel;
    private int uuid;
    private FragmentDetailBinding binding;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        // View view = inflater.inflate(R.layout.fragment_detail, container, false);
        return binding.getRoot();
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
                binding.setDog(dogBreed);
//                if (getContext() != null && !TextUtils.isEmpty(dogBreed.imageUrl)) {
//                    Util.loadImage(dogImage, dogBreed.imageUrl, Util.getCircularProgressDrawable(getContext()));
//                }
//                dogName.setText(dogBreed.dogBreed);
//                dogPurpose.setText(dogBreed.bredFor);
//                dogLifeSpan.setText(dogBreed.lifSpan);
//                dogTemperament.setText(dogBreed.temperament);
            }
        });

    }

}