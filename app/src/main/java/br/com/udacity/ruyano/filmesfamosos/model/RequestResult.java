package br.com.udacity.ruyano.filmesfamosos.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RequestResult {

    @SerializedName("page")
    private Integer page;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    @SerializedName("results")
    private List<Result> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static ArrayList<Result> getMock(int count) {
        ArrayList<Result> mock = new ArrayList<>();

        for(int i = 0; i<count; i++) {
            Result item = new Result();
            item.setTitle("Teste Mock " + i);
            item.setPosterPath("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRcx0M6Wbwzyw0scnLT1sylUJnnhqF7MsoefKJlvvg_7lzP-ypC9KVx3Zk");
            mock.add(item);
        }

        return mock;
    }

}
