package com.skybooking.stakeholderservice.exception;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private LocalizationBean localization;

    @Autowired
    private UserRepository userRepository;
    // Error handle for @Valid

    @Autowired
    private HttpServletRequest requests;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        BindingResult result = ex.getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors();
        String validation = "";

        body.put("data", null);
        for (FieldError fieldError: fieldErrors) {
            validation = localization.multiLanguageRes(fieldError.getDefaultMessage());

            vldRegisterWeb(fieldError, result, body);
//            vldRegisterApp(fieldError, result, body);
        }
        body.put("message", validation);
        return new ResponseEntity<>(body, headers, status);

    }

    public void vldRegisterWeb(FieldError fieldError, BindingResult result, Map<String, Object> body) {

        String url = requests.getRequestURL().toString();
        String[] splitUrl = url.split("/");

        if (splitUrl[3].equals("wv1.0.0") && splitUrl[4].equals("auth") && splitUrl[5].equals("register")){
            if (fieldError.getCode().equals("UsernameUnique")) {
                if (result.getFieldValue("typeSky") != null && result.getFieldValue("typeSky").equals("bussiness")) {
                    UserEntity user = userRepository.findByEmailOrPhone(result.getFieldValue("username").toString(), result.getFieldValue("code").toString());
                    if (user.getStakeHolderUser().getIsSkyowner() != 1) {
                        HashMap<String, Object> skyowner = new HashMap<>();
                        skyowner.put("email", result.getFieldValue("username"));
                        skyowner.put("typeSky", result.getFieldValue("typeSky"));
                        body.put("data", skyowner);
                    }
                }
            }
        }

    }


}
