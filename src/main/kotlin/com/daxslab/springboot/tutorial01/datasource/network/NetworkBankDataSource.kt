package com.daxslab.springboot.tutorial01.datasource.network

import com.daxslab.springboot.tutorial01.datasource.BankDataSource
import com.daxslab.springboot.tutorial01.datasource.network.dto.BankList
import com.daxslab.springboot.tutorial01.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException

@Repository("network")
class NetworkBankDataSource(
    @Autowired private val restTemplate: RestTemplate
) : BankDataSource {

    val baseUrl = "https://54.193.31.159"

    override fun retrieveBanks(): Collection<Bank> {
        val response: ResponseEntity<BankList> = restTemplate.getForEntity("$baseUrl/banks")
        return response.body?.results
            ?: throw IOException("Could not fetch banks from network")
    }

    override fun getByAccountNumber(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun create(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun update(accountNumber: String, bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun delete(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }
}
