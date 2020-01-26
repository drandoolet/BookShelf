package bookshelf.spring.customdao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.boot.SpringApplication;

import java.util.List;

@RunWith(BlockJUnit4ClassRunner.class)
public class ReflectionTest {

    @Test
    public void testReflection() {
        playWithReflection();
    }

    private void playWithReflection() {
        try {
            Class<?> clazz = Class.forName(SpringApplication.class.getName());
            printAllConstructors(clazz);
            printAllMethods(clazz);
            printAllFields(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printAllFields(Class<?> c) {
        System.out.println(String.format("Fields of %s:", c.getSimpleName()));
        List.of(c.getFields()).forEach(System.out::println);
    }

    private void printAllMethods(Class<?> c) {
        System.out.println(String.format("Methods of %s:", c.getSimpleName()));
        List.of(c.getMethods()).forEach(System.out::println);
    }

    private void printAllConstructors(Class<?> c) {
        System.out.println(String.format("Constructors of %s:", c.getSimpleName()));
        List.of(c.getConstructors()).forEach(System.out::println);
    }
}
