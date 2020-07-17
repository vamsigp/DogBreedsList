package vk.dogbreed.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface DogsApi {

    // https://raw.githubusercontent.com/DevTides/DogsApi/master/dogs.json
    // Host - https://raw.githubusercontent.com
    // End point - /DevTides/DogsApi/master/dogs.json
    @GET("/DevTides/DogsApi/master/dogs.json")
    Single<List<DogBreed>> getDogs();

}
