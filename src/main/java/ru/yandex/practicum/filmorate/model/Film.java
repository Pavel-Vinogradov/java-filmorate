package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Film {


    @NotNull
    private long id;


    @NotNull(message = "Название не может быть пустым")
    @NotEmpty(message = "Название не может быть пустым")
    private String name;


    @Size(max = 200, message = "Описание не должно превышать 200 символов")
    private String description;

    @PastOrPresent(message = "Дата релиза не может быть в будущем")
    @Past(message = "Дата релиза должна быть в прошлом")
    private LocalDate releaseDate;


    @Positive(message = "Продолжительность фильма должна быть положительной")
    private float duration;


    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                '}';
    }

}

