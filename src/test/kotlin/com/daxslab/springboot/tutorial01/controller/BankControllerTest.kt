package com.daxslab.springboot.tutorial01.controller

import com.daxslab.springboot.tutorial01.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
) {

    val BASE_URL = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class Index {
        @Test
        fun `should return all banks`() {
            // when
            mockMvc.get(BASE_URL)
                //then
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].account_number") {
                        value("1234")
                    }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class View {

        @Test
        fun `should return data of a bank`() {
            //given
            val accountNumber = "1234"
            // when
            mockMvc.get("$BASE_URL/$accountNumber")
                // then
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.account_number") {
                        value("1234")
                    }
                }
        }

        @Test
        fun `should return 404 when unexisting accountNumber requested`() {
            //given
            val accountNumber = "bad-account-number"
            // when
            mockMvc.get("$BASE_URL/$accountNumber")
                // then
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content { string("Can't find a bank with account number '$accountNumber'") }
                }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class Create {
        @Test
        @DirtiesContext
        fun `should create a new bank`() {
            // given
            val bank = Bank("4567", 4.0, 14)

            // when
            mockMvc.post(BASE_URL) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }
                // then
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(bank))
                    }
                }

            mockMvc.get("$BASE_URL/${bank.accountNumber}")
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(bank))
                    }
                }
        }

        @Test
        fun `should throw BAD REQUEST when inserting bank account with existing account number`() {
            // given
            val bank = Bank("1234", 4.0, 14)

            // when
            mockMvc.post(BASE_URL) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }
                // then
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { string("Bank ${bank.accountNumber} already exists.") }
                }
        }
    }

    @Nested
    @DisplayName("PATCH /api/banks/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class Update {
        @Test
        @DirtiesContext
        fun `should update a bank`() {
            // given
            val bank = Bank("1234", 8.0, 14)

            // when
            mockMvc.patch("$BASE_URL/${bank.accountNumber}") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }
                // then
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(bank))
                    }
                }

            mockMvc.get("$BASE_URL/${bank.accountNumber}")
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(bank))
                    }
                }
        }

        @Test
        fun `should throw NOT FOUND when trying to update unexisting bank`() {
            // given
            val bank = Bank("7890", 8.0, 14)

            // when
            mockMvc.patch("$BASE_URL/${bank.accountNumber}") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }
                // then
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content { string("Can't find a bank with account number '${bank.accountNumber}'") }
                }
        }
    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class Delete {
        @Test
        @DirtiesContext
        fun `should delete a bank`() {
            // given
            val bank = Bank("1234", 1.0, 11)

            // when
            mockMvc.delete("$BASE_URL/${bank.accountNumber}")
                // then
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(bank))
                    }
                }

            mockMvc.get("$BASE_URL/${bank.accountNumber}")
                .andExpect {
                    status { isNotFound() }
                    content {
                        content { string("Can't find a bank with account number '${bank.accountNumber}'") }
                    }
                }
        }

        @Test
        fun `should throw NOT FOUND when trying to delete unexisting bank`() {
            // given
            val bank = Bank("7890", 8.0, 14)

            // when
            mockMvc.delete("$BASE_URL/${bank.accountNumber}")
                // then
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content { string("Can't find a bank with account number '${bank.accountNumber}'") }
                }
        }
    }
}
