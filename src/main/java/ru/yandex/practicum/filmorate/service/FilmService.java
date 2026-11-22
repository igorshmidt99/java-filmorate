package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.dto.film.*;

import java.util.List;

public interface FilmService {
    CreateFilmRespDto createFilm(CreateFilmReqDto dto);

    UpdateFilmRespDto updateFilm(UpdateFilmReqDto dto);

    List<GetFilmRespDto> getAllFilms();
}
