package back.domain.user.service;

import back.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Getter
@Service
@RequiredArgsConstructor
public class UserService {

    @Transactional
    public User createUser(User user) {
        return null;
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return null;
    }

    @Transactional(readOnly = true)
    public boolean verifyNickname(String nickname) {
        return true;
    }

    @Transactional
    public void updateNickName(String nickname) {
    }

    @Transactional(readOnly = true)
    public boolean verifyPassword(String nickname) {
        return true;
    }

    @Transactional
    public void updatePassword(String password) {

    }

    @Transactional
    public User updateUserInfo(User user) {
        return null;
    }

    @Transactional
    public boolean deleteUser() {
        return true;
    }

    @Transactional
    public void logout(String refreshToken, HttpServletRequest request, HttpServletResponse response) {

    }

}
