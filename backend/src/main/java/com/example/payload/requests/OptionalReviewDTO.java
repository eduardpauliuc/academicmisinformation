package com.example.payload.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionalReviewDTO {

    private Boolean status;

    private String reviewMessage;

}
