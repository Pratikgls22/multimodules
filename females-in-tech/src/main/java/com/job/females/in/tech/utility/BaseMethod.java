package com.job.females.in.tech.utility;

import com.job.females.in.tech.entity.EmployerEntity;
import com.job.females.in.tech.entity.RoleEntity;
import com.job.females.in.tech.entity.UserEntity;
import com.job.females.in.tech.entity.UserRoleMappingEntity;
import com.job.females.in.tech.enums.ExceptionEnum;
import com.job.females.in.tech.exception.CustomException;
import com.job.females.in.tech.repository.EmployerRepository;
import com.job.females.in.tech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BaseMethod {

    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;

    public UserEntity getUser(String email) {
        return this.userRepository.findByEmailIgnoreCaseAndIsDeleteFalse(email)
                .orElseThrow(() -> new CustomException(ExceptionEnum.USER_ALREADY_EXIST.getValue(), HttpStatus.BAD_REQUEST));
    }

    // For Email Message Body :
    public String getHtmlCode(String userName, String url) {
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Password Reset</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            line-height: 1.6;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f4f4f4;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .credentials {\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 10px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 10px auto;\n" +
                "            padding: 20px;\n" +
                "            background-color:aliceblue ;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        h2 {\n" +
                "            color: #3498db;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        p {\n" +
                "            margin-bottom: 20px;\n" +
                "            color: #333333;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            color: #3498db;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        a:hover {\n" +
                "            text-decoration: underline;\n" +
                "        }\n" +
                "\n" +
                "        .btn {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            background-color: #3498db;\n" +
                "            color: white;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <div class=\"credentials\">\n" +
                "        <h2>Task Management System</h2>\n" +
                "    </div>" +
                "    <h2>Welcome</h2>\n" +
                "    <p>Hello " + userName + ",</p>\n" +
                "    <p>Thank you for joining our service! We're excited to have you on board.</p>\n" +
                "    <p>Your account has been created,To set your password and complete your account setup, please click the button below:</p>\n" +
                "    <a href=\" " + url + "\" class=\"btn\">Verify</a>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }

    public UserRoleMappingEntity getUserRoleMappingEntity(UserEntity userEntity, RoleEntity roleEntity) {
        return UserRoleMappingEntity.builder()
                .userId(userEntity)
                .roleId(roleEntity)
                .build();
    }
}
