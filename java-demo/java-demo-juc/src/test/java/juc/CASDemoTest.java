package juc;

import javademo.juc.CASDemo;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class CASDemoTest {

    @Test
    public void testAtomicDemo() {
        val casDemo = new CASDemo();
        casDemo.atomicDemo();
    }

    @Test
    public void testAtomicABADemo() throws InterruptedException {
        val casDemo = new CASDemo();
        casDemo.atomicABADemo();
    }

    @Test
    public void testAtomicABAResolveDemo() throws InterruptedException {
        val casDemo = new CASDemo();
        casDemo.atomicABAResolveDemo();
    }
}
