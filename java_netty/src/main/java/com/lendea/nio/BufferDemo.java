package com.lendea.nio;

import java.nio.ByteBuffer;

/**
 * @author lendea
 * @date 2022/8/30 10:37
 */
public class BufferDemo {

    public static void main(String[] args) {
        byte[] data = new byte[]{55, 56, 57, 58, 59};
        final ByteBuffer buffer = ByteBuffer.wrap(data);
        System.out.println(buffer.capacity());
        System.out.println(buffer.position());
        System.out.println(buffer.limit());

        System.out.println(buffer.get());
        System.out.println(buffer.position());
        buffer.mark();

        buffer.position(4);
        System.out.println(buffer.get());

        buffer.reset();
        System.out.println(buffer.get());
        System.out.println(buffer.position());
    }
}
