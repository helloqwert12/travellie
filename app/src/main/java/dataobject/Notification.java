package dataobject;

/**
 * Created by tranminhquan on 01/03/2018.
 */

public class Notification {
    private String id;
    private String senderName;  // tên người gửi
    private INTERACTION_TYPE type;  // loại tương tác
    private String postid;

    public Notification(){

    }

    public String getId() { return id; }
    public String getSenderName() { return senderName; }
    public INTERACTION_TYPE getType() { return type; }
    public String getPostid() { return postid; }

    public void setId(String value) { id = value; }
    public void setSenderName(String value) { senderName = value; }
    public void setType(INTERACTION_TYPE value) { type = value; }
    public void setPostid(String value) { postid = value; }
}
