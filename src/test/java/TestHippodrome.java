import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestHippodrome {
    @Test
    public void nullException(){
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }
    @Test
    public void nullExceptionMessage(){
        try{
            new Hippodrome(null);
        } catch (IllegalArgumentException e){
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    @Test
    public void listIsEmptyException(){
        List<Horse> list = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(list));
    }

    @Test
    public void listIsEmptyExceptionMessage(){
        List<Horse> list = new ArrayList<>();
        try{
            new Hippodrome(list);
        } catch (IllegalArgumentException e){
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void getHorses(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            horses.add(new Horse(" " + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void moveHippodrome(){
        //arrange
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            horses.add(mock(Horse.class));
        }

        //act
        new Hippodrome(horses).move();

        //assert
        for (Horse horse:
             horses) {
            verify(horse).move();
        }
    }

    @Test
    public void getWinner(){
        Horse horse1 = new Horse("Amigo1", 1, 2.9);
        Horse horse2 = new Horse("Amigo2", 1, 2);
        Horse horse3 = new Horse("Amigo3", 1, 3);
        Horse horse4 = new Horse("Amigo4", 1, 1);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertSame(horse3, hippodrome.getWinner());
    }
}
