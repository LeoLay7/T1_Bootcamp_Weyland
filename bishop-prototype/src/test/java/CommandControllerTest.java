import org.homework.controller.CommandController;
import org.homework.request.CommandRequest;
import org.homework.service.CommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommandControllerTest {

    @Mock
    private CommandService commandService;

    @InjectMocks
    private CommandController commandController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCommand_ShouldReturnOk() {
        // Arrange
        CommandRequest request = new CommandRequest("Test command", "NORMAL", "TestUser", "2023-01-01");
        doNothing().when(commandService).addCommand(any(CommandRequest.class));
        
        // Act
        ResponseEntity<?> response = commandController.addCommand(request);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(commandService, times(1)).addCommand(request);
    }
}