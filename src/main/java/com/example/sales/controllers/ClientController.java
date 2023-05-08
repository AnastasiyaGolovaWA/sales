package com.example.sales.controllers;

import com.example.sales.entity.Client;
import com.example.sales.service.ClientService;
import com.example.sales.service.dao.ClientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private ClientInterface clientInterface;

    @Autowired
    public void setClientInterface(final ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientInterface.getAllClients();
    }

    @PutMapping("/{clientId}/discounts")
    public void updateClientDiscounts(@PathVariable Long clientId, @RequestParam Double discount1, @RequestParam Double discount2) {
        clientInterface.updateClientDiscounts(clientId, discount1, discount2);
    }
}
