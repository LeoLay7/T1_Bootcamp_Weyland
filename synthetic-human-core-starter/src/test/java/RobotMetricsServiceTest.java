
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.example.command.Command;
import org.example.command.CommandsQueue;
import org.example.command.Priority;
import org.example.metrics.CommandExecutedEvent;
import org.example.metrics.RobotMetricsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RobotMetricsServiceTest {

    @Mock
    private CommandsQueue commandsQueue;

    private MeterRegistry meterRegistry;
    private RobotMetricsService metricsService;

    @BeforeEach
    void setUp() {
        meterRegistry = new SimpleMeterRegistry();
        metricsService = new RobotMetricsService(meterRegistry, commandsQueue);
        metricsService.init(); // Initialize metrics
    }

    @Test
    void testQueueSizeMetric() {
        // Arrange
        when(commandsQueue.getQueueSize()).thenReturn(5);

        // Act & Assert
        assertEquals(5, meterRegistry.get("robot.queue.size").gauge().value());
    }

    @Test
    void testCommandExecutedMetric() {
        // Arrange
        Command command = new Command(
                "Test command",
                Priority.CRITICAL,
                "Ellen Ripley",
                "12:30:45"
        );
        CommandExecutedEvent event = new CommandExecutedEvent(this, command);

        // Act
        metricsService.handleCommandExecutedEvent(event);

        // Assert
        assertEquals(1, meterRegistry.get("robot.commands.executed")
                .tag("author", "Ellen Ripley")
                .counter().count());
    }

    @Test
    void testMultipleCommandsFromSameAuthor() {
        // Arrange
        Command command1 = new Command(
                "Test command 1",
                Priority.CRITICAL,
                "Ellen Ripley",
                "12:30:45"
        );
        Command command2 = new Command(
                "Test command 2",
                Priority.COMMON,
                "Ellen Ripley",
                "12:35:45"
        );
        CommandExecutedEvent event1 = new CommandExecutedEvent(this, command1);
        CommandExecutedEvent event2 = new CommandExecutedEvent(this, command2);

        // Act
        metricsService.handleCommandExecutedEvent(event1);
        metricsService.handleCommandExecutedEvent(event2);

        // Assert
        assertEquals(2, meterRegistry.get("robot.commands.executed")
                .tag("author", "Ellen Ripley")
                .counter().count());
    }

    @Test
    void testCommandsFromDifferentAuthors() {
        // Arrange
        Command command1 = new Command(
                "Test command 1",
                Priority.CRITICAL,
                "Ellen Ripley",
                "12:30:45"
        );
        Command command2 = new Command(
                "Test command 2",
                Priority.COMMON,
                "Ash",
                "12:35:45"
        );
        CommandExecutedEvent event1 = new CommandExecutedEvent(this, command1);
        CommandExecutedEvent event2 = new CommandExecutedEvent(this, command2);

        // Act
        metricsService.handleCommandExecutedEvent(event1);
        metricsService.handleCommandExecutedEvent(event2);

        // Assert
        assertEquals(1, meterRegistry.get("robot.commands.executed")
                .tag("author", "Ellen Ripley")
                .counter().count());
        assertEquals(1, meterRegistry.get("robot.commands.executed")
                .tag("author", "Ash")
                .counter().count());
    }
}
