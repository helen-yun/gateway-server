package com.pongift.naver.data.model;

import lombok.*;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class Time {
    private String startTime;
    private String endTime;

    private OffsetDateTime startTimeOffset;
    private OffsetDateTime endTimeOffset;
}

