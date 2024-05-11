package com.samvolvo.database.modals;

public class ServerData {
    private String serverId;
    private String announcementChannel;
    private String welcomeChannel;
    private String logChannel;
    private String botUpdateChannel;
    private boolean welcomesMessages;
    private boolean antiLinkSending;

    public String getServerId() {
        return serverId;
    }

    public String getAnnouncementChannel() {
        return announcementChannel;
    }

    public void setAnnouncementChannel(String announcementChannel) {
        this.announcementChannel = announcementChannel;
    }

    public String getWelcomeChannel() {
        return welcomeChannel;
    }

    public void setWelcomeChannel(String welcomeChannel) {
        this.welcomeChannel = welcomeChannel;
    }

    public String getLogChannel() {
        return logChannel;
    }

    public void setLogChannel(String logChannel) {
        this.logChannel = logChannel;
    }

    public String getBotUpdateChannel() {
        return botUpdateChannel;
    }

    public void setBotUpdateChannel(String botUpdateChannel) {
        this.botUpdateChannel = botUpdateChannel;
    }

    public boolean isWelcomesMessages() {
        return welcomesMessages;
    }

    public void setWelcomesMessages(boolean welcomesMessages) {
        this.welcomesMessages = welcomesMessages;
    }

    public boolean isAntiLinkSending() {
        return antiLinkSending;
    }

    public void setAntiLinkSending(boolean antiLinkSending) {
        this.antiLinkSending = antiLinkSending;
    }
}
