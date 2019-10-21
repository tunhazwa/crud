package com.sample.crud.controller;

import com.sample.crud.dao.TicketDao;
import com.sample.crud.model.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ticket")
@ResponseBody
@Slf4j
@RequiredArgsConstructor
public class TicketController {

    @Autowired
    private TicketDao ticketDao;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/addTicket")
    public Ticket addTicket(@RequestBody Ticket ticket) {

        return ticketDao.save(ticket);
    }

    @GetMapping("/allTicket")
    public List<Ticket> findTickets() {

        return ticketDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable int id) {
        Optional<Ticket> stock = ticketDao.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable int id, @Valid @RequestBody Ticket product) {
        if (ticketDao.findById(id).isEmpty()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(ticketDao.save(product));
    }

//    public Ticket update(Long id, Ticket stock) {
//
//        Ticket chosenItem = TicketDao.findById(id).orElseThrow();
//
//        if (id != chosenItem.getId()) {
//            return null;
//        } else
//            return TicketDao.save(stock);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        if (!ticketDao.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        ticketDao.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
