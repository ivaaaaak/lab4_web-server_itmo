package com.ivaaaaak.lab4web.controller;

import com.ivaaaaak.lab4web.controller.requests.AttemptRequest;
import com.ivaaaaak.lab4web.controller.responses.AttemptResponse;
import com.ivaaaaak.lab4web.model.attempts.Attempt;
import com.ivaaaaak.lab4web.model.users.User;
import com.ivaaaaak.lab4web.service.attempts.AttemptsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/attempts")
public class AttemptsController {

    private final AttemptsService attemptsService;

    @GetMapping
    public ResponseEntity<List<AttemptResponse>> getAll() { //pagination
        log.info("All attempts request");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        var allAttempts = attemptsService.getAllAttempts(user);
        List<AttemptResponse> response = new LinkedList<>();
        for (Attempt attempt: allAttempts) {
            response.add(new AttemptResponse(attempt.getId(), attempt.getX(), attempt.getY(), attempt.getR(), attempt.isHit()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AttemptResponse> add(@RequestBody @Valid AttemptRequest attemptRequest) {
        log.info("Attempt request: {}", attemptRequest.toString());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        var addedAttempt = attemptsService.addAttempt(attemptRequest, user);
        var response = new AttemptResponse(addedAttempt.getId(), addedAttempt.getX(), addedAttempt.getY(), addedAttempt.getR(), addedAttempt.isHit());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<String> clear() {
        log.info("Clear request");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        attemptsService.clearAttempts(user);
        return ResponseEntity.ok("Successfully cleared");
    }

}
