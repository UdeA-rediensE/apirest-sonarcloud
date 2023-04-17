package co.udea.api.hero.service;

import co.udea.api.hero.model.Hero;
import co.udea.api.hero.repository.HeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {

    @Mock
    private HeroRepository heroRepository;
    @InjectMocks
    private HeroService heroService;
    private Hero hero;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hero = new Hero(1, "Superman");
    }

    @Test
    void getHero() {
        //Arange: preparacion del entorno
        when(heroRepository.findById(1)).thenReturn(Optional.of(hero));

        //Act: Ejecucion de la accion que se esta probando
        Hero result = heroService.getHero(1);

        //Assert: Verificación de que el resultado de la acción es el esperado.
        assertEquals(hero, result);
    }

    @Test
    void getHeroes() {
        when(heroRepository.findAll()).thenReturn(Collections.singletonList(hero));
        List<Hero> expected = Collections.singletonList(hero);
        List<Hero> result = heroService.getHeroes();
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    void addHero() {
        when(heroRepository.save(hero)).thenReturn(hero);
        Hero result = heroService.addHero(hero);
        assertEquals(hero, result);
    }
    @Test
    void searchHeroes() {
        String search = "man";
        when(heroRepository.findAllByNameContainingIgnoreCase(search)).thenReturn(Collections.singletonList(hero));
        List<Hero> expected = Collections.singletonList(hero);
        List<Hero> result = heroService.searchHeroes(search);
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    void deleteHero() {
        when(heroRepository.findById(1)).thenReturn(Optional.of(hero));
        heroService.deleteHero(1);
        verify(heroRepository, times(1)).deleteById(hero.getId());
    }

    @Test
    void updateHero() {
        Hero updatedHero = new Hero(1, "Batman");
        when(heroRepository.findById(1)).thenReturn(Optional.of(hero));
        when(heroRepository.save(updatedHero)).thenReturn(updatedHero);

        Hero result = heroService.updateHero(updatedHero);
        assertEquals(updatedHero, result);
    }
}