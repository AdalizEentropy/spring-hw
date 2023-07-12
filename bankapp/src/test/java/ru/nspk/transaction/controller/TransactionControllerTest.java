package ru.nspk.transaction.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.nspk.util.CreateTestTransactions.createDto;
import static ru.nspk.util.CreateTestTransactions.createRespDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.nspk.transaction.dto.TransactionReqDto;
import ru.nspk.transaction.service.TransactionService;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {
    private static final String URL = "/transactions";
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MockMvc mvc;
    @MockBean private TransactionService transactionService;

    @Test
    void notCreateTransaction_withoutAuth() throws Exception {
        var reqDto = createDto(1234567, 7654321, 100, 643);

        mvc.perform(
                        post(URL)
                                .content(objectMapper.writeValueAsString(reqDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser("spring")
    void createTransaction() throws Exception {
        var reqDto = createDto(1234567, 7654321, 100, 643);
        var returnedTransaction = createRespDto(1, 1234567, 7654321, 100, 643);
        when(transactionService.createTransaction(any(TransactionReqDto.class)))
                .thenReturn(returnedTransaction);

        mvc.perform(
                        post(URL)
                                .content(objectMapper.writeValueAsString(reqDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.time").exists())
                .andExpect(jsonPath("$.accountFrom").value(1234567))
                .andExpect(jsonPath("$.accountTo").value(7654321))
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.currency").value(643));
    }

    @Test
    @WithMockUser("spring")
    void reverseTransaction() throws Exception {
        var returnedTransaction = createRespDto(2, 1234567, 7654321, 100, 643);
        when(transactionService.reverseTransaction(1)).thenReturn(returnedTransaction);

        mvc.perform(post(URL + "/reverse/1").contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.time").exists())
                .andExpect(jsonPath("$.accountFrom").value(1234567))
                .andExpect(jsonPath("$.accountTo").value(7654321))
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.currency").value(643));
    }
}
