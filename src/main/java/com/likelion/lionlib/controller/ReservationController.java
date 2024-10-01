package com.likelion.lionlib.controller;

import com.likelion.lionlib.dto.CountReserveResponse;
import com.likelion.lionlib.dto.CustomUserDetails;
import com.likelion.lionlib.dto.ReservationRequest;
import com.likelion.lionlib.dto.ReserveResponse;
import com.likelion.lionlib.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")

public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
//    public ResponseEntity<ReserveResponse> addReserve(@RequestBody ReservationRequest reservationRequest) {
//        log.info("reservation: {}", reservationRequest);
//        ReserveResponse newReservation = reservationService.addReserve(reservationRequest);
//        return ResponseEntity.ok(newReservation);
//    }

    public ResponseEntity<ReserveResponse> addReserve(@RequestBody ReservationRequest reservationRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        log.info("reservation request: {}", reservationRequest);
        ReserveResponse newReservation = reservationService.addReserve(customUserDetails, reservationRequest);
        return ResponseEntity.ok(newReservation);
    }

    @GetMapping("/reservations/{reservationsId}")
    public ResponseEntity<ReserveResponse> getReserve(@PathVariable("reservationsId") Long reservationsId) {
        log.info("getReservation: {}", reservationsId);

        ReserveResponse reservation = reservationService.getReserve(reservationsId);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{reservationsId}")
    public ResponseEntity<String> deleteReserve(@PathVariable("reservationsId") Long reservationsId) {
        log.info("deleteReservation: {}", reservationsId);

        reservationService.deleteReserve(reservationsId);
        return ResponseEntity.ok("예약이 취소되었습니다.");
    }

    @GetMapping("/members/reservations")
    public ResponseEntity<List<ReserveResponse>> getMemberReservations(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("Request GET reservations for memberId: {}", customUserDetails);
        List<ReserveResponse> reservations = reservationService.getMemberReservations(customUserDetails);
        return ResponseEntity.ok(reservations);
    }

    // 도서 예약 수 조회 API
    @GetMapping("/books/{bookId}/reservations")
    public ResponseEntity<CountReserveResponse> getNumOfBookReservations(@PathVariable("bookId") Long bookId) {
        log.info("Request GET the number of reservations for bookId: {}", bookId);
        CountReserveResponse countResponse = reservationService.getNumOfBookReservations(bookId);
        return ResponseEntity.ok(countResponse);
    }
}