
package com.cyber.accounting.movies.app.domain.models.movies;

import com.google.gson.annotations.Expose;

public class Genre {

    @Expose
    private Long id;
    @Expose
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
