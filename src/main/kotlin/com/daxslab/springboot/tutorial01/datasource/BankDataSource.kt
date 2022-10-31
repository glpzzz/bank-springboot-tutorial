package com.daxslab.springboot.tutorial01.datasource

import com.daxslab.springboot.tutorial01.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>

    fun getByAccountNumber(accountNumber: String): Bank

    fun create(bank: Bank): Bank

    /**
     * Updates the data of bank with [accountNumber] with the rest of the data in [bank]
     */
    fun update(accountNumber: String, bank: Bank): Bank

    /**
     * Removes bank with [accountNumber] from the collection
     */
    fun delete(accountNumber: String): Bank

}
