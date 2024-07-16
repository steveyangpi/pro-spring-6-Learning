package com.apress.prospring6.five.annotated;

import com.apress.prospring6.five.advice.*;
import com.apress.prospring6.five.common.CommandingDocumentarist;
import com.apress.prospring6.five.common.Guitar;
import com.apress.prospring6.five.common.RejectedInstrumentException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnnotatedAdviceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(AnnotatedAdviceTest.class);

    @Test
    void testBeforeAdviceV1() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, BeforeAdviceV1.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("beforeAdviceV1"));

        NewDocumentarist documentarist = ctx.getBean("documentarist", NewDocumentarist.class);
        documentarist.execute();
        ctx.close();
    }

    @Test
    void testBeforeAdviceV2() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, BeforeAdviceV2.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("beforeAdviceV2"));

        var documentarist = ctx.getBean("documentarist", NewDocumentarist.class);
        documentarist.execute();
        ctx.close();
    }

    @Test
    void testBeforeAdviceV3() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, BeforeAdviceV3.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("beforeAdviceV3"));

        var documentarist = ctx.getBean("documentarist", NewDocumentarist.class);
        documentarist.execute();
        ctx.close();
    }

    @Test
    void testBeforeAdviceV4() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, BeforeAdviceV4.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("beforeAdviceV4"));

        var documentarist = ctx.getBean("documentarist", NewDocumentarist.class);
        documentarist.execute();
        ctx.close();
    }

    @Test
    void testAroundAdviceV1() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, AroundAdviceV1.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("aroundAdviceV1"));

        var documentarist = ctx.getBean("documentarist", NewDocumentarist.class);
        documentarist.execute();
        ctx.close();
    }

    @Test
    void testAroundAdviceV2() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, CommandingDocumentarist.class, AroundAdviceV2.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("aroundAdviceV2"));

        var documentarist = ctx.getBean("commandingDocumentarist", CommandingDocumentarist.class);
        documentarist.execute();
        ctx.close();
    }

    @Test
    void testAfterAdviceV1() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, AfterAdviceV1.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("afterAdviceV1"));

        var guitar = new Guitar();
        var guitarist = ctx.getBean("agustin", PretentiosGuitarist.class);
        guitarist.sing(guitar);
        LOGGER.info("----------------");
        guitar.setBrand("Musicman");

        assertThrows(IllegalArgumentException.class, () -> guitarist.sing(guitar), "Unacceptable guitar!");
        ctx.close();
    }

    @Test
    void testAfterReturningAdviceV1() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, AfterReturningAdviceV1.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("afterReturningAdviceV1"));

        var guitar = new Guitar();
        var guitarist = ctx.getBean("agustin", PretentiosGuitarist.class);
        guitarist.sing(guitar);
        LOGGER.info("------------------");
        guitar.setBrand("Musicman");

        assertThrows(IllegalArgumentException.class, () -> guitarist.sing(guitar), "Unacceptable guitar!");
        ctx.close();
    }

    @Test
    void testAfterThrowingAdviceV1() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, AfterThrowingAdviceV1.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("afterThrowingAdviceV1"));

        var guitar = new Guitar();
        var guitarist = ctx.getBean("agustin", PretentiosGuitarist.class);
        guitarist.sing(guitar);
        LOGGER.info("----------------------");
        guitar.setBrand("Musicman");

        assertThrows(IllegalArgumentException.class, () -> guitarist.sing(guitar), "Unacceptable guitar!");
        ctx.close();
    }

    @Test
    void testAfterThrowingAdviceV2() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, AfterThrowingAdviceV2.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("afterThrowingAdviceV2"));

        var guitar = new Guitar();
        var guitarist = ctx.getBean("agustin", PretentiosGuitarist.class);
        guitarist.sing(guitar);
        LOGGER.info("---------------------");
        guitar.setBrand("Musicman");

        assertThrows(RejectedInstrumentException.class, () -> guitarist.sing(guitar), "Unacceptable guitar!");
        ctx.close();
    }

    @Test
    void testAfterThrowingAdviceV5() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, BeforeAdviceV5.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("beforeAdviceV5"));

        var johnMayer = ctx.getBean("johnMayer", GrammyGuitarist.class);
        johnMayer.sing(new Guitar());

        var pretentiousGuitarist = ctx.getBean("agustin", PretentiosGuitarist.class);
        pretentiousGuitarist.sing(new Guitar());

        ctx.close();
    }

    @Test
    void testAfterThrowingAdviceV6(){
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class, BeforeAdviceV6.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("beforeAdviceV6"));

        var johnMayer = ctx.getBean("johnMayer", GrammyGuitarist.class);
        johnMayer.sing(new Guitar());

        var pretentiousGuitarist = ctx.getBean("agustin", PretentiosGuitarist.class);
        pretentiousGuitarist.sing(new Guitar());

        ctx.close();
    }

    @Test
    void testAfterThrowingAdviceV7(){
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectJAopConfig.class,BeforeAdviceV7.class);
        ctx.refresh();
        assertTrue(Arrays.asList(ctx.getBeanDefinitionNames()).contains("beforeAdviceV7"));

        var johnMayer = ctx.getBean("johnMayer",GrammyGuitarist.class);
        johnMayer.sing(new Guitar());

        var pretentiousGuitarist = ctx.getBean("agustin",PretentiosGuitarist.class);
        pretentiousGuitarist.sing(new Guitar());

        var gretsch = new Guitar();
        gretsch.setBrand("Gretsch");
        johnMayer.sing(gretsch);
        pretentiousGuitarist.sing(gretsch);

        ctx.close();
    }
}
