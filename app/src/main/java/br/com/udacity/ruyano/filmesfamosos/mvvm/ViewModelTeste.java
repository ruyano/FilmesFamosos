package br.com.udacity.ruyano.filmesfamosos.mvvm;

import android.view.View;

import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;

public class ViewModelTeste extends ViewModel {

    private MvvmAdapter adapter;
    private MutableLiveData<Movie> movieSelected;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    private MovieRepository repository;

    void init() {
        adapter = new MvvmAdapter(R.layout.simple_list_item, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
        movieSelected = new MutableLiveData<>();
        repository = new MovieRepository();
    }

    public MvvmAdapter getAdapter() {
        return adapter;
    }

    public void setMoviesInAdapter(List<Movie> movies) {
        this.adapter.setMovies(movies);
        this.adapter.notifyDataSetChanged();
    }

    public MutableLiveData<List<Movie>> getMovies() {
        return repository.getMovies();
    }

    public MutableLiveData<Movie> getMovieSelected() {
        return movieSelected;
    }

    public Movie getMovieAt(Integer index) {
        if (repository.getMovies().getValue() != null
                && repository.getMovies().getValue().size() > index) {
            return repository.getMovies().getValue().get(index);
        }
        return null;
    }

    void fetchList() {
        repository.fetchList();
    }

    public void onItemClick(Integer index) {
        Movie selected = getMovieAt(index);
        if (selected != null)
            movieSelected.setValue(selected);
    }

}
