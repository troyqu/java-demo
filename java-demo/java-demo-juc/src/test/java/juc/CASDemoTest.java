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

}
