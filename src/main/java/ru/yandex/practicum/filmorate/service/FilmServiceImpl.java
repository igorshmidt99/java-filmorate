package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.film.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    @Override
    public CreateFilmRespDto createFilm(CreateFilmReqDto dto) {
        Film film = Film.builder().name(dto.getName()).description(dto.getDescription())
                .releaseDate(dto.getReleaseDate()).duration(dto.getDuration()).build();
        film = filmRepository.createFilm(film);
        return CreateFilmRespDto.builder().id(film.getId()).description(film.getDescription())
                .name(film.getName()).releaseDate(film.getReleaseDate())
                .duration(film.getDuration()).build();
    }

    @Override
    public UpdateFilmRespDto updateFilm(UpdateFilmReqDto dto) {
        Film film = filmRepository.getFilm(dto.getId());
        film.setDescription(dto.getDescription());
        film.setName(dto.getName());
        film.setDuration(dto.getDuration());
        film.setReleaseDate(dto.getReleaseDate());
        film = filmRepository.updateFilm(film);
        return UpdateFilmRespDto.builder().id(film.getId()).name(film.getName()).description(film.getDescription())
                .releaseDate(film.getReleaseDate()).duration(film.getDuration()).build();
    }

    @Override
    public List<GetFilmRespDto> getAllFilms() {
        return filmRepository.getAllFilms()
                .stream()
                .map(film -> GetFilmRespDto.builder().id(film.getId()).name(film.getName())
                        .description(film.getDescription()).releaseDate(film.getReleaseDate()).duration(film.getDuration())
                        .build())
                .toList();
    }
}