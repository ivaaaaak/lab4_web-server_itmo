package com.ivaaaaak.lab4web.model.attempts;

import com.ivaaaaak.lab4web.model.users.User;
import org.springframework.stereotype.Component;

@Component
public class AttemptsFactory {

    public Attempt createAttempt(final double x,
                                 final double y,
                                 final double r,
                                 final boolean hit,
                                 final User user) {
        var newAttempt = new Attempt();
        newAttempt.setX(x);
        newAttempt.setY(y);
        newAttempt.setR(r);
        newAttempt.setHit(hit);
        newAttempt.setUser(user);
        return newAttempt;
    }
}