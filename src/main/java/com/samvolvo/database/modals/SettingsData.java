package com.samvolvo.database.modals;

public class SettingsData {
    private boolean isLinkDetectionActive;

    public SettingsData(boolean isLinkDetectionActive) {
        this.isLinkDetectionActive = isLinkDetectionActive;
    }

    public boolean isLinkDetectionActive() {
        return isLinkDetectionActive;
    }

    public void setLinkDetectionActive(boolean linkDetectionActive) {
        isLinkDetectionActive = linkDetectionActive;
    }

}
