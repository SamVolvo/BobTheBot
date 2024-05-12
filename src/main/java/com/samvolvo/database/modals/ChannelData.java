package com.samvolvo.database.modals;

public class ChannelData {

    private String serverId;
    private String announcementChannel;
    private String logChannel;
    private String welcomesChannel;
    private String botAnnouncementChannel;

    public ChannelData(String serverId, String announcementChannel, String logChannel, String welcomesChannel, String botAnnouncementChannel) {
        this.serverId = serverId;
        this.announcementChannel = announcementChannel;
        this.logChannel = logChannel;
        this.welcomesChannel = welcomesChannel;
        this.botAnnouncementChannel = botAnnouncementChannel;
    }

    public String getServerId() {
        return serverId;
    }

    public String getAnnouncementChannel() {
        return announcementChannel;
    }

    public void setAnnouncementChannel(String announcementChannel) {
        this.announcementChannel = announcementChannel;
    }

    public String getLogChannel() {
        return logChannel;
    }

    public void setLogChannel(String logChannel) {
        this.logChannel = logChannel;
    }

    public String getWelcomesChannel() {
        return welcomesChannel;
    }

    public void setWelcomesChannel(String welcomesChannel) {
        this.welcomesChannel = welcomesChannel;
    }

    public String getBotAnnouncementChannel() {
        return botAnnouncementChannel;
    }

    public void setBotAnnouncementChannel(String botAnnouncementChannel) {
        this.botAnnouncementChannel = botAnnouncementChannel;
    }
}
