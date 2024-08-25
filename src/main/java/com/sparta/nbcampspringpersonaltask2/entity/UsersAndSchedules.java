package com.sparta.nbcampspringpersonaltask2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Table(name = "users_and_schedules")
@NoArgsConstructor
public class UsersAndSchedules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public UsersAndSchedules(User user, Schedule schedule) {
        this.user = user;
        this.schedule = schedule;
    }
}
