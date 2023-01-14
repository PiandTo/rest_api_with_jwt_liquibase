package com.example.restful_test.security;

import com.example.restful_test.model.Client;
import com.example.restful_test.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerClientDetailsService implements ClientDetailsService {
    private final ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        Client client = clientRepository.findClientByLogin(s);
        if (client == null) {
            return null;
        }
        return new CustomerClientDetails(client);
    }
}
