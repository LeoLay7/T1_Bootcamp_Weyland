import org.example.command.Command;
import org.example.command.CommandExecutor;
import org.example.command.CommandsQueue;
import org.example.command.Priority;
import org.example.exception.CommandQueueOverflowException;
import org.example.metrics.CommandExecutedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class CommandsQueueTest {

    @Mock
    private CommandExecutor commandExecutor;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private CommandsQueue commandsQueue;

    @BeforeEach
    void setUp() {
        commandsQueue = new CommandsQueue(commandExecutor, eventPublisher, "1", "2");
    }

    @Test
    void testAddCriticalCommand() {
        // Arrange
        Command command = new Command(
                "Critical task",
                Priority.CRITICAL,
                "Ellen Ripley",
                "12:30:45"
        );

        // Act
        commandsQueue.addCommand(command);

        // Assert - verify that execute was called
        verify(commandExecutor, timeout(1000)).execute(command);

        // Verify that event was published
        ArgumentCaptor<CommandExecutedEvent> eventCaptor = ArgumentCaptor.forClass(CommandExecutedEvent.class);
        verify(eventPublisher, timeout(1000)).publishEvent(eventCaptor.capture());
        assertEquals(command, eventCaptor.getValue().getCommand());
    }

    @Test
    void testAddCommonCommand() {
        // Arrange
        Command command = new Command(
                "Common task",
                Priority.COMMON,
                "Ellen Ripley",
                "12:30:45"
        );

        // Act
        commandsQueue.addCommand(command);

        // Assert - verify that execute was called (may take some time due to queue)
        verify(commandExecutor, timeout(1000)).execute(command);

        // Verify that event was published
        ArgumentCaptor<CommandExecutedEvent> eventCaptor = ArgumentCaptor.forClass(CommandExecutedEvent.class);
        verify(eventPublisher, timeout(1000)).publishEvent(eventCaptor.capture());
        assertEquals(command, eventCaptor.getValue().getCommand());
    }

    @Test
    void testQueueOverflow() {
        // Arrange - fill the queue
        Command command = new Command("Task", Priority.COMMON, "Ellen Ripley", "12:30:45");

        // Make executor.execute block to ensure queue fills up
        doAnswer(invocation -> {
            Thread.sleep(500); // Delay execution
            return null;
        }).when(commandExecutor).execute(any());

        // The third command should cause overflow
        assertThrows(CommandQueueOverflowException.class, () -> {
            for (int i = 0; i < 50; i++) {
                commandsQueue.addCommand(command);
            }
        });
    }

    @Test
    void testNullCommand() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            commandsQueue.addCommand(null);
        });
    }
}
