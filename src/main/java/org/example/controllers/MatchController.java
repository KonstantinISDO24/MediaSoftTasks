package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.controllers.dtos.MatchDto;
import org.example.models.Match;
import org.example.services.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
@Tag(name = "MatchController", description = "API для работы с матчами")
public class MatchController {

    private final MatchService matchService;

    @Operation(description = "Регистрация нового матча")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Матч успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    })
    @PostMapping
    public ResponseEntity<Match> registerMatch(
            @Parameter(description = "Данные для регистрации матча")
            @RequestBody MatchDto matchDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.registerMatch(matchDto));
    }

    @Operation(description = "Получение турнирной таблицы по сезону и дате")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса")
    })
    @GetMapping("/standings")
    public ResponseEntity<Map<String, Map<String, Integer>>> getStandings(
            @Parameter(description = "Сезон для турнирной таблицы")
            @RequestParam String season,
            @Parameter(description = "Дата, на которую необходимо получить таблицу")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Map<String, Map<String, Integer>> standings = matchService.getStandings(season, date);
        return ResponseEntity.ok(standings);
    }
}