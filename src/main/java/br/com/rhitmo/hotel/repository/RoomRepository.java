package br.com.rhitmo.hotel.repository;

import br.com.rhitmo.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>,
		JpaSpecificationExecutor<Room> {

	@Query("from Room p left join fetch p.reservations")
	List<Room> findAll();

	Optional<Room> findByIdAndStatus(Long id, Long status);

}
