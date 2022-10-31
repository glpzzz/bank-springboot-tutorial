package com.daxslab.springboot.tutorial01.service

import com.daxslab.springboot.tutorial01.datasource.BankDataSource
import com.daxslab.springboot.tutorial01.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(accountNumber: String): Bank = dataSource.getByAccountNumber(accountNumber)
    fun createBank(bank: Bank): Bank = dataSource.create(bank)
    fun update(accountNumber: String, bank: Bank): Bank = dataSource.update(accountNumber, bank)
}
