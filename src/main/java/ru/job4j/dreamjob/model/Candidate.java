package ru.job4j.dreamjob.model;

import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String city;
    private int cityId;

    public Candidate() {
    }

    public Candidate(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public Candidate(int id, String name, int cityId) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id
                && Objects.equals(name, candidate.name)
                && Objects.equals(city, candidate.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city);
    }

    @Override
    public String toString() {
        return "Candidate: "
                + "id=" + id
                + ", name='" + name
                + ", city='" + city;
    }
}
