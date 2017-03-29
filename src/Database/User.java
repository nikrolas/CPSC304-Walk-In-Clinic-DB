package Database;

/**
 * Created by Hayley on 2017-03-28.
 */
public class User {
    private String userName;
    private String password;

    User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
