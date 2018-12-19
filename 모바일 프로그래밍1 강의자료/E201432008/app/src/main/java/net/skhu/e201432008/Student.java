package net.skhu.e201432008;


import java.util.Objects;

public class Student {
    String name;
    int studentNumber;

    public Student(){

    }

    public Student(String name, int studentNumber){
        this.name=name;
        this.studentNumber=studentNumber;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {

        this.studentNumber = studentNumber;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, studentNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student == false) return false;
        Student other = (Student)obj;
        return this.name.equals(other.name)&&
                this.studentNumber==other.studentNumber;
    }

    @Override
    public String toString() {
        return String.format("이름:%s 학번:%d", name, studentNumber);
    }
}
