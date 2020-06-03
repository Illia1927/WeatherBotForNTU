package com.ntu.java.weather.model;

public class Query {
    private Long Id;
    private String name;

    public Query(Long id, String name) {
        Id = id;
        this.name = name;
    }

    public Query() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Query{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }
}
