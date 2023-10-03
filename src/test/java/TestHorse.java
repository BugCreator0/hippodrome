import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class TestHorse {

    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    public void nullNameMessage() {
        try {
            new Horse(null, 1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t", "\n\n\n\n\n"})
    public void blankNameException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t", "\n\n\n\n\n"})
    public void blankNameMessage(String name) {
        try {
            new Horse(name, 1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    public void negativeSpeed() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Amigo", -1, 1));
    }

    @Test
    public void speedCannotBeNegative() {
        try {
            new Horse("Amigo", -1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void negativeDistance() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Amigo", 1, -1));
    }

    @Test
    public void distanceCannotBeNegative() {
        try {
            new Horse("Amigo", 1, -1);
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        String amigo = "Amigo";
        Horse horse = new Horse(amigo, 1, 1);
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals(amigo, nameValue);

    }

    @Test
    public void getSpeed() {
        double speed = 100;
        Horse horse = new Horse("Amigo", speed, 1);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void getDistance() {
        double distance = 100;
        Horse horse = new Horse("Amigo", 1, distance);
        assertEquals(distance, horse.getDistance());
    }

    @Test
    public void zeroDistanceByDefault() {
        Horse horse = new Horse("Amigo", 1);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveUsesGetRandom() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("Amigo", 1, 1).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));

        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.4, 0.5})
    void checkGetRandomDoubleMethod(double arg) {
        try (MockedStatic<Horse> mockedStatic =  mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(arg);
            Horse horse = new Horse("Amigo", 2, 3);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(arg);

            horse.move();

            assertEquals(3 + 2 * arg, horse.getDistance());
        }
    }
}
