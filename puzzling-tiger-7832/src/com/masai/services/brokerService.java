package com.masai.services;

import java.util.Map;

import com.masai.entities.Broker;

public interface brokerService {
	
	public boolean login(String email, String password, Map<String, Broker> brokers);

	public void brokerageAccountApply(Broker broker, Map<String, Broker> brokers);

	
}
