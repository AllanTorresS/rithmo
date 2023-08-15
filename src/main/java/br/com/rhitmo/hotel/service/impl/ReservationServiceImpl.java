package br.com.rhitmo.hotel.service.impl;

import br.com.rhitmo.hotel.controller.dto.ReservationRequestDTO;
import br.com.rhitmo.hotel.controller.dto.ReservationResponseDTO;
import br.com.rhitmo.hotel.model.Reservation;
import br.com.rhitmo.hotel.model.ReservationStatus;
import br.com.rhitmo.hotel.model.Room;
import br.com.rhitmo.hotel.model.RoomStatus;
import br.com.rhitmo.hotel.model.exceptions.BusinessException;
import br.com.rhitmo.hotel.repository.ReservationRepository;
import br.com.rhitmo.hotel.repository.RoomRepository;
import br.com.rhitmo.hotel.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ReservationResponseDTO create(ReservationRequestDTO request, Long roomId) {

        validateCheckinCheckout(request);
        var room = findRoom(roomId);
        validateRoomAvaiableForReservation(request, roomId);
        var entity = mapToEntity(request, room);
        reservationRepository.save(entity);
        return mapToDto(entity);
    }

    @Override
    public void cancel(Long reservationId) {
        var reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()-> new BusinessException(String.format("Não foi encontrado a reserva de ID: '%s'", reservationId)));
        reservation.setStatus(ReservationStatus.CANCELED.getValue());
        reservationRepository.save(reservation);
    }

    @Override
    public void update(ReservationRequestDTO request, Long reservationId, Long roomId) {
        validateCheckinCheckout(request);
        var room = findRoom(roomId);
        validateRoomAvaiableForReservation(request, roomId);
        var reservation = findReservation(reservationId);

        reservation.setCheckinDate(request.getCheckinDate());
        reservation.setCheckoutDate(request.getCheckinDate());
        reservation.setGrossAmount(calculateTotalAmountToPay(room, request));

        reservationRepository.save(reservation);

    }

    private void validateCheckinCheckout(ReservationRequestDTO requestDTO) {
        if (requestDTO.getCheckinDate().isAfter(requestDTO.getCheckoutDate()))
            throw new BusinessException("Data de checkin é maior que a data de checkout");
    }

    private Room findRoom(Long roomId) {
         var entity = roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(String.format("Não foi encontrado o quarto de ID: '%s'", roomId)));

         if(RoomStatus.UNAVAILABLE.getValue().equals(entity.getStatus())){
             throw new BusinessException(String.format("O quarto de ID: '%s' não está ativo", roomId));
         }

         return entity;
    }

    private Reservation findReservation(Long reservationId) {
        var entity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BusinessException(String.format("Não foi encontrado a reserva de ID: '%s'", reservationId)));

        if(ReservationStatus.CANCELED.getValue().equals(entity.getStatus())){
            throw new BusinessException(String.format("A reserva de ID: '%s' não está ativa", reservationId));
        }

        return entity;
    }

    private void validateRoomAvaiableForReservation(ReservationRequestDTO requestDTO, Long roomId) {
        var reservations = reservationRepository.checkRoomAvaible(requestDTO.getCheckinDate(), requestDTO.getCheckoutDate(), roomId);
        if (!reservations.isEmpty()) {
            throw new BusinessException("A data escolhida para reserva não está disponivel");
        }
    }

    private Reservation mapToEntity(ReservationRequestDTO request, Room roomEntity) {

        var entity = mapper.map(request, Reservation.class);
        entity.setRoom(roomEntity);
        entity.setStatus(ReservationStatus.ACTIVE.getValue());
        entity.setGrossAmount(calculateTotalAmountToPay(roomEntity, request));
        return entity;
    }

    private BigDecimal calculateTotalAmountToPay(Room roomEntity, ReservationRequestDTO request) {
        var dailyAmount = roomEntity.getDailyRateValue();
        var days = ChronoUnit.DAYS.between(request.getCheckinDate(), request.getCheckoutDate());

        return dailyAmount.multiply(new BigDecimal(days));

    }

    private ReservationResponseDTO mapToDto(Reservation reservation){
        return mapper.map(reservation, ReservationResponseDTO.class);
    }
}
