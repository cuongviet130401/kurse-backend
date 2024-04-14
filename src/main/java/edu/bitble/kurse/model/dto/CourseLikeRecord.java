package edu.bitble.kurse.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseLikeRecord {

    private Integer id;

    private Integer courseId;
    private Integer userId;
    private LocalDateTime createdAt;

}