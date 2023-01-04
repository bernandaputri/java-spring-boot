package id.bca7.demoSpring.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotBlank(message = "Book Title is required")
    private String bookTitle;

    @NotBlank(message = "Book Author is required")
    private String bookAuthor;

    @NotBlank(message = "Book Publisher is required")
    private String bookPublisher;

    private String categoryName;
}
