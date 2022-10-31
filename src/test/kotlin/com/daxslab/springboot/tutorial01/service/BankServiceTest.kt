package com.daxslab.springboot.tutorial01.service

import com.daxslab.springboot.tutorial01.datasource.BankDataSource
import com.daxslab.springboot.tutorial01.model.Bank
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class BankServiceTest {
    private val dataSource: BankDataSource = mockk(relaxed = true)
    private val bankService = BankService(dataSource)

    @Test
    fun `should call its data source to retrieve banks`() {
        // when
        bankService.getBanks()

        // then
        verify(exactly = 1) { dataSource.retrieveBanks() }

    }

    @Test
    fun `should call its data source to retrieve a banks data`() {
        // given
        val accountNumber = "1234"

        // when
        bankService.getBank(accountNumber)

        // then
        verify(exactly = 1) { dataSource.getByAccountNumber(accountNumber) }
    }

    @Test
    fun `should call data source create to add a new bank`() {
        // given
        val bank = Bank("4567", 4.0, 4)

        // when
        bankService.createBank(bank)

        // then
        verify(exactly = 1) { dataSource.create(bank) }
    }
}
