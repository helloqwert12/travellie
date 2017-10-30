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
    private String coverLink;
    private boolean male; //true: male - false: female
    private String email;
    private String phone;
    private String description;
    private String rank; //Chinh lai kieu du lieu sau
    private List<String> friends; //Chua thong tin cac userid

    public UserInfo(){
        //Default constructor for firebase getting data return
    }

    public UserInfo(String fname,
                    String lname, String dob, String avt, String cvr,
                    boolean m, String em, String p, String des, String rk, List<String> frd){
        //userid = uid;
        //username = usrn;
        firstname = fname;
        lastname = lname;
        dateofbirth = dob;
        avatarLink = avt;
        coverLink = cvr;
        male = m;
        email = em;
        phone = p;
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
    public String getAvatarLink() { return avatarLink; }
    public String getCoverLink() { return coverLink; }
    public boolean isMale() { return (male == true); }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDescription() { return description; }
    public String getRank() { return rank; } //Chinh lai kieu du lieu sau
    public List<String> getFriends() { return friends; }

    //public void setUserid(String value) { userid = value; }
    //public void setUsername(String value) { username = value; }
    public void setFirstname(String value) { firstname = value; }
    public void setLastname(String value) { lastname = value; }
    public void setDateofbirth(String value) { dateofbirth = value; }
    public void setAvatarLink(String value) { avatarLink = value; }
    public void setCoverLink(String value) { coverLink = value; }
    public void setMale(boolean value) { male = value; }
    public void setEmail(String value) { email = value; }
    public void setPhone(String value) { phone = value; }
    public void setDescription(String value) { description = value; }
    public void setRank(String value) { rank = value; }
    public void setFriends(List<String> value) { friends = value; }
    public void addFriend(String usrid) { friends.add(usrid); }
}
