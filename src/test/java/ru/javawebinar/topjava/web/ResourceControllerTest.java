package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ResourceControllerTest extends AbstractControllerTest {

    private static final String STYLE_URL_TEMPLATE = "/resources/css/style.css";
    private static final String CONTENT_TYPE_TEXT_CSS = "text/css;charset=UTF-8";

    @Test
    void getStyle() throws Exception {
        perform(get(STYLE_URL_TEMPLATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf(CONTENT_TYPE_TEXT_CSS)));
    }

}
