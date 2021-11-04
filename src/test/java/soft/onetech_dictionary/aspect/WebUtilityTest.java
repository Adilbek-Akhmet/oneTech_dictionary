package soft.onetech_dictionary.aspect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.mock.web.MockHttpServletRequest;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@ExtendWith(SpringExtension.class)
class WebUtilityTest {

    @InjectMocks
    private WebUtility underTest;

    @Test
    @WithMockUser(username = "testUser")
    void testGetCurrentUsername() {
        String result = underTest.getCurrentUsername();
        Assertions.assertEquals("testUser", result);
    }

    @Test
    void testGetClientHttpServletRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        HttpServletRequest clientHttpServletRequest = underTest.getClientHttpServletRequest();
        Assertions.assertNotNull(clientHttpServletRequest);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme