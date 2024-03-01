package com.client.api;

import java.util.List;
import java.util.function.Predicate;

import com.client.entity.Client;

@FunctionalInterface
interface ListFilter {
	List<Client> removeElements(List<Client> l,Predicate<Client> p);
}
