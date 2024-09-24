package com.likelion.lionlib.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.lionlib.domain.Reservation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReserveResponse {
    private Long memberId;
    private Long bookId;

    public static ReserveResponse fromEntity(Reservation reservation) {
        return ReserveResponse.builder()
                .memberId(reservation.getMember().getId())
                .bookId(reservation.getBook().getId())
                .build();
    }
}
