package com.client.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.client.entity.Client;
import com.client.repo.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.client.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@Tag(name="Client API")
public class ClientController {
	
    @Autowired
    ClientRepository clientRepository;

    @PostMapping("/add_client")
    @Operation(summary="Add Client")
    public ResponseEntity<?> addUser(@RequestBody Client user) throws SQLException{
    	try {
    		clientRepository.saveUser(user);
    		return new ResponseEntity<>("Client added",HttpStatus.CREATED);
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		throw new Conflict(e.getMessage());
    	}

    }

    @PutMapping("/client_edit")
    @Operation(summary="Edit Client details")
    public ResponseEntity<?> updateUser(@RequestBody Client user)throws SQLException{
    	try {
    		clientRepository.updateUser(user);
    	return new ResponseEntity<>("Client details updated",HttpStatus.ACCEPTED);
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    		throw new InternalServerException(e.getMessage());
    	}
    }

    @GetMapping("/client/{id}")
    @Operation(summary="get Client by Id")
    public ResponseEntity<?> getUser(@PathVariable int id){
    	try {
    		Client user=clientRepository.getById(id);
    		
    		JSONObject r=new JSONObject();
    		r.put("Status", HttpStatus.OK);
    		r.put("Status code", 200);
    		r.put("message", "Success");
    		r.put("Data", user.toJson());
    		return new ResponseEntity<>(r.toString(),HttpStatus.OK);
    	}
    	catch (Exception e){
    		System.out.println(e.getMessage());
    		throw new UserNotFoundException(e.getMessage());
	}
    }

    @GetMapping("/all_valid_clients")
    @Operation(summary="get all valid Clients")
    public ResponseEntity<?> getUsers() throws JSONException{
    	try {
    		JSONObject r=new JSONObject();
    		List<Client> user = clientRepository.allUsers();
    		Predicate<Client> email = i -> isValidEmailAddress(i.getEmail());
    		Predicate<Client> mbl = i -> isValidContactNumber(i.getPhone());
    		ListFilter filter = (l, p) -> {
    			l=l.stream().filter(p).collect(Collectors.toList());
    	    	return l;
    		 };
    		 List<Client> filteredList = filter.removeElements(user,email);
    		 List<Client> finalList =filter.removeElements(user,mbl);
    		r.put("Status", HttpStatus.OK);
    		r.put("Status code", 200);
    		r.put("message", "Success");
    		r.put("Data", finalList);
    		return new ResponseEntity<>(r.toString(),HttpStatus.OK);
    	}
        catch(Exception e) {
        	System.out.println(e.getMessage());
        	throw new InternalServerException(e.getMessage());
        }
        
    }
    @GetMapping("/all_clients")
    @Operation(summary="get all  Clients")
    public ResponseEntity<?> getUser() throws JSONException{
    	try {
    		JSONObject r=new JSONObject();
    		List<Client> user = clientRepository.allUsers();
    		List<JSONObject> users = new ArrayList<>();
    		for(Client i:user) {
    			users.add(i.toJson());
    		}
    		r.put("Status", HttpStatus.OK);
    		r.put("Status code", 200);
    		r.put("message", "Success");
    		r.put("Data", users);
    		return new ResponseEntity<>(r.toString(),HttpStatus.OK);
    	}
        catch(Exception e) {
        	System.out.println(e.getMessage());
        	throw new InternalServerException(e.getMessage());
        }
    }
    @GetMapping("/all_clients_sorted")
    @Operation(summary="get all  Clients sorted")
    public ResponseEntity<?> getUsersorted() throws JSONException{
    	try {
    		JSONObject r=new JSONObject();
    		List<Client> user = clientRepository.allUsers();
    		List<Client> sortedPersons = user.stream()
                    .sorted(Comparator.comparing(Client::getFname).thenComparing(Client::getLname))
                    .collect(Collectors.toList());
    		r.put("Status", HttpStatus.OK);
    		r.put("Status code", 200);
    		r.put("message", "Success");
    		r.put("Data", sortedPersons);
    		return new ResponseEntity<>(r.toString(),HttpStatus.OK);
    	}
        catch(Exception e) {
        	System.out.println(e.getMessage());
        	throw new InternalServerException(e.getMessage());
        }
        
    }
    
    @GetMapping("/all_clients/{pageno}")
    @Operation(summary="get all  Clients")
    public ResponseEntity<?> getUserpagination(@PathVariable int pageno) throws JSONException{
    	try {
    		JSONObject r=new JSONObject();
    		List<Client> user = clientRepository.allUsers();
    		int total=user.size();
    		int end=5*pageno;
    		int start=end-5;
    		List<Client> client = new ArrayList<>();
    		for(int i=start;i<((end>user.size())? user.size():end);i++){
    			client.add(user.get(i));
    		}
    		r.put("Status", HttpStatus.OK);
    		r.put("Status code", 200);
    		r.put("message", "Success");
    		r.put("Data", client);
    		return new ResponseEntity<>(r.toString(),HttpStatus.OK);
    	}
        catch(Exception e) {
        	System.out.println(e.getMessage());
        	throw new InternalServerException(e.getMessage());
        }
    }
    @DeleteMapping("/client_remove/{id}")
    @Operation(summary="Delete Client by Id")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
    	try {
    		String s=clientRepository.deleteById(id);
    		return new ResponseEntity<>(s,HttpStatus.OK);
    	}
    	catch (Exception e){
    		System.out.println(e.getMessage());
    		throw new UserNotFoundException(e.getMessage());
	}
    	
    }
    private static boolean isValidContactNumber(String contactNumber) {
        return contactNumber.length()==10;
    }
    private static boolean isValidEmailAddress(String emailAddress) {
        return Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", emailAddress);
    }
}






//@RequestMapping("/*")
//public ResponseEntity<?> badrequest(){
//	throw new handleExceptionCustom("wrong url");
//}