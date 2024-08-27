package com.sparta.nbcampspringpersonaltask2.entity;

import com.sparta.nbcampspringpersonaltask2.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @Column(name = "comment", length = 500)
    private String comment;

    @Column(name = "writer_name")
    private String writerName;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment(CommentRequestDto commentRequestDto, Schedule schedule) {
        comment = commentRequestDto.getComment();
        writerName = commentRequestDto.getWriterName();
        this.schedule = schedule;
    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        comment = commentRequestDto.getComment();
        writerName = commentRequestDto.getWriterName();
    }
}
