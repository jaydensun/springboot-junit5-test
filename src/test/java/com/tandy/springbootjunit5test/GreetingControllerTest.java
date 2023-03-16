package com.tandy.springbootjunit5test;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GreetingControllerTest {

    private static MockMvc mockMvc;

    @BeforeAll
    static void setUp(WebApplicationContext webApplicationContext) {
        System.out.println("----初始化mockMvc-----");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        System.out.println("----mockMvc执行完毕------");
    }

    @AfterAll
    static void tearDown() {
        System.out.println("---- mockMvc: ------" + mockMvc.toString());
        System.out.println("----所有测试完毕------");
    }

    @Order(1)
    @Test
    void greeting() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/greeting")
                        .param("name", "abc")
                        .accept(MediaType.ALL)
                )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(200, status);
        assertEquals("{\"id\":1,\"content\":\"Hello, abc!\"}", content);
        System.out.println("测试 greeting 完成");
    }

    @Order(2)
    @Test
    void greetingDefault() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/greeting")
                        .accept(MediaType.ALL)
                )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(200, status);
        assertEquals("{\"id\":2,\"content\":\"Hello, World!\"}", content);
        System.out.println("测试 greetingDefault 完成");
    }
}