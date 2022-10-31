package com.daxslab.springboot.tutorial01.datasource.network.dto

import com.daxslab.springboot.tutorial01.model.Bank

data class BankList(
    val results: Collection<Bank>
)
