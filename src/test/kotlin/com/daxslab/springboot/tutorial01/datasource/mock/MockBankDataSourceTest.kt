package com.daxslab.springboot.tutorial01.datasource.mock

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class MockBankDataSourceTest {

    private val mockDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks`() {
        // when
        val banks = mockDataSource.retrieveBanks()

        // then
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {
        // when
        val banks = mockDataSource.retrieveBanks()

        // then
        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).allMatch { it.trust != 0.0 }
        assertThat(banks).allMatch { it.transactionFee != 0 }
    }

    @Test
    fun `should all accountNumbers be different`() {
        // when
        val banks = mockDataSource.retrieveBanks()
        val banksNames = banks.map { it.accountNumber }

        // then
        assertThat(banksNames).doesNotHaveDuplicates()
    }

    @Test
    fun `should return a bank data given the account number`() {
        // given
        val accountNumber = "1234"

        // when
        val bank = mockDataSource.getByAccountNumber(accountNumber)

        // then
        assertThat(bank.accountNumber).isEqualTo(accountNumber)
    }

    @Test
    fun `should throw exception when asking for unexisting account number`() {
        // given
        val accountNumber = "12345"

        // when/then
        val exception = assertThrows<NoSuchElementException>() {
            mockDataSource.getByAccountNumber(accountNumber)
        }
        assertThat(exception.message).isEqualTo("Can't find a bank with account number '$accountNumber'")
    }
}
