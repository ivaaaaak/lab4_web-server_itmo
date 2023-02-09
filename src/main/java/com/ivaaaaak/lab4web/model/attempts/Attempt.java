package com.ivaaaaak.lab4web.model.attempts;

import com.ivaaaaak.lab4web.model.users.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "attempts")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x")
    @NotNull
    private double x;

    @Column(name = "y")
    @NotNull
    private double y;

    @Column(name = "r")
    @NotNull
    @Min(1)
    @Max(5)
    private double r;

    @Column(name = "hit")
    @NotNull
    private boolean hit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

}
