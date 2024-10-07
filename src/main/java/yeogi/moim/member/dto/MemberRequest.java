package yeogi.moim.member.dto;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class MemberRequest {
    private String username;
    private String email;
    private String password;

    public void validate() {
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Invalid username");
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new RuntimeException("Invalid email");
        }
        if (password == null || password.trim().isEmpty() || password.length() < 4) {
            throw new RuntimeException("Invalid password");
        }
    }

    public String getEncodedPassword(PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(password);
    }
}
