package io.github.avew.mask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MaskTest {

    @Test
    public void testMask() {
        MaskWord maskWord = new MaskWord("password,username", "=", "([\\w]+)");
        String mask = maskWord.mask("This is a debug message with password=12345");
        assertEquals("This is a debug message with password=*****", mask);
    }

    @Test
    public void testMaskJson() {
        MaskWord maskWord = new MaskWord("npwp,nama,alamat", ":", "([\\w]+)");
        String data = "{\"npwp\":\"0717166367077000\",\"nama\":\"Asep Dadang\",\"alamat\":\"Jl. Kebon Jeruk No. 1\"}";
        String mask = maskWord.mask(data.replaceAll("\"", ""));
        assertEquals("{npwp:****************,nama:**** Dadang,alamat:**. Kebon Jeruk No. 1}", mask);
    }

    @Test
    public void testJacksonMask() throws JsonProcessingException {
        User user = new User("avew", "123456");
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(user);
        assertEquals("{\"username\":\"avew\",\"password\":\"***\"}", result);
    }
}