package com.samvolvo;

import com.samvolvo.database.MySQLWrapper;
import com.samvolvo.managers.*;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.vitacraft.jmjda.api.config.ConfigUtil;
import net.vitacraft.jmjda.api.config.YAMLConfig;

import javax.security.auth.login.LoginException;


public class BobTheBot {
    private static YAMLConfig config = (YAMLConfig) ConfigUtil.getConfig("config.yml", ConfigUtil.Filetype.YAML, ConfigUtil.PathType.RELATIVE);

    private static ShardManager shardManager;
    public static void main(String[] args) throws LoginException {

        if (config.getString("token").equals(" ") || config.getString("token").equals(null)){
            System.out.println("LineLol: Fill in the bot token in the config.yml!");
            return;
        }

        MySQLWrapper wrapper = new MySQLWrapper(config.getString("db-url"), config.getString("db-user"), config.getString("db-psw"));

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(config.getString("token"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setActivity(Activity.watching("Build phase!"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        builder.build();
        shardManager = builder.build();

        shardManager.addEventListener(new CommandManager());

    }

    public static YAMLConfig getConfig() {return config;}
}
