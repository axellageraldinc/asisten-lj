package com.example.demo.Dao;

import com.example.demo.model.Followers;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FollowersDao {

    private static final String table_name = "followers";
    private static final String id = "id";
    private static final String username = "username";
    private static final String user_id = "user_id";

    public static int CreateTable(){
        int status=0;
        Connection connection = DbConnection.getConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " +
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
        Connection connection = DbConnection.getConnection();
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
}
