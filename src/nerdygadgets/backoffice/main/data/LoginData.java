package nerdygadgets.backoffice.main.data;

public class LoginData {
    private static LoginData instance;

    private String id;
    private String name;
    private String email;
    private Boolean isloggedin = false;

    private LoginData(){

    }

    public static LoginData getInstance(){
        if(instance == null){
            instance = new LoginData();
        }
        return instance;
    }
    //test

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void login(){
        this.isloggedin = true;
    }

    public void logout(){
        this.isloggedin = false;
        this.id = null;
        this.name = null;
        this.email = null;
    }

}

