package com.daxslab.springboot.tutorial01.controller

import com.daxslab.springboot.tutorial01.model.Bank
import com.daxslab.springboot.tutorial01.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun index(): Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNumber}")
    fun view(@PathVariable accountNumber: String): Bank = service.getBank(accountNumber)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody bank: Bank): Bank = service.createBank(bank)

    @PatchMapping("/{accountNumber}")
    fun update(@PathVariable accountNumber: String, @RequestBody bank: Bank): Bank = service.update(accountNumber, bank)

    @DeleteMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable accountNumber: String): Bank = service.delete(accountNumber)

}
