package com.likelion.lionlib.service;

import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.domain.Book;
import com.likelion.lionlib.domain.Reservation;
import com.likelion.lionlib.dto.CountReserveResponse;
import com.likelion.lionlib.dto.ReservationRequest;
import com.likelion.lionlib.dto.ReserveResponse;
import com.likelion.lionlib.dto.CustomUserDetails;
import com.likelion.lionlib.exception.ReservationNotFoundException;
import com.likelion.lionlib.exception.ReserveExistsException;
import com.likelion.lionlib.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final GlobalService globalService;

    // Constructor
    public ReservationService(ReservationRepository reservationRepository, GlobalService globalService) {
        this.reservationRepository = reservationRepository;
        this.globalService = globalService;
    }

    // 예약된 도서 조회
//    public ReserveResponse addReserve(ReservationRequest reservationRequest) {
//        Member member = globalService.findMemberById(reservationRequest.getMemberId());
//        Book book = globalService.findBookById(reservationRequest.getBookId());
//
//        reservationRepository.findByMemberAndBook(member, book).ifPresent(
//                reservation -> { throw new RuntimeException("이미 예약된 도서입니다."); }
//        );
//
//        Reservation newReservation = Reservation.builder()
//                .member(member)
//                .book(book)
//                .build();
//
//        reservationRepository.save(newReservation);
//
//        return ReserveResponse.fromEntity(newReservation);
//    }
        public ReserveResponse addReserve(CustomUserDetails customUserDetails,ReservationRequest reservationRequest) {
        Member member = globalService.findMemberById(customUserDetails.getId());
        Book book = globalService.findBookById(reservationRequest.getBookId());

        reservationRepository.findByMemberAndBook(member, book).ifPresent(
                reservation -> { throw new ReserveExistsException(); }
        );

        Reservation newReservation = Reservation.builder()
                .member(member)
                .book(book)
                .build();

        reservationRepository.save(newReservation);

        return ReserveResponse.fromEntity(newReservation);
    }

    // 예약 정보 조회
    public ReserveResponse getReserve(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
        return ReserveResponse.fromEntity(reservation);
    }
    // 예약 취소
    public void deleteReserve(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
        reservationRepository.deleteById(reservationId);
    }
    // 사용자 예약 정보 조회
    public List<ReserveResponse> getMemberReservations(CustomUserDetails customUserDetails) {
		Member member = globalService.findMemberById(customUserDetails.getId());
        List<Reservation> reserveList = reservationRepository.findByMember(member);
		return reservationRepository.findByMember(member).stream().map((ReserveResponse::fromEntity)).toList();
	}

    // 도서 예약 수 현황 조회
    public CountReserveResponse getNumOfBookReservations(Long bookId) {
        Book book = globalService.findBookById(bookId);
        Long count = reservationRepository.countAllByBook(book);

        return new CountReserveResponse(count);
    }
}
