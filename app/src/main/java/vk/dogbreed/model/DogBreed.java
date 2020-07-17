package vk.dogbreed.model;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class DogBreed {
    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    public String breedId;

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    public String dogBreed;

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    public String lifSpan;

    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    public String breedGroup;

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    public String bredFor;


    @SerializedName("temperament")
    public String temperament;

    @ColumnInfo(name = "dog_url")
    @SerializedName("url")
    public String imageUrl;

    @PrimaryKey(autoGenerate = true)
    public int uuid;

    public DogBreed(String breedId, String dogBreed, String lifSpan, String breedGroup,
                    String bredFor, String temperament, String imageUrl) {
        this.breedId = breedId;
        this.dogBreed = dogBreed;
        this.lifSpan = lifSpan;
        this.breedGroup = breedGroup;
        this.bredFor = bredFor;
        this.temperament = temperament;
        this.imageUrl = imageUrl;
    }


}
