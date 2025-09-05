
class User {
    protected String username;
    protected String password;
    protected String role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(String password) {
        return this.password.equals(password);
    }
    public String getRole() {
        return role; 
    }
}