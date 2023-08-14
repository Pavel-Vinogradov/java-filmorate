package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@Builder
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


    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;


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
        if (updatedUser.getBirthday() != null) {
            this.setBirthday(updatedUser.getBirthday());
        }
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + (name != null ? name : login) + '\'' +
                ", birthDate=" + birthday +
                '}';
    }
}
