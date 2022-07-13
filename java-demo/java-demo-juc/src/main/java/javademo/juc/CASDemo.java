package javademo.juc;

import javademo.model.User;
import lombok.val;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class CASDemo {

    /**
     * CAS,compare and swap比较并替换。 CAS有三个参数：需要读写的内存位值（V）、进行比较的预期原值（A）和拟写入的新值(B)。当且仅当V的值等于A时，CAS才会通过原子方式用新值B来更新V的值，否则不会执行任何操作
     */
    public void atomicDemo() {
        val user1 = new User("t1", 10);
        val user2 = new User("t2", 20);
        AtomicReference<User> atomicReference = new AtomicReference<>(); //创建原子引用类
        atomicReference.set(user1); //设置主内存的共享变量为user1
        String format = "cas result=%b, current user=%s";
        System.out.println(String.format(format, atomicReference.compareAndSet(user1, user2), atomicReference.get().toString())); //比较并交换，如果主内存中的值是user1，那么原子类交换成功
        System.out.println(String.format(format, atomicReference.compareAndSet(user1, user2), atomicReference.get().toString())); //比较并交换，上面已经进行了交换，主内存的值应该是user2，本次会失败不进行交换
    }

    /**
     * CAS引起的ABA问题
     */
    public void atomicABADemo() throws InterruptedException {
        val user1 = new User("t1", 10);
        val user2 = new User("t2", 20);
        val user3 = new User("t3", 30);
        String format = "cas result=%b, current user=%s";

        AtomicReference<User> atomicReference = new AtomicReference<>(user1); //创建原子引用类
        new Thread(() -> {
            user1.setAge(11);
            System.out.println("thread1第一次修改user1 in thread1=" + user1.toString());
            atomicReference.compareAndSet(user1, user2);
            user1.setAge(10);
            System.out.println("thread1第二次修改user1 in thread1=" + user1.toString());
            atomicReference.compareAndSet(user2, user1); //模拟CAS的ABA场景，先从user1改成user2，再从user2改成user1
            System.out.println("user1 in thread1=" + user1.toString());
        }, "thread1").start();

        TimeUnit.SECONDS.sleep(1); //模拟业务操作等待一段时间后，其他线程执行

        new Thread(() -> {
            System.out.println("user1 in thread2=" + user1.toString());

            //再把user1改成user3，本次会操作成功，但是这时候的user1已经不是最开始的user1了,
            System.out.println(String.format(format, atomicReference.compareAndSet(user1, user3), atomicReference.get().toString()));
        }, "thread2").start();
    }

    public void atomicABAResolveDemo() throws InterruptedException {
        val user1 = new User("t1", 10);
        val user2 = new User("t2", 20);
        val user3 = new User("t3", 30);
        String format = "cas result=%b, current user=%s";

        AtomicStampedReference<User> atomicReference = new AtomicStampedReference<>(user1, 1); //创建原子引用类
        int stamp = atomicReference.getStamp();//获取第一次版本号

        new Thread(() -> {
            user1.setAge(11);
            System.out.println("thread1第一次查看user1 in thread1=" + user1.toString() + "stamp=" + stamp);

            //比较并更新，更新user1到user2并且更新版本号，参数一次是 期望值，更新值，期望版本号，更新版本号
            atomicReference.compareAndSet(user1, user2, stamp, stamp + 1);

            user1.setAge(10);
            int stamp1 = atomicReference.getStamp();//获取当前版本号
            System.out.println("thread1第二次查看user1 in thread1=" + user1.toString() + "stamp=" + stamp1);

            atomicReference.compareAndSet(user2, user1, stamp1, stamp1 + 1); //比较并更新，并且更新版本号
            System.out.println("thread1第三次查看user1 in thread1=" + user1.toString() + "stamp=" + atomicReference.getStamp());

        }, "thread1").start();

        TimeUnit.SECONDS.sleep(1); //模拟业务操作等待一段时间后，其他线程执行

        new Thread(() -> {
            System.out.println("thread2第一次查看user1 in thread1=" + user1.toString() + "stamp=" + stamp);
            //再把user1改成user3，本次操作失败，显示最新的user,
            System.out.println(String.format(format, atomicReference.compareAndSet(user1, user3, stamp, stamp + 1), atomicReference.getReference().toString()));
        }, "thread2").start();

    }
}
