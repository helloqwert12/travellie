package dataobject;
import android.media.Image;

import java.util.List;

/**
 * Created by tranminhquan on 10/18/2017.
 * Chua data ve bai post trong app
 * Note: Hien tai co 4 post type: newsfeed, food, hotel, place
 */


public class Post {
    //private String postid;
    private String content;
    private List<String> imageLinks;
    private String userid;
    private long timestamp;
    private List<String> links;
    private List<String> tags;
    private List<String> likes; //chua cac likeid
    private List<String> shares; //chua cac shareid
    private List<String> comments; //chua cac commentid
    private POST_TYPE type; //type cua bai post

    public Post(){
        //default constructor for firebase
    }

    public Post(String ctn, List<String> imglinks, String uid, long ts, List<String> lk,
                List<String> tg, List<String> lik, List<String> shrs, List<String> cmts, POST_TYPE tp){
        //postid = pid;
        content = ctn;
        imageLinks = imglinks;
        userid = uid;
        timestamp = ts;
        links = lk;
        tags = tg;
        likes = lik;
        shares = shrs;
        comments = cmts;
        type = tp;
    }

    //Get-set
    //public String getPostid() { return postid; }
    public String getContent() { return content; }
    public List<String> getImageLinks() { return imageLinks; }
    public String getUserid() { return userid; }
    public long getTimestamp() { return timestamp; }
    public List<String> getLinks() { return links; }
    public List<String> getTags() { return tags; }
    public List<String> getLikes() { return likes; }
    public List<String> getShares() { return shares; }
    public List<String> getComments() { return comments; }
    public POST_TYPE getType() { return type; }

    //public void setPostid(String value) { postid = value; }
    public void setContent(String value) { content = value; }
    public void setImageLinks(List<String> value) { imageLinks = value; }
    public void setTimestamp(long value) { timestamp = value; }
    public void setUserid(String value) { userid = value; }
    public void setLinks(List<String> value) { links = value; }
    public void setTags(List<String> value) { tags = value; }
    public void setLikes(List<String> value) { likes = value; }
    public void setShares(List<String> value) { shares = value; }
    public void setComments(List<String> value) { comments = value; }
    public void setType(POST_TYPE value) { type = value; }

    //add
    public void addImageLink(String element) { imageLinks.add(element); }
    public void addLink(String element) { links.add(element); }
    public void addTag(String element) { tags.add(element); }
    public void addLike(String element) { likes.add(element); }
    public void addShare(String element) { shares.add(element); }
    public void addComment(String element) { comments.add(element); }

}
