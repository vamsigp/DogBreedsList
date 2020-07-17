package vk.dogbreed.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import vk.dogbreed.R;
import vk.dogbreed.model.DogBreed;
import vk.dogbreed.util.Util;

public class DogsListAdapter extends RecyclerView.Adapter<DogsListAdapter.DogViewHolder> {

    private List<DogBreed> dogBreedList;

    public DogsListAdapter(ArrayList<DogBreed> dogsList) {
        this.dogBreedList = dogsList;
    }

    public void updateDogsList(ArrayList<DogBreed> updatedList) {
        this.dogBreedList.clear();
        this.dogBreedList.addAll(updatedList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        ImageView imageView = holder.itemView.findViewById(R.id.imageofdog);
        TextView lifeSpan = holder.itemView.findViewById(R.id.doglifeSpan);
        TextView name = holder.itemView.findViewById(R.id.dogname);
        LinearLayout layout = holder.itemView.findViewById(R.id.doglayout);

        Log.d("VAMSI", "position = " + position);

        DogBreed d = dogBreedList.get(position);
        name.setText(dogBreedList.get(position).dogBreed);
        lifeSpan.setText(dogBreedList.get(position).lifSpan);

        Util.loadImage(imageView, dogBreedList.get(position).imageUrl, Util.getCircularProgressDrawable(imageView.getContext()));

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListFragmentDirections.ActionDetail actionDetail = ListFragmentDirections.actionDetail();
                actionDetail.setDogUuid(dogBreedList.get(position).uuid);
                Navigation.findNavController(layout).navigate(actionDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.dogBreedList.size();
    }

    class DogViewHolder extends RecyclerView.ViewHolder {

        public View itemView;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
