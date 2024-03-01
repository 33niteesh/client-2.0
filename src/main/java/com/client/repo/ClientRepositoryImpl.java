package com.client.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.client.entity.Client;

import java.util.List;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Client saveUser(Client user) {
    	String storedProcedureCall = "CALL insertRecord(?,?,?,?,?,?)";
        jdbcTemplate.update(storedProcedureCall, user.getId(), user.getFname(), user.getLname(), user.getEmail(),user.getPhone(),user.getAge());
        return user;
    }

    @Override
    public Client updateUser(Client user) {
        jdbcTemplate.update(Constants.UPDATE_USER_BY_ID_QUERY, user.getFname(), user.getId());
        return user;
    }

    @Override
    public Client getById(int id) {
    	String storedProcedureCall = "CALL getOne(?)";
        return jdbcTemplate.queryForObject(storedProcedureCall, (rs, rowNum) -> {
            return new Client(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"),rs.getString("phone"),rs.getInt("age"));
        },id);
    }

    @Override
    public String deleteById(int id) {
        Integer i=jdbcTemplate.update(Constants.DELETE_USER_BY_ID, id);
        if(i!=1) {
        	return "user notfound : ";
        }
        return "User got deleted with id :" + id;
    }

    @Override
    public List<Client> allUsers() {
    	String storedProcedureCall = "CALL getAllpersons()";
        return jdbcTemplate.query(storedProcedureCall, (rs, rowNum) -> {
            return new Client(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"),rs.getString("phone"),rs.getInt("age"));
        });
    }
}
