package com.example.sales.service.dao;

import com.example.sales.entity.Client;

import java.util.List;

public interface ClientInterface {
    List<Client> getAllClients();
    void updateClientDiscounts(Long clientId, Double discount1, Double discount2);
}
