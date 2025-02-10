package com.example.smartinventoryapi.services;

import com.sendgrid.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import static org.mockito.Mockito.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SendEmailServiceTest {

    @Mock
    private SendGrid sendGrid;

    @InjectMocks
    private SendEmailService sendEmailService;

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @BeforeEach
    void setUp() {
        // Przygotowanie mocków
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmailSuccessfully() throws IOException {
        // Mockowanie odpowiedzi z SendGrid
        Request mockRequest = mock(Request.class);
        Response mockResponse = mock(Response.class);
        when(sendGrid.api(any(Request.class))).thenReturn(mockResponse);
        when(mockResponse.getStatusCode()).thenReturn(202);

        String toEmail = "hecmanczuk.krystian@example.com";
        String subject = "Testowy e-mail";
        String content = "Treść testowego e-maila";

        // Wywołanie metody
        sendEmailService.sendEmail(toEmail, subject, content);

        // Weryfikacja, czy metoda wysyłania e-maila została wywołana
        verify(sendGrid).api(any(Request.class));
    }

    @Test
    void testSendEmailFailure() throws IOException {
        // Mockowanie odpowiedzi z SendGrid z błędem
        Request mockRequest = mock(Request.class);
        Response mockResponse = mock(Response.class);
        when(sendGrid.api(any(Request.class))).thenReturn(mockResponse);
        when(mockResponse.getStatusCode()).thenReturn(400); // Błąd 400

        String toEmail = "test@example.com";
        String subject = "Testowy e-mail";
        String content = "Treść testowego e-maila";

        // Testowanie, czy wyjątek jest rzucany przy błędzie
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sendEmailService.sendEmail(toEmail, subject, content);
        });

        assertEquals("Błąd wysyłania e-maila: Bad Request", exception.getMessage());
    }
}
