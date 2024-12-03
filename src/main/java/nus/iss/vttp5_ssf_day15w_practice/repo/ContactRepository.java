package nus.iss.vttp5_ssf_day15w_practice.repo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import nus.iss.vttp5_ssf_day15w_practice.model.Contact;

@Repository
public class ContactRepository {

    // dependency injection --> RedisTemplate into ContactRepository
    @Autowired @Qualifier("redis-object")
    private RedisTemplate<String, Object> template; 

    // hgetall id 
    public Optional<Contact> getContactById(String id) {

        HashOperations<String, String, Object> hashOps = template.opsForHash();
        Map<String, Object> contact = hashOps.entries(id);

        if (contact.isEmpty()) {
            return Optional.empty(); 

        }

        Contact result = new Contact(); 
        result.setName(contact.get("name").toString());
        result.setEmail(contact.get("email").toString());
        result.setPhone(contact.get("phone").toString()); 
        
        return Optional.of(result);

    }

    // keys * 
    public Set<String> getContactIds() {
        return template.keys("*");

    }

    // hset abc123 name fred
    // hset abc123 email fred@gmail.com
    public void insertContact(Contact contact) {

        // ListOperations<String, Object> listOps = template.opsForList();
        // ValueOperations<String, Object> valueOps = template.opsForValue(); 

        // key serialiser, hash key 
        HashOperations<String, String, Object> hashOps = template.opsForHash(); 

        // hashOps.put(contact.getId(), "name", contact.getName()); 
        // hashOps.put(contact.getId(), "email", contact.getEmail());
        // hashOps.put(contact.getId(), "phone", contact.getPhone());

        Map<String, Object> values = new HashMap<>(); 
        values.put("name", contact.getName());
        values.put("email", contact.getEmail());
        values.put("phone", contact.getPhone());
        hashOps.putAll(contact.getId(), values);

    }
    
}
