package com.example.demo.Dao;

import com.example.demo.model.Followers;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FollowersDao {

    private static final String table_name = "followers";
    private static final String id = "id";
    private static final String username = "username";
    private static final String user_id = "user_id";

    public static int CreateTable(){
        int status=0;
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + table_name +
                            "(" +
                            id + " SERIAL PRIMARY KEY, " +
                            username + " TEXT NOT NULL, " +
                            user_id + " TEXT NOT NULL)"
            );
            status = preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("FAILED create table followers : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
        return status;
    }

    public static int Insert(Followers followers){
        int status=0;
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + table_name +
                            "(" +
                            username + "," +
                            user_id +
                            ") VALUES (?,?)"
            );
            preparedStatement.setString(1, followers.getName());
            preparedStatement.setString(2, followers.getUser_id());
            status = preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println("FAILED insert into table followers : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
        return status;
    }

    public static List<Followers> getFollowers(){
        List<Followers> followersList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(
                    "SELECT " + user_id + " FROM " + table_name
            );
            rs = ps.executeQuery();
            while (rs.next()){
                Followers followers = new Followers();
                followers.setUser_id(rs.getString(user_id));
                followersList.add(followers);
            }
        } catch (Exception ex){
            System.out.println("FAILED get followers : " + ex.toString());
        } finally {
            DbConnection.CloseResultSet(rs);
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
        return followersList;
    }
}
