package org.shalim.heybeach.repositories;

import java.util.Set;

import org.shalim.heybeach.model.Photo;
import org.shalim.heybeach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
	Photo findByIdAndOwner(Long id, User owner);

	Set<Photo> findByOwner(User owner);
}
