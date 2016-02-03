package io.rong.app.database;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table QUN.
 */
public class Qun {

    /** Not-null value. */
    private String qunId;
    private String name;
    private String portraitUri;
    private String displayName;
    private String role;
    private Long timestamp;

    public Qun() {
    }

    public Qun(String qunId) {
        this.qunId = qunId;
    }

    public Qun(String qunId, String name, String portraitUri, String displayName, String role, Long timestamp) {
        this.qunId = qunId;
        this.name = name;
        this.portraitUri = portraitUri;
        this.displayName = displayName;
        this.role = role;
        this.timestamp = timestamp;
    }

    /** Not-null value. */
    public String getQunId() {
        return qunId;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setQunId(String qunId) {
        this.qunId = qunId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
