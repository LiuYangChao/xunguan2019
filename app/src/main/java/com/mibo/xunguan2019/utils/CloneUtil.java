package com.mibo.xunguan2019.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Author liuyangchao
 * Date on 2019/6/20.10:47
 */

public class CloneUtil {

    public static Object clone(Serializable obj) {
        Object clone = cloneObject(obj);
        if (clone == null) {
            clone = cloneObject(obj);
        }
        return clone;
    }

    public static Object cloneObject(Serializable obj) {
        Object anotherObj = null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;

        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] bytes = baos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

            ois = new ObjectInputStream(bais);
            anotherObj = ois.readObject();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch (StackOverflowError error) {
            System.out.println("stack length " + error.getStackTrace().length);
            error.printStackTrace();
            return null;
        } finally {
            if (oos != null)
                try {
                    oos.close();
                } catch (IOException localIOException3) {
                }
            if (ois != null)
                try {
                    ois.close();
                } catch (IOException localIOException4) {
                }
        }
        return anotherObj;
    }

    public static int getObjectSize(Serializable obj) {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(bs);
            os.writeObject(obj);
            os.flush();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return bs.size();
    }

}
