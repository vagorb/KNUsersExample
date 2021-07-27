package com.example.knexample.controller;

import com.example.knexample.model.ManyToManyRel;
import com.example.knexample.model.ManyUsersOneData;
import com.example.knexample.model.User;
import com.example.knexample.repository.DataRepository;
import com.example.knexample.repository.ManyToManyRelRepository;
import com.example.knexample.repository.ManyUsersOneDataRepository;
//import com.example.knexample.repository.OrderItemRepository;
//import com.example.knexample.repository.OrderRepository;
import com.example.knexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DataRepository dataRepository;

    @Autowired
    ManyUsersOneDataRepository manyUsersOneDataRepository;

    @Autowired
    ManyToManyRelRepository manyToManyRelRepository;

//    @Autowired
//    OrderRepository orderRepository;
//
//    @Autowired
//    OrderItemRepository orderItemRepository;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping("/sortedusers")
    public ResponseEntity<Map<String, Object>> getAllUsersSorted(@RequestParam(required = false) String username,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "3") int size,
                                                        @RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Sort.Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<User> users;

            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<User> pageUsers;
            if (username == null)
                pageUsers = userRepository.findAll(pagingSort);
            else
                pageUsers = userRepository.findByUsernameContaining(username, pagingSort);

            users = pageUsers.getContent();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("users", users);
            response.put("currentPage", pageUsers.getNumber());
            response.put("totalItems", pageUsers.getTotalElements());
            response.put("totalPages", pageUsers.getTotalPages());

            return new ResponseEntity<>( response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String username) {
        try {
            List<User> users = new ArrayList<>();

            if (username == null)
                users.addAll(userRepository.findAll());
            else
                users.addAll(userRepository.findByUsernameContaining(username));

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userRepository
                    .save(new User(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User _user1 = userData.get();
            _user1.setUsername(user.getUsername());
            _user1.setFirstName(user.getFirstName());
            _user1.setLastName(user.getLastName());
            _user1.setEmail(user.getEmail());
            return new ResponseEntity<>(userRepository.save(_user1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> userData = userRepository.findById(id);

        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/users/test")
    public ResponseEntity<ManyUsersOneData> createUsers(@RequestBody User user) {
        try {
//            User _user = userRepository
//                    .save(new User(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail()));
//            Data data = new Data();
//            data.setUser(_user);
//            dataRepository.save(data);
            ManyUsersOneData _user = new ManyUsersOneData();
            _user.setUsers(userRepository.findAllByEmail(user.getEmail()));
            manyUsersOneDataRepository.save(_user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/many")
    public ResponseEntity<ManyToManyRel> createManyToMany(@RequestBody User user) {
        try {
//            User _user = userRepository
//                    .save(new User(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail()));
//            Data data = new Data();
//            data.setUser(_user);
//            dataRepository.save(data);
            System.out.println(user);
            ManyToManyRel _user = new ManyToManyRel();
            _user.setProducts( new HashSet<>(userRepository.findAll()));
            manyToManyRelRepository.save(_user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/users/relations")
//    public void createRelation(@RequestBody User user) {
//        MToOne m = new MToOne();
//        OToMany o = new OToMany();
//
//
//
//    }

}
