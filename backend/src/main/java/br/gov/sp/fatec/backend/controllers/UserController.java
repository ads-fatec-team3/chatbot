package br.gov.sp.fatec.backend.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.backend.models.User;
import br.gov.sp.fatec.backend.repositories.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> Get() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> GetById(@PathVariable(value = "id") final long id)
    {
        final Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/users", method =  RequestMethod.POST)
    public User Post(@Valid @RequestBody final User user)
    {
        return userRepository.save(user);
    }

    @RequestMapping(value = "/users/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<User> Put(@PathVariable(value = "id") final long id, @Valid @RequestBody final User newUser)
    {
        final Optional<User> oldUser = userRepository.findById(id);
        if(oldUser.isPresent()){
            final User user = oldUser.get();
            user.setName(newUser.getName());

            userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") final long id)
    {
        final Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            userRepository.delete(user.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}