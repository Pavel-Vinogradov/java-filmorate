package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Film {
    private long id;

    @NotNull(message = "Название не может быть пустым")
    @NotEmpty(message = "Название не может быть пустым")
    private String name;

    @Size(max = 200, message = "Описание не должно превышать 200 символов")
    private String description;

    @Past(message = "Дата релиза должна быть в прошлом")
    private Date releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительной")
    private float duration;

    public void updateFrom(Film updatedFilm) {
        if (updatedFilm.getName() != null) {
            this.setName(updatedFilm.getName());
        }
        if (updatedFilm.getDescription() != null) {
            this.setDescription(updatedFilm.getDescription());
        }
        if (updatedFilm.getReleaseDate() != null) {
            this.setReleaseDate(updatedFilm.getReleaseDate());
        }
        if (updatedFilm.getDuration() != 0) {
            this.setDuration(updatedFilm.getDuration());
        }
    }
}

