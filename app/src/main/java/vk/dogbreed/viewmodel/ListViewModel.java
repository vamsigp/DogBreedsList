package vk.dogbreed.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import vk.dogbreed.model.DogBreed;
import vk.dogbreed.model.DogDao;
import vk.dogbreed.model.DogDatabase;
import vk.dogbreed.model.DogsApiService;
import vk.dogbreed.util.SharedPreferencesHelper;

public class ListViewModel extends AndroidViewModel {

    private static final long refreshTime = 5 * 60 * 1000 * 1000 * 1000L; // Nano secs
    public MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<List<DogBreed>>();
    public MutableLiveData<Boolean> dogLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private DogsApiService dogsApiService = new DogsApiService();
    private CompositeDisposable disposable = new CompositeDisposable();
    private AsyncTask<List<DogBreed>, Void, List<DogBreed>> insertTask;
    private AsyncTask<Void, Void, List<DogBreed>> retrieveTask;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public ListViewModel(@NonNull Application application) {
        super(application);
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(getApplication());
    }

    public void refresh() {
        long lastUpdateTime = sharedPreferencesHelper.getLastUpdateTime();
        long currentTime = System.nanoTime();

        System.out.println("lastUpdateTime=" + lastUpdateTime);
        if (lastUpdateTime == 0 || currentTime - lastUpdateTime > refreshTime) {
            System.out.println("fetch from remote");
            fetchFromRemote();
        } else {
            System.out.println("fetch from db");
            fetchFromDatabase();
        }
    }

    public void refreshBypassRemote() {
        fetchFromRemote();
    }

    private void fetchFromDatabase() {
        loading.setValue(true);
        retrieveTask = new DogsRetrievalTask();
        retrieveTask.execute();
    }

    private void fetchFromRemote() {
        loading.setValue(true);
        disposable.add(
                dogsApiService.getDogs().subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                            @Override
                            public void onSuccess(List<DogBreed> dogList) {
                                insertTask = new InsertDogsTask();
                                insertTask.execute(dogList);
                                sharedPreferencesHelper.saveUpdateTime(System.nanoTime());
                                Toast.makeText(getApplication(), "Fetch from Network", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                dogLoadError.setValue(true);
                                loading.setValue(false);
                            }
                        }));
    }

    private void dogsRetrieved(List<DogBreed> dogBreeds) {
        dogs.setValue(dogBreeds);
        dogLoadError.setValue(false);
        loading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

        if (insertTask != null) {
            insertTask.cancel(true);
            insertTask = null;
        }

        if (retrieveTask != null) {
            retrieveTask.cancel(true);
            retrieveTask = null;
        }
    }


    private class InsertDogsTask extends AsyncTask<List<DogBreed>, Void, List<DogBreed>> {

        @Override
        protected List<DogBreed> doInBackground(List<DogBreed>... lists) {
            List<DogBreed> list = lists[0];

            DogDao dao = DogDatabase.getInstance(getApplication()).dogDao();
            dao.deleteAllDogs();

            ArrayList<DogBreed> dogBreedArrayList = new ArrayList<>(list);
            List<Long> rowIds = dao.insertAll(dogBreedArrayList.toArray(new DogBreed[0]));

            for (int i = 0; i < rowIds.size(); i++) {
                list.get(i).uuid = rowIds.get(i).intValue();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreedList) {
            super.onPostExecute(dogBreedList);
            dogsRetrieved(dogBreedList);
        }
    }

    private class DogsRetrievalTask extends AsyncTask<Void, Void, List<DogBreed>> {

        @Override
        protected List<DogBreed> doInBackground(Void... voids) {

            DogDao dogDao = DogDatabase.getInstance(getApplication()).dogDao();
            List<DogBreed> allDogs = dogDao.getAllDogs();
            return allDogs;
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreedList) {
            Toast.makeText(getApplication(), "Fetch from Database", Toast.LENGTH_LONG).show();
            dogsRetrieved(dogBreedList);
        }
    }
}
