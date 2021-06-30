package com.lanagj.adviseme.data_import.tmdb.discover;

public enum SortBy {
    VOTE_AVERAGE_DESC("vote_average.desc"),
    VOTE_AVERAGE_ASC("vote_average.asc"),
    RELEASE_DATE_DESC("release_date.desc"),
    RELEASE_DATE_ASC("release_date.asc"),
    POPULARITY_DESC("popularity.desc"),
    POPULARITY_ASC("popularity.asc");

    private final String filter;

    SortBy(String filter) {
        this.filter = filter;
    }

    public String getFilter() {

        return filter;
    }
}
