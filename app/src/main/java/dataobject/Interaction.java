package dataobject;

import java.math.BigInteger;

/**
 * Created by tranminhquan on 10/18/2017.
 * Abstract class chung cua cac class tuong tac voi bai post: like, share, comment, ...
 */

public abstract class Interaction {
    private String postid; //dang tuong tac tai post nao
    //private String userid; //tuong tac nay do user nao tao ra
    private long timestamp; //khoang thoi gian tuong tac nay duoc tao ra

    public Interaction(){

    }

    public Interaction(String pid, String uid){
        postid = pid;
        //userid = uid;
    }

    //Get-Set
    public String getPostid() { return postid; }
    public long getTimestamp() { return timestamp; }
    //public String getUserid() { return userid; }

    public void setPostid(String value) { postid = value; }
    public void setTimestamp(long value) { timestamp = value; }
    //public void setUserid(String value) { userid = value; }
}
