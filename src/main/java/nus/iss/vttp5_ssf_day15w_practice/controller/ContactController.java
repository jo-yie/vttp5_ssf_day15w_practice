package nus.iss.vttp5_ssf_day15w_practice.controller;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import nus.iss.vttp5_ssf_day15w_practice.model.Contact;
import nus.iss.vttp5_ssf_day15w_practice.service.ContactService;

@Controller
public class ContactController {

    private final Logger logger = Logger.getLogger(ContactController.class.getName());

    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public String home() { 
        return "home";

    }

    @GetMapping("/add-contact")
    public String showContactForm() { 
        return "add-contact";

    }

    @GetMapping("/contacts")
    public String getContacts(Model model) {
        
        model.addAttribute("ids", contactService.getContacts());

        return "contact-list";

    }

    
    @GetMapping("/contact/{id}")
    public ModelAndView getContactById(@PathVariable String id) {

        Optional<Contact> opt = contactService.getContactById(id);
        ModelAndView mav = new ModelAndView(); 

        if (opt.isEmpty()) {
            // 404
            mav.setViewName("not-found");
            mav.setStatus(HttpStatusCode.valueOf(404));
            mav.addObject("id", id);
            return mav; 

        }

        Contact contact = opt.get(); 
        
        // 200 
        mav.setViewName("contact-info");
        mav.addObject("contact", contact);
        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav; 
        
    }


    @PostMapping("/contact")
    public String postContact(@RequestBody MultiValueMap<String, String> form, Model model) {

        String name = form.getFirst("name");
        String email = form.getFirst("email");
        String phone = form.getFirst("phone");

        Contact contact = new Contact(); 
        contact.setName(name); 
        contact.setEmail(email);
        contact.setPhone(phone);

        logger.info("Adding %s to contact".formatted(name));
        String id = contactService.insert(contact);

        model.addAttribute("name", name);
        model.addAttribute("id", id);

        return "added";

    }
    
}
