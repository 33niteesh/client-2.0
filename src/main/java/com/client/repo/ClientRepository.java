package com.client.repo;

import com.client.entity.Client;

import java.util.List;



public interface ClientRepository {
	Client saveUser(Client user);

	Client updateUser(Client user);

	Client getById(int id);

    String deleteById(int id);

    List<Client> allUsers();
}

