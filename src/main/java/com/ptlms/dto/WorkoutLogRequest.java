package com.ptlms.dto;

import com.ptlms.entity.WorkoutPart;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkoutLogRequest {
    private LocalDate workoutDate;
    private WorkoutPart workoutPart;
    private String workoutDetail;
    private Integer sets;
    private Integer durationMinutes;
    private String dietPhotoUrl;
    private String dietMemo;
}
