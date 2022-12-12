package module;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class User {
    @NonNull
    private final int id;
    @NonNull
    private String email;
    @NonNull
    private String login;
    private String name;
    private LocalDate birthday;
}
