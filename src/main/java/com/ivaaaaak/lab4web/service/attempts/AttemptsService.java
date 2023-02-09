package com.ivaaaaak.lab4web.service.attempts;

import com.ivaaaaak.lab4web.controller.requests.AttemptRequest;
import com.ivaaaaak.lab4web.model.attempts.Attempt;
import com.ivaaaaak.lab4web.model.attempts.AttemptsFactory;
import com.ivaaaaak.lab4web.model.users.User;
import com.ivaaaaak.lab4web.repository.AttemptsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttemptsService {

    private final AttemptsRepository attemptsRepository;
    private final AttemptsFactory attemptsFactory;
    private final AreaChecker areaChecker;

    public Attempt addAttempt(final AttemptRequest request, final User user) {
        final var x = request.x();
        final var y = request.y();
        final var r = request.r();
        final var hit = areaChecker.isHit(x, y, r);
        final var newAttempt = attemptsFactory.createAttempt(x, y, r, hit, user);
        return attemptsRepository.save(newAttempt);
    }

    @Transactional
    public void clearAttempts(final User user) {
        attemptsRepository.deleteAllByUserId(user.getId());
    }

    public List<Attempt> getAllAttempts(final User user) {
        return attemptsRepository.findAllByUserId(user.getId());
    }
}
