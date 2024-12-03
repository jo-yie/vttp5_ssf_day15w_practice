package nus.iss.vttp5_ssf_day15w_practice.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.vttp5_ssf_day15w_practice.model.Contact;
import nus.iss.vttp5_ssf_day15w_practice.repo.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepo; 

    public Optional<Contact> getContactById(String id) {
        return contactRepo.getContactById(id);
        
    }
    
    public Set<String> getContacts() { 
        return contactRepo.getContactIds(); 

    }

    public String insert(Contact contact) {

        String id = UUID.randomUUID().toString().substring(0, 8);
        contact.setId(id); 

        contactRepo.insertContact(contact);

        return id;
        
    }
}
