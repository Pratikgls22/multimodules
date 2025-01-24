package com.job.females.in.tech.utility;

import com.job.females.in.tech.entity.UserEntity;
import com.job.females.in.tech.enums.PatternEnums;
import com.job.females.in.tech.exception.CustomException;
import com.job.females.in.tech.repository.UserRepository;
import com.job.females.in.tech.responseDto.TokenClaims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class Utilities {
    private final UserRepository userRepository;
    private final TokenClaims tokenClaims;

    public static String generateStrongPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        // Ensure at least one character from each category
        // note -- special chars is being removed as it is not allowed in the password while redirecting from UI
        password.append(getRandomChar(PatternEnums.LOWERCASE_LETTERS.getValue()));
        password.append(getRandomChar(PatternEnums.UPPERCASE_LETTERS.getValue()));
        password.append(getRandomChar(PatternEnums.DIGITS.getValue()));

        // Fill the rest with the password
        for (int i = 4; i < length; i++) {
            password.append(PatternEnums.ALLOWED_CHARS.getValue().charAt(random.nextInt(PatternEnums.ALLOWED_CHARS.getValue().length())));
        }

        return password.toString();
    }

    private static char getRandomChar(String value) {
        SecureRandom random = new SecureRandom();
        return value.charAt(random.nextInt(value.length()));
    }

    public static Date getDate() {
        return new Date();
    }

    public UserEntity currentUser() {
        return Optional.ofNullable(this.tokenClaims.getUserId())
                .flatMap(this.userRepository::findById)
                .orElse(null);
    }

    public <T> T getCurrentEntity(Class<T> entityType, Function<Long, Optional<T>> findByIdFunction) {
        System.out.println("entityType = " + entityType);
        return Optional.ofNullable(this.tokenClaims.getUserId())
                .flatMap(findByIdFunction)
                .orElseThrow(() -> new CustomException("Entity not found", HttpStatus.NOT_FOUND));
    }

}
