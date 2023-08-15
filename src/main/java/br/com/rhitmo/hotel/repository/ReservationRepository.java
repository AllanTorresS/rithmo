package br.com.rhitmo.hotel.repository;

import br.com.rhitmo.hotel.model.Reservation;
import br.com.rhitmo.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>,
		JpaSpecificationExecutor<Room> {

	@Query("select a from Reservation a where (:checkin between a.checkinDate and a.checkoutDate" +
			" or :checkout between a.checkinDate and a.checkoutDate) and a.status = 1 and a.room.id = :roomId")
	List<Reservation> checkRoomAvaible(
			@Param("checkin") LocalDate checkin,
			@Param("checkout") LocalDate checkout,
			@Param("roomId") Long roomId);

}
