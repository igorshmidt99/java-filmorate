package module;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Film {
    private final int id;
    private final String name;
    private final LocalDate releaseDate;
    private final Duration duration;
    @NonNull
    private String description;
}