package vk.dogbreed.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import vk.dogbreed.model.DogBreed;
import vk.dogbreed.model.DogDao;
import vk.dogbreed.model.DogDatabase;

public class DetailViewModel extends AndroidViewModel {

    public MutableLiveData<DogBreed> dogDetail = new MutableLiveData<DogBreed>();

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void populateDogData(int uuid) {
        new FetchDogTask().execute(uuid);
//
//        DogBreed dog1 = new DogBreed("1", "corgi", "15 years", "", "", "", "");
//        dogDetail.setValue(dog1);
    }

    private class FetchDogTask extends AsyncTask<Integer, Void, DogBreed> {

        @Override
        protected DogBreed doInBackground(Integer... uuid) {
            DogDao dogDao = DogDatabase.getInstance(getApplication()).dogDao();
            return dogDao.getDog(uuid[0]);
        }

        @Override
        protected void onPostExecute(DogBreed dogBreed) {
            super.onPostExecute(dogBreed);
            dogDetail.setValue(dogBreed);
        }
    }
}
