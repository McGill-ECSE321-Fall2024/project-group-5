package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Review;

public class ReviewResponseDto {
    private int id;
    private String name;

    public ReviewResponseDto(Review model) {
        this.id = model.getId();
        this.name = model.getName();
    }

    public int getId() {
        return id;
    }

    public String getName()  {
        return name;
    }
}
