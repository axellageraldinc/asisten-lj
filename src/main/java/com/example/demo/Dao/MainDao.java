package com.example.demo.Dao;

import com.example.demo.model.Main;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MainDao {
    private static final String id = "id";
    private static final String group_id = "group_id";
    private static final String group_name = "group_name";
    private static final String deskripsi = "deskripsi";
    private static final String tipe = "tipe";

    private static final String tipe_data = "tipe_data";
    private static final String tipe_tugas = "tugas";
    private static final String tipe_ujian = "ujian";

    public static void CreateType(){
        PreparedStatement ps_tipe = null;
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try{
            ps_tipe = connection.prepareStatement(
                    "CREATE TYPE " + tipe_data + " AS ENUM " +
                            "(" + "'" +tipe_tugas + "'" + "," +
                            "'" + tipe_ujian + "'" +
                            ")"
            );
            if (ps_tipe.executeUpdate()==1)
                System.out.println("Create type " + tipe_data + " berhasil!");
        } catch (Exception ex){
            System.out.println("Gagal create type : " + tipe_data + " gagal : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(ps_tipe);
            DbConnection.CloseConnection(connection);
        }
    }

//    public static void CreateTableGroup(String groupId){
//        Connection connection = null;
//        try {
//            connection = DbConnection.getConnection();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        PreparedStatement ps = null;
//        try{
//            ps = connection.prepareStatement(
//                    "CREATE TABLE IF NOT EXISTS " + groupId +
//                            "(" +
//                            id + " SERIAL PRIMARY KEY, " +
//                            group_id + " TEXT NOT NULL, " +
//                            ")"
//            );
//            if (ps.executeUpdate()==1)
//                System.out.println("Create table " + groupId + " berhasil!");
//        } catch (Exception ex){
//            System.out.println("Gagal membuat table " + groupId + " : " + ex.toString());
//        } finally {
//            DbConnection.ClosePreparedStatement(ps);
//            try {
//                DbConnection.CloseConnection(DbConnection.getConnection());
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void CreateTableData(String groupId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DbConnection.getConnection();
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + groupId +
                            "(" +
                            id + " TEXT PRIMARY KEY, " +
                            deskripsi + " TEXT NOT NULL, " +
                            tipe + " " +tipe_data + " NOT NULL" +
                            ")"
            );
            if (preparedStatement.executeUpdate()==1)
                System.out.println("Create table " + groupId + "berhasil");
        } catch (Exception ex){
            System.out.println("Gagal create table " + groupId + " : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
    }

    public static int Insert(String groupId,Main main){
        int status=0;
        Connection connection = null;
        PreparedStatement ps = null;
        try{
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(
                    "INSERT INTO " + groupId +
                            "(" +
                            id + "," + deskripsi + "," +tipe +
                            ") VALUES (?,?,?::" + tipe_data + ")"
            );
            ps.setString(1, main.getId());
            ps.setString(2, main.getDeskripsi());
            ps.setString(3, main.getTipe());
            status = ps.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal insert main table : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
        return status;
    }

    public static List<Main> GetAll(String groupId, String type){
        List<Main> mainList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(
                    "SELECT * FROM " + groupId + " WHERE " + tipe + "=?::" + tipe_data
            );
            ps.setString(1, type);
            rs = ps.executeQuery();
            while (rs.next()){
                Main main = new Main();
                main.setId(rs.getString(id));
                main.setDeskripsi(rs.getString(deskripsi));
                mainList.add(main);
            }
        } catch (Exception ex){
            System.out.println("Gagal get data : " + ex.toString());
        } finally {
            DbConnection.CloseResultSet(rs);
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
        System.out.println("Main List kelas DAO : " + mainList);
        return mainList;
    }

    public static int DeleteItem(String groupId, String idd){
        int status = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        try{
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(
                    "DELETE FROM " + groupId + " WHERE " + id + "=?"
            );
            ps.setString(1, idd);
            status = ps.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal delete item : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
        return status;
    }
}
