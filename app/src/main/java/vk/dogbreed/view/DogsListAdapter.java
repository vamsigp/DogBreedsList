package vk.dogbreed.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import vk.dogbreed.R;
import vk.dogbreed.databinding.ItemDogBinding;
import vk.dogbreed.model.DogBreed;

public class DogsListAdapter extends RecyclerView.Adapter<DogsListAdapter.DogViewHolder> implements DogListClickListener {

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
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDogBinding view = DataBindingUtil.inflate(layoutInflater, R.layout.item_dog, parent, false);
        // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {

        holder.itemView.setDog(dogBreedList.get(position));
        holder.itemView.setListener(this);

//        ImageView imageView = holder.itemView.findViewById(R.id.imageofdog);
//        TextView lifeSpan = holder.itemView.findViewById(R.id.doglifeSpan);
//        TextView name = holder.itemView.findViewById(R.id.dogname);
//        LinearLayout layout = holder.itemView.findViewById(R.id.doglayout);
//
//        Log.d("TAG", "position = " + position);
//
//        DogBreed d = dogBreedList.get(position);
//        name.setText(dogBreedList.get(position).dogBreed);
//        lifeSpan.setText(dogBreedList.get(position).lifSpan);
//
//        Util.loadImage(imageView, dogBreedList.get(position).imageUrl, Util.getCircularProgressDrawable(imageView.getContext()));
//
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ListFragmentDirections.ActionDetail actionDetail = ListFragmentDirections.actionDetail();
//                actionDetail.setDogUuid(dogBreedList.get(position).uuid);
//                Navigation.findNavController(layout).navigate(actionDetail);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return this.dogBreedList.size();
    }

    @Override
    public void onDogListItemClicked(View view) {
        String uuidString = ((TextView) view.findViewById(R.id.dogId)).getText().toString();
        int uuid = Integer.parseInt(uuidString);

        ListFragmentDirections.ActionDetail actionDetail = ListFragmentDirections.actionDetail();
        actionDetail.setDogUuid(uuid);
        Navigation.findNavController(view).navigate(actionDetail);

    }

    class DogViewHolder extends RecyclerView.ViewHolder {

        public ItemDogBinding itemView;

        public DogViewHolder(ItemDogBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
