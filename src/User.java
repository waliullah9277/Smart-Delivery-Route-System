public class User {
    String email;
    String password;
    String confirmPassword;

    User(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}