package webserver.http.controller;

import db.DataBase;
import model.User;
import org.springframework.util.StringUtils;
import webserver.http.*;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SignUpController implements Controller {

    private final List<String> requiredParams = Arrays.asList("userId", "password", "name", "email");

    private HttpRequestParamValidator paramValidator = new HttpRequestParamValidator(requiredParams);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<HttpRequestParam> params = HttpRequestParams.convertFrom(httpRequest.getBody());
        paramValidator.validate(params);

        Map<String, String> paramMap = params.stream()
                .collect(Collectors.toMap(HttpRequestParam::getName, HttpRequestParam::getValue));

        User newUser = new User(
                paramMap.get("userId"),
                paramMap.get("password"),
                paramMap.get("name"),
                paramMap.get("email")
        );
        DataBase.addUser(newUser);

        httpResponse.setStatus(HttpStatus.x302_Found);
        httpResponse.addHeader("Location", "/index.html");
    }



}
