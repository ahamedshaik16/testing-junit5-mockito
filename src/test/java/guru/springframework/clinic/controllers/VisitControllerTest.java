package guru.springframework.clinic.controllers;

import guru.springframework.clinic.model.Pet;
import guru.springframework.clinic.model.Visit;
import guru.springframework.clinic.services.PetService;
import guru.springframework.clinic.services.VisitService;
import guru.springframework.clinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VisitControllerTest {

    @Mock
    VisitService visitService;

//    @Mock
//    PetService petService;

    @Spy
    PetMapService petService;

    @InjectMocks
    VisitController visitController;

    @Test
    void loadPetWithVisit() {
        //given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(1L);
        Pet pet3 = new Pet(3L);
        petService.save(pet);
        petService.save(pet3);

//        given(petService.findById(1L)).willReturn(pet);
        given(petService.findById(1L)).willCallRealMethod();

        //when
        Visit visit = visitController.loadPetWithVisit(1L ,model);

        //then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(1L);
        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    void loadPetWithVisitWithStubbing() {
        //given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(1L);
        Pet pet3 = new Pet(3L);
        petService.save(pet);
        petService.save(pet3);

        given(petService.findById(1L)).willReturn(pet3);

        //when
        Visit visit = visitController.loadPetWithVisit(1L ,model);

        //then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(3L);
        verify(petService, times(1)).findById(anyLong());
    }
}
