package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class User {

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

    @Past(message = "Дата рождения не может быть в будущем")
    private Date birthDate;


    public void updateFrom(User updatedUser) {
        if (updatedUser.getEmail() != null) {
            this.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getLogin() != null) {
            this.setLogin(updatedUser.getLogin());
        }
        if (updatedUser.getName() != null) {
            this.setName(updatedUser.getName());
        }
        if (updatedUser.getBirthDate() != null) {
            this.setBirthDate(updatedUser.getBirthDate());
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + (name != null ? name : login) + '\'' +
                ", birthDate=" + birthDate.toString() +
                '}';
    }
}