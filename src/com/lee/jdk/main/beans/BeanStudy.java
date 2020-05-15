package com.lee.jdk.main.beans;

import com.lee.jdk.pojo.Student;

import java.beans.Beans;
import java.beans.FeatureDescriptor;

public class BeanStudy {

    public static void main(String[] args) {
        try {
            Student student = (Student) Beans.instantiate(ClassLoader.getSystemClassLoader(), Student.class.getName());
            System.out.println(student.getAge());

            FeatureDescriptor fd = new FeatureDescriptor();
            fd.setDisplayName("study");
            fd.setHidden(false);
            fd.setName("descriptor");
            fd.setPreferred(false);
            fd.setValue("1","A");
            fd.setValue("2","B");
            fd.setValue("3","C");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
