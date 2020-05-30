package com.skybooking.stakeholderservice.exception.anotation;

import com.skybooking.stakeholderservice.v1_0_0.io.repository.contact.ContactRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, Object> {

    private String firstFieldName;
    private String message;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRP contactRP;

    @Autowired
    private UserBean userBean;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void initialize(final EmailUnique constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        int index = request.getServletPath().lastIndexOf('/');
        String seg = request.getServletPath().substring(0,index);
        String lastSeg = seg.substring(seg.lastIndexOf('/') + 1);

        boolean valid = true;

        try
        {

            final String email = BeanUtils.getProperty(value, firstFieldName);

            Boolean n = Boolean.parseBoolean(userRepository.existsByEmailOrPhone(email, null, lastSeg.equals("company") ? userBean.getUserPrincipalVld().getId() : null));
            Boolean b = Boolean.parseBoolean(contactRP.existsContact(email, lastSeg.equals("company") ? userBean.getUserPrincipalVld().getStakeHolderUser().getStakeholderCompanies().stream().findFirst().get().getId() : null));

            if (n || b) {
                valid = false;
            }

        }
        catch (final Exception ignore)
        {
            // ignore
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }


}
