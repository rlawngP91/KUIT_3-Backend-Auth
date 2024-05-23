package kuit3.backend.service;

import kuit3.backend.common.exception.UserException;
import kuit3.backend.dao.UserDao;
import kuit3.backend.dto.user.PostUserRequest;
import kuit3.backend.dto.user.PostUserResponse;
import kuit3.backend.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.DUPLICATE_EMAIL;
import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.DUPLICATE_NICKNAME;

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
        if (userDao.hasDuplicateEmail(postUserRequest.getEmail())) {
            throw new UserException(DUPLICATE_EMAIL);
        }
        if (userDao.hasDuplicateNickName(postUserRequest.getNickname())) {
            throw new UserException(DUPLICATE_NICKNAME);
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

}