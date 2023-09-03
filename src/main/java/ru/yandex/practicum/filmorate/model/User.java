package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
public class User {

    @NotNull
    private long id;


    @NotNull(message = "Электронная почта не может быть пустой")
    @NotEmpty(message = "Электронная почта не может быть пустой")
    @Email(message = "Неверный формат электронной почты")
    private String email;


    @NotNull(message = "Логин не может быть пустым")
    @NotEmpty(message = "Логин не может быть пустым")
    @Pattern(regexp = "^[\\S]+$", message = "Логин не может быть пустым")
    private String login;


    private String name;


    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    private Set<Long> friends;

}
