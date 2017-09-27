package com.example.demo.Dao;

import com.example.demo.model.Group;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
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
                            "'" + tipe_ujian + "'" + "," +
                            ")"
            );
            if (ps_tipe.executeUpdate()==1)
                System.out.println("Create type " + tipe_data + " berhasil!");
        } catch (Exception ex){
            System.out.println("Gagal create type : " + tipe_data + " gagal");
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
                    "CREATE TABLE IF NOT EXISTS " + groupId + "_data" +
                            "(" +
                            id + " TEXT PRIMARY KEY, " +
                            deskripsi + " TEXT NOT NULL, " +
                            tipe + " " +tipe_data + " NOT NULL" +
                            ")"
            );
            if (preparedStatement.executeUpdate()==1)
                System.out.println("Create table " + groupId + "_data" + "berhasil");
        } catch (Exception ex){
            System.out.println("Gagal create table " + groupId + "_data" + " : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
    }

    public static int Insert(String groupId,Group group){
        int status=0;
        Connection connection = null;
        PreparedStatement ps = null;
        try{
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(
                    "INSERT INTO " + groupId + "_data" +
                            "(" +
                            id + "," + deskripsi + "," +tipe +
                            ") VALUES (?,?,?)"
            );
            ps.setString(1, group.getId());
            ps.setString(2, group.getDeskripsi());
            ps.setString(3, group.getTipe());
            status = ps.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal insert group table : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
        return status;
    }

    public static List<Group> GetAllTugas(String groupId, String tipe){
        String tableName = groupId + "_data";
        List<Group> groupList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DbConnection.getConnection();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try{
            ps = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE " + tipe + "=?"
            );
            ps.setString(1, tipe);
            rs = ps.executeQuery();
            while (rs.next()){
                Group group = new Group();
                group.setId(rs.getString(id));
                group.setDeskripsi(rs.getString(deskripsi));
                groupList.add(group);
            }
        } catch (Exception ex){
            System.out.println("Gagal get data : " + ex.toString());
        } finally {
            DbConnection.CloseResultSet(rs);
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
        return groupList;
    }
}
