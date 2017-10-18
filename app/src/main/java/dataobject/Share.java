package dataobject;

/**
 * Created by tranminhquan on 10/18/2017.
 * Class chua thong tin ve share
 */

public class Share extends Interaction {
    private String shareid;

    public Share(){

    }

    public Share(String pid, String uid, String sid){
        super(pid, uid);
        shareid = sid;
    }

    //Get-Set
    public String getShareid() { return shareid; }

    public void setShareid(String value) { shareid = value; }
}
