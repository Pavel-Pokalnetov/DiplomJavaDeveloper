package ru.slenergo.AppMonitoring.configuration;



import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.slenergo.AppMonitoring.etc.StaticTools.dropSmallDecimalPart;

@SpringBootTest
public class StaticToolsTest {
    @Test
    public void dropSmallDecimalPartTest(){
        assertEquals(dropSmallDecimalPart(12.12345,1),12.1,0.0);
        assertEquals(dropSmallDecimalPart(-45.461,1),-45.4,0.0);
        assertEquals(dropSmallDecimalPart(0.0001,4),0.0001,0.0);
        assertEquals(dropSmallDecimalPart(1.9999,1),1.9,0.0);

    }
}