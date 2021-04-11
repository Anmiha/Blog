package ru.example.blog.dto.response;

public class TagDto {

    private Integer id;

    private String name;

    private Float weight;


    public TagDto(final Integer id, final String name, final Float weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public TagDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(final Float weight) {
        this.weight = weight;
    }
}
