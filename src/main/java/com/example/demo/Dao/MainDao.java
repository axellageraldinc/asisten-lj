package com.example.demo.Dao;

import com.example.demo.model.GroupMember;
import com.example.demo.model.ImgDetectStatus;
import com.example.demo.model.Main;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainDao {
    private static final String id = "id";
    private static final String group_id = "group_id";
    private static final String group_name = "group_name";
    private static final String deskripsi = "deskripsi";
    private static final String tipe = "tipe";
    private static final String user_id = "user_id";
    private static final String user_name = "user_name";

    private static final String tipe_data = "tipe_data";
    private static final String tipe_tugas = "tugas";
    private static final String tipe_ujian = "ujian";

    private static final String status = "status";

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

    public static void CreateTableGroupMember(String group_id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String groupMemberTableName = group_id + "_memberIds";
        try{
            connection = DbConnection.getConnection();
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + groupMemberTableName +
                            "(" +
                            id + " SERIAL PRIMARY KEY, " +
                            user_id + " TEXT NOT NULL " +
                            ")"
            );
            if(preparedStatement.executeUpdate()==1)
                System.out.println("Berhasil create table GroupMember");
        } catch (Exception ex){
            System.out.println("Gagal create table GroupMember : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
    }

    public static void CreateTableImageDetectStatus(String group_id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String groupMemberTableName = "img_detect_status";
        try{
            connection = DbConnection.getConnection();
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + groupMemberTableName +
                            "(" +
                            id + " SERIAL PRIMARY KEY, " +
                            "group_id TEXT, " +
                            status + " INT NOT NULL " +
                            ")"
            );
            if(preparedStatement.executeUpdate()==1)
                System.out.println("Berhasil create table img detect status");
            DeleteItemImgStatus(group_id);
            insertImgStatus(group_id);
        } catch (Exception ex){
            System.out.println("Gagal create table img detect status : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
    }

    public static int DeleteItemImgStatus(String groupId){
        int status = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        try{
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(
                    "DELETE FROM " + "img_detect_status" + " WHERE " + group_id + "=?"
            );
            ps.setString(1, groupId);
            status = ps.executeUpdate();
        } catch (Exception ex){
            System.out.println("Gagal delete item : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
        return status;
    }

    public static void insertImgStatus(String group_id){
        Connection connection = null;
        PreparedStatement ps = null;
        String groupMemberTableName = "img_detect_status";
        try{
            try {
                connection = DbConnection.getConnection();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            ps = connection.prepareStatement(
                    "INSERT INTO " + groupMemberTableName +
                            "(" +
                            "group_id" + "," +
                            status +
                            ") VALUES(?,?)"
            );
            ps.setString(1, group_id);
            ps.setInt(2, 0);
            ps.executeUpdate();
        } catch (SQLException ex){
            System.out.println("Gagal insert grup img status : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
    }

    public static void UpdateImgStatus(String group_id, int status){
        Connection connection = null;
        PreparedStatement ps = null;
        String groupMemberTableName = "img_detect_status";
        try{
            try {
                connection = DbConnection.getConnection();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            ps = connection.prepareStatement(
                    "UPDATE " + groupMemberTableName + " SET status=?" + " WHERE group_id=?"
            );
            ps.setInt(1, status);
            ps.setString(2, group_id);
            if(ps.executeUpdate()==1){
                System.out.println("Berhasil update imd status : " + status);
            }
        } catch (SQLException ex){
            System.out.println("Gagal update status img detect : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
    }

    public static int getStatus(String group_id){
        int statuss=0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String groupMemberTableName = "img_detect_status";
        try{
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(
                    "SELECT " + status +  " FROM " + groupMemberTableName + " WHERE group_id=?"
            );
            ps.setString(1, group_id);
            rs = ps.executeQuery();
            while(rs.next()){
                ImgDetectStatus imgDetectStatus = new ImgDetectStatus();
                imgDetectStatus.setStatus(rs.getInt(status));
                statuss = imgDetectStatus.getStatus();
            }
        } catch (Exception ex){
            System.out.println("Gagal get status : " + ex.toString());
        } finally {
            DbConnection.CloseResultSet(rs);
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
        return statuss;
    }

    public static int InsertGroupMemberId(String groupId, String user_idd){
        Connection connection = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        int status=0;
        String groupMemberTableName = groupId + "_memberIds";
        try{
            try {
                connection = DbConnection.getConnection();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            ps = connection.prepareStatement(
                    "INSERT INTO " + groupMemberTableName +
                            "(" +
                            user_id +
                            ") VALUES(?)"
            );
            ps.setString(1, user_idd);
            int isDuplicate = 0;
            ps2 = connection.prepareStatement(
                    "SELECT * FROM " + groupMemberTableName + " WHERE " + user_id + "=?"
            );
            ps2.setString(1, user_idd);
            rs = ps2.executeQuery();
            while (rs.next()){
                isDuplicate++;
            }
            if(isDuplicate==0)
                status = ps.executeUpdate();
            else
                status = 0;
        } catch (SQLException ex){
            System.out.println("Gagal insert grup member : " + ex.toString());
        } finally {
            DbConnection.CloseResultSet(rs);
            DbConnection.ClosePreparedStatement(ps2);
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
        return status;
    }

    public static List<GroupMember> getAllMemberIds(String groupId){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String groupMemberTableName = groupId + "_memberIds";
        List<GroupMember> groupMembers = new ArrayList<>();
        try{
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(
                    "SELECT " + user_id + " FROM " + groupMemberTableName
            );
            rs = ps.executeQuery();
            while(rs.next()){
                GroupMember groupMember = new GroupMember();
                groupMember.setUserId(rs.getString(user_id));
                groupMembers.add(groupMember);
            }
        } catch (Exception ex){
            System.out.println("Gagal get all data member id : " + ex.toString());
        } finally {
            DbConnection.CloseResultSet(rs);
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
        return groupMembers;
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

    public static void DropTable(String tablename){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DbConnection.getConnection();
            ps = connection.prepareStatement(
                    "DROP TABLE " + tablename
            );
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbConnection.ClosePreparedStatement(ps);
            DbConnection.CloseConnection(connection);
        }
    }
}
