package com.troyqu.javademo;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟简单的虚拟机栈，实现入栈和出栈
 */
public class VMStack {
    private int[] stack;
    private int top; // 栈顶指针

    public VMStack(int capacity) {
        stack = new int[capacity];
        top = -1; // 初始化时，栈为空
    }

    public void push(int value) {
        if (top == stack.length - 1) {
            System.out.println("栈已满，无法入栈");
            return;
        }
        top++;
        stack[top] = value;
        System.out.println(value + " 入栈成功");
    }

    public int pop() {
        if (top == -1) {
            System.out.println("栈为空，无法出栈");
            return -1; // 表示栈为空
        }
        int value = stack[top];
        top--;
        System.out.println(value + " 出栈成功");
        return value;
    }

    public static void main(String[] args) {
        VMStack stack = new VMStack(5);

        stack.push(10);
        stack.push(20);
        stack.push(30);

        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
    }
}

class VirtualMachineStack {
    private List<Integer> stack;
    private int top; // 栈顶指针

    public VirtualMachineStack(int capacity) {
        stack = new ArrayList<>(capacity);
        top = -1; // 初始化时，栈为空
    }

    public synchronized void push(int value) {
        if (top == stack.size() - 1) {
            System.out.println("栈已满，无法入栈");
            return;
        }
        top++;
        stack.add(value);
        System.out.println(value + " 入栈成功");
    }

    public synchronized int pop() {
        if (top == -1) {
            System.out.println("栈为空，无法出栈");
            return -1; // 表示栈为空
        }
        int value = stack.get(top);
        stack.remove(top);
        top--;
        System.out.println(value + " 出栈成功");
        return value;
    }

    public static void main(String[] args) {
        VirtualMachineStack stack = new VirtualMachineStack(5);

        stack.push(10);
        stack.push(20);
        stack.push(30);

        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
    }
}
