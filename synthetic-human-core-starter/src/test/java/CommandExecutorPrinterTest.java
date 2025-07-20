import org.example.command.Command;
import org.example.command.CommandExecutorPrinter;
import org.example.command.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class CommandExecutorPrinterTest {

    private CommandExecutorPrinter executor;

    @BeforeEach
    void setUp() {
        executor = new CommandExecutorPrinter();
    }

    @Test
    void testExecute() {
        // Arrange
        Command command = new Command(
                "Test command",
                Priority.CRITICAL,
                "Ellen Ripley",
                "12:30:45"
        );

        // Act & Assert - just verify no exceptions
        assertDoesNotThrow(() -> {
            executor.execute(command);
        });
    }
}

