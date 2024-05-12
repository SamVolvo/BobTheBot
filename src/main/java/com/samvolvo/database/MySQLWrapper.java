package com.samvolvo.database;

import com.samvolvo.database.modals.ChannelData;
import com.samvolvo.database.modals.SettingsData;
import com.samvolvo.utils.DataBaseUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class MySQLWrapper {
    private final HikariDataSource dataSource;

    public MySQLWrapper(String url, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);
        createChannelTable();
        createSettingsTable();
    }

    private void createChannelTable(){
        try (Connection connection = dataSource.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS ChannelData ("
                    + "ID varchar(256) not null,"
                    + "announcementChannel varchar(256) not null,"
                    + "logChannel varchar(256) not null,"
                    + "welcomesChannel varchar(256) not null,"
                    + "botAnnounceChannel varchar(256)  not null,"
                    + ")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableSQL);
                System.out.println("[DB-System]: Channel table created");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void createSettingsTable(){
        try (Connection connection = dataSource.getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS SettingsData ("
                    + "ID          varchar(256) not null,"
                    + "isLinkDetectionOn tinyint(1) not null,"
                    + ")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableSQL);
                System.out.println("[DB-System]: Settings table created");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void saveChannels(String id, ChannelData channelData) {
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "INSERT INTO ChannelData (ID, announcementChannel, logChannel, welcomesChannel, botAnnouncementChannel) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, String.valueOf(id));
                insertStatement.setString(2, channelData.getAnnouncementChannel());
                insertStatement.setString(3, channelData.getWelcomesChannel());
                insertStatement.setString(4, channelData.getLogChannel());
                insertStatement.setString(5, channelData.getBotAnnouncementChannel());

                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        createChannelTable();
    }

    public void saveSettings(String id, SettingsData settingsData) {
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "INSERT INTO SettingsData (ID, isLinkDetectionOn) VALUES (?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, String.valueOf(id));
                insertStatement.setInt(2, DataBaseUtil.setBool(settingsData.isLinkDetectionActive()));
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        createChannelTable();
    }

    public void updateChannelData(ChannelData data){
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "UPDATE ChannelData SET announcementChannel = ?, logChannel = ?, welcomesChannel = ?, botAnnouncementChannel = ?, WHERE ID = ?";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, data.getAnnouncementChannel());
                insertStatement.setString(2, data.getLogChannel());
                insertStatement.setString(3, data.getWelcomesChannel());
                insertStatement.setString(4, data.getBotAnnouncementChannel());
                insertStatement.setString(5, data.getServerId());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateSettingsData(String serverId, SettingsData data){
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "UPDATE SettingsData SET isLinkDetectionOn, WHERE ID = ?";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, DataBaseUtil.setBool(data.isLinkDetectionActive()));
                insertStatement.setString(2, serverId);
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ChannelData getChannelData(String serverId, ChannelData data){
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "SELECT * FROM ChannelData WHERE id = ?";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, serverId);
                ResultSet results = insertStatement.executeQuery();
                if (results.next()){
                    String announcementChannel = results.getString("announcementChannel");
                    String logChannel = results.getString("logChannel");
                    String welcomesChannel = results.getString("welcomesChannel");
                    String botAnnouncementChannel = results.getString("botAnnouncementChannel");
                    ChannelData channelData = new ChannelData(serverId, announcementChannel, logChannel, welcomesChannel, botAnnouncementChannel);
                    insertStatement.close();
                    return channelData;
                }else{
                    insertStatement.close();
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public SettingsData getSettingsData(String serverId, SettingsData data){
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "SELECT * FROM SettingsData WHERE id = ?";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, serverId);
                ResultSet results = insertStatement.executeQuery();
                if (results.next()){
                    boolean isLinkDetectionActive = DataBaseUtil.checkBool(results.getInt("isLinkDetectionActive"));
                    SettingsData settingsData = new SettingsData(isLinkDetectionActive);
                    insertStatement.close();
                    return settingsData;
                }else{
                    insertStatement.close();
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

}