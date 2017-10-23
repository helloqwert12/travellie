package dataobject;

import android.media.Image;

import java.util.List;

/**
 * Created by tranminhquan on 10/18/2017.
 * Chua thong tin ve user
 */

public class UserInfo {
    //private String userid;
    //private String username;
    private String firstname;
    private String lastname;
    private String dateofbirth;
    private String avatarLink;
    private boolean male; //true: male - false: female
    private String email;
    private String description;
    private String rank; //Chinh lai kieu du lieu sau
    private List<String> friends; //Chua thong tin cac userid

    public UserInfo(){
        //Default constructor for firebase getting data return
    }

    public UserInfo(String fname,
                    String lname, String dob, String avt,
                    boolean m, String em, String des, String rk, List<String> frd){
        //userid = uid;
        //username = usrn;
        firstname = fname;
        lastname = lname;
        dateofbirth = dob;
        avatarLink = avt;
        male = m;
        email = em;
        description = des;
        rank = rk;
        friends = frd;
    }

    //Get-Set
    //public String getUserid() { return userid; }
    //public String getUsername() { return username; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return  lastname; }
    public String getDateofbirth() { return  dateofbirth; }
    public String getAvatar() { return avatarLink; }
    public boolean isMale() { return (male == true); }
    public String getEmail() { return email; }
    public String getDescription() { return description; }
    public String getRank() { return rank; } //Chinh lai kieu du lieu sau
    public List<String> getFriends() { return friends; }

    //public void setUserid(String value) { userid = value; }
    //public void setUsername(String value) { username = value; }
    public void setFirstname(String value) { firstname = value; }
    public void setLastname(String value) { lastname = value; }
    public void setDateofbirth(String value) { dateofbirth = value; }
    public void setAvatar(String value) { avatarLink = value; }
    public void setMale(boolean value) { male = value; }
    public void setEmail(String value) { email = value; }
    public void setDescription(String value) { description = value; }
    public void setRank(String value) { rank = value; }
    public void setFriends(List<String> value) { friends = value; }
    public void addFriend(String usrid) { friends.add(usrid); }
}
