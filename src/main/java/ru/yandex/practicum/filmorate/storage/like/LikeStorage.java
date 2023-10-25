package ru.yandex.practicum.filmorate.storage.like;

public interface LikeStorage {
    public void likeFilm(Integer id, Integer userId);

    public void deleteLike(Integer id, Integer userId);
}
