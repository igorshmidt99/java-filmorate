package module;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;
}