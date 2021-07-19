package guru.springframework.clinic.repositories;

import guru.springframework.clinic.model.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
