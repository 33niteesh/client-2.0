package com.client.repo;

public class Constants {
	public static final String INSERT_USER_QUERY = "INSERT INTO PERSONS(id,fname,lname,email,phone,age) values(?,?,?,?,?,?)";
    public static final String UPDATE_USER_BY_ID_QUERY = "UPDATE PERSONS SET fname=? WHERE ID=?";
    public static final String GET_USER_BY_ID_QUERY = "SELECT * FROM PERSONS WHERE ID=?";
    public static final String DELETE_USER_BY_ID = "DELETE FROM PERSONS WHERE ID=?";
    public static final String GET_USERS_QUERY = "SELECT * FROM PERSONS";
}
