package org.emmek.beu2w2d4.payloads.author;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthorPostDTO(
        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 3, max = 30, message = "surname must be between 3 e 30 chars")
        String name,
        @NotEmpty(message = "Surname cannot be empty")
        @Size(min = 3, max = 30, message = "surname must be between 3 e 30 chars")
        String surname,
        @NotEmpty(message = "Email cannot be empty")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email not valid")
        String email,
        @NotEmpty(message = "Birth date cannot be empty")
        @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Birth date must be in format gg-MM-yyyy")
        String birthDate
) {

}
