import org.example.command.Command;
import org.example.command.Priority;
import org.example.exception.InvalidCommandException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommandTest {

    @Test
    void testValidCommand() {
        // Arrange & Act
        Command command = new Command(
                "Check power unit",
                Priority.CRITICAL,
                "Ellen Ripley",
                "12:30:45"
        );

        // Assert
        assertEquals("Check power unit", command.getDescription());
        assertEquals(Priority.CRITICAL, command.getPriority());
        assertEquals("Ellen Ripley", command.getAuthor());
        assertEquals("12:30:45", command.getTime());
        assertTrue(command.isCritical());
    }

    @Test
    void testCommonCommand() {
        // Arrange & Act
        Command command = new Command(
                "Check power unit",
                Priority.COMMON,
                "Ellen Ripley",
                "12:30:45"
        );

        // Assert
        assertFalse(command.isCritical());
    }

    @Test
    void testNullDescription() {
        // Act & Assert
        assertThrows(InvalidCommandException.class, () -> {
            new Command(null, Priority.COMMON, "Ellen Ripley", "12:30:45");
        });
    }

    @Test
    void testLongDescription() {
        // Arrange
        String longDescription = "a".repeat(1001); // Exceeds 1000 chars

        // Act & Assert
        assertThrows(InvalidCommandException.class, () -> {
            new Command(longDescription, Priority.COMMON, "Ellen Ripley", "12:30:45");
        });
    }

    @Test
    void testNullAuthor() {
        // Act & Assert
        assertThrows(InvalidCommandException.class, () -> {
            new Command("Check power unit", Priority.COMMON, null, "12:30:45");
        });
    }

    @Test
    void testLongAuthor() {
        // Arrange
        String longAuthor = "a".repeat(101); // Exceeds 100 chars

        // Act & Assert
        assertThrows(InvalidCommandException.class, () -> {
            new Command("Check power unit", Priority.COMMON, longAuthor, "12:30:45");
        });
    }

    @Test
    void testNullTime() {
        // Act & Assert
        assertThrows(InvalidCommandException.class, () -> {
            new Command("Check power unit", Priority.COMMON, "Ellen Ripley", null);
        });
    }

    @Test
    void testEmptyTime() {
        // Act & Assert
        assertThrows(InvalidCommandException.class, () -> {
            new Command("Check power unit", Priority.COMMON, "Ellen Ripley", "");
        });
    }

    @Test
    void testInvalidTimeFormat() {
        // Act & Assert
        assertThrows(InvalidCommandException.class, () -> {
            new Command("Check power unit", Priority.COMMON, "Ellen Ripley", "invalid-time");
        });
    }
}