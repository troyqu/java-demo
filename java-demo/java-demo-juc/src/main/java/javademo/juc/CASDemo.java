package javademo.juc;

import javademo.model.User;
import lombok.val;

import java.util.concurrent.atomic.AtomicReference;

public class CASDemo {

    /**
     * CAS,compare and swap比较并替换。 CAS有三个参数：需要读写的内存位值（V）、进行比较的预期原值（A）和拟写入的新值(B)。当且仅当V的值等于A时，CAS才会通过原子方式用新值B来更新V的值，否则不会执行任何操作
     */
    public void atomicDemo() {
        val user1 = new User("t1", 20);
        val user2 = new User("t2", 30);
        AtomicReference<User> atomicReference = new AtomicReference<>(); //创建原子引用类
        atomicReference.set(user1); //设置主内存的共享变量为user1
        String format = "cas result=%b, user=%s";
        System.out.println(String.format(format, atomicReference.compareAndSet(user1, user2), atomicReference.get().toString())); //比较并交换，如果主内存中的值是user1，那么原子类交换成功
        System.out.println(String.format(format, atomicReference.compareAndSet(user1, user2), atomicReference.get().toString())); //比较并交换，上面已经进行了交换，主内存的值应该是user2，本次会失败不进行交换
    }

}
