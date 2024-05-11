package com.samvolvo.database;

import com.samvolvo.BobTheBot;
import com.samvolvo.database.modals.ServerData;
import net.vitacraft.jmjda.api.config.YAMLConfig;

import java.sql.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Database {

    private static String url;
    private static String user;
    private static String psw;
    private static String name;
    private static Connection connection;
    private static final long IDLE_TIMEOUT = 3600000; // 1 HOUR in milliseconds
    private static long lastConnectionTime = System.currentTimeMillis();
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    // Config
    private static YAMLConfig config = BobTheBot.getConfig();

    public static Connection getConnection(){
        long currentTime = System.currentTimeMillis();
        if (connection != null){
            return connection;
        }else{
            establishConnection();
            lastConnectionTime = currentTime;
            return connection;
        }
    }

    public static void onEnable(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        url = "jdbc:mysql//" + config.getString("db-ip") + "/" + config.getString("db-name");
        user = config.getString("db-username");
        psw = config.getString("db-password");

        createTables();
        executorService.scheduleWithFixedDelay(() ->{
            long currentTime = System.currentTimeMillis();
            if (connection != null && (currentTime - lastConnectionTime) > IDLE_TIMEOUT){
                destroyConnection();
                System.out.println("[DB-System] Trying to restart the Database!");
                establishConnection();
            }
        }, IDLE_TIMEOUT, IDLE_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public static void onDisable(){
        destroyConnection();
        executorService.shutdown();
    }


    private static void establishConnection(){
        try {
            connection = DriverManager.getConnection(url, user, psw);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void destroyConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Tables
    private static void createTables(){
        // CREATE && LOAD TABLE
        //Warn table
        try {
            Statement statementWarnTable = connection.createStatement();
            String sqlServerTable = "CREATE TABLE IF NOT EXISTS UserData(id varchar(36) primary key, announcementChannel varchar(36), welcomesChannel varchar(36), logChannel varchar(36), botUpdateChannel varchar(36), welcomesMessages )";
            statementWarnTable.execute(sqlServerTable);
            statementWarnTable.close();
            System.out.println("[DB-System]: ServerData Table was created Succesfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //warning System
    public void createUserData(ServerData data){
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("INSERT INTO ServerData (id, announcementChannel, welcomeChannel, logChannel, botUpdateChannel, WelcomesMessages, antiLinkSending) VALUES (?,?,?,?,?,?,?)");
            statement.setString(1, data.getServerId());
            statement.setString(2, data.getAnnouncementChannel());
            statement.setString(3, data.getWelcomeChannel());
            statement.setString(4, data.getLogChannel());
            statement.setString(5, data.getBotUpdateChannel());
            statement.setBoolean(6, data.isWelcomesMessages());
            statement.setBoolean(7, data.isAntiLinkSending());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateUserData(UserData data){
        PreparedStatement statement = null;
        try {
            statement = getConnection()
                    .prepareStatement("UPDATE UserData SET warnings = ?, WHERE id = ?");
            statement.setInt(1, data.getWarnings());
            statement.setString(2, data.getUserId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserData findUserDatabyID(String id){
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM UserData WHERE id = ?");
            statement.setString(1, id);
            ResultSet results = statement.executeQuery();

            if (results.next()){
                int warnings = results.getInt("warnings");
                UserData userData = new UserData(id, warnings);
                statement.close();
                return userData;
            }else{
                statement.close();
                UserData userData = new UserData(id, 0);
                createUserData(userData);
                return userData;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}