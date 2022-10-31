package com.daxslab.springboot.tutorial01.datasource.mock

import com.daxslab.springboot.tutorial01.datasource.BankDataSource
import com.daxslab.springboot.tutorial01.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {

    var banks = mutableListOf(
        Bank("1234", 1.0, 11),
        Bank("2345", 2.0, 12),
        Bank("3456", 3.0, 13),
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun getByAccountNumber(accountNumber: String): Bank =
        banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw java.util.NoSuchElementException("Can't find a bank with account number '$accountNumber'")

    override fun create(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("Bank ${bank.accountNumber} already exists.")
        }
        if (banks.add(bank)) {
            return bank
        } else {
            throw Error("Can't create/update the bank")
        }
    }

    override fun update(accountNumber: String, bank: Bank): Bank {
        delete(accountNumber)
        return create(bank)
    }

    override fun delete(accountNumber: String): Bank {
        return banks.removeAt(banks.indexOf(getByAccountNumber(accountNumber)))
    }
}
