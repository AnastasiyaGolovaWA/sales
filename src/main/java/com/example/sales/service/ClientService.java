package com.example.sales.service;

import com.example.sales.entity.Client;
import com.example.sales.repository.ClientRepository;
import com.example.sales.service.dao.ClientInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService implements ClientInterface {
    @Autowired
    ClientRepository clientRepository;
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public void updateClientDiscounts(Long clientId, Double discount1, Double discount2) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client not found with id " + clientId));
        client.setDiscount1(discount1);
        client.setDiscount2(discount2);
        clientRepository.save(client);
    }
}
