package kuit3.backend.service;

import kuit3.backend.common.exception.DatabaseException;
import kuit3.backend.common.exception.UserException;
import kuit3.backend.dao.UserDao;
import kuit3.backend.dto.user.PostLoginRequest;
import kuit3.backend.dto.user.PostLoginResponse;
import kuit3.backend.dto.user.PostUserRequest;
import kuit3.backend.dto.user.PostUserResponse;
import kuit3.backend.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public PostUserResponse signUp(PostUserRequest postUserRequest) {
        log.info("[UserService.signUp]");

        // TODO: 1. validation (중복 검사)
        validateEmail(postUserRequest.getEmail());
        String nickname = postUserRequest.getNickname();
        if (nickname != null) {
            validateNickname(postUserRequest.getNickname());
        }

        // TODO: 2. password 암호화
        String encodedPassword = passwordEncoder.encode(postUserRequest.getPassword());
        postUserRequest.resetPassword(encodedPassword);

        // TODO: 3. DB insert & userId 반환
        long userId = userDao.createUser(postUserRequest);

        // TODO: 4. JWT 토큰 생성
        String jwt = jwtProvider.createToken(postUserRequest.getEmail(), userId);

        return new PostUserResponse(userId, jwt);
    }

    public long findUserIdByEmail(String email) {
        return userDao.findUserIdByEmail(email);
    }

    public PostLoginResponse login(PostLoginRequest postLoginRequest, long userId) {
        log.info("[UserService.login]");

        // TODO: 1. 비밀번호 일치 확인
        validatePassword(postLoginRequest.getPassword(), userId);

        // TODO: 2. JWT 갱신
        String updatedJwt = jwtProvider.createToken(postLoginRequest.getEmail(), userId);

        return new PostLoginResponse(userId, updatedJwt);
    }

    public void modifyUserStatus_deleted(long userId) {
        int affectedRows = userDao.modifyUserStatus_deleted(userId);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserStatus_dormant(long userId) {
        int affectedRows = userDao.modifyUserStatus_dormant(userId);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void modifyNickname(long userId, String nickname) {
        validateNickname(nickname);
        int affectedRows = userDao.modifyNickname(userId, nickname);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    private void validatePassword(String password, long userId) {
        String encodedPassword = userDao.getPasswordByUserId(userId);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new UserException(PASSWORD_NO_MATCH);
        }
    }

    private void validateEmail(String email) {
        if (userDao.hasDuplicateEmail(email)) {
            throw new UserException(DUPLICATE_EMAIL);
        }
    }

    private void validateNickname(String nickname) {
        if (userDao.hasDuplicateNickName(nickname)) {
            throw new UserException(DUPLICATE_NICKNAME);
        }
    }
}