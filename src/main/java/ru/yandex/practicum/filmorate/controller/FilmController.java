package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.film.*;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public CreateFilmRespDto createFilm(@Valid @RequestBody CreateFilmReqDto dto) {
        return filmService.createFilm(dto);
    }

    @PutMapping
    public UpdateFilmRespDto updateFilm(@Valid @RequestBody UpdateFilmReqDto dto) {
        return filmService.updateFilm(dto);
    }

    @GetMapping
    public List<GetFilmRespDto> getAllFilms() {
        return filmService.getAllFilms();
    }
}