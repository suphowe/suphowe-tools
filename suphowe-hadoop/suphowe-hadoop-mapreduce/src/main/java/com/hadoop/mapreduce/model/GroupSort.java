package com.hadoop.mapreduce.model;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 分组排序model类
 * @author suphowe
 */
public class GroupSort implements WritableComparable<GroupSort> {

    private int name;
    private int num;

    public GroupSort() {
    }

    public GroupSort(int name, int num) {
        this.name = name;
        this.num = num;
    }

    public void set(int name, int num) {
        this.name = name;
        this.num = num;
    }

    @Override
    public int compareTo(GroupSort groupSort) {
        if (this.name != groupSort.getName()) {
            return this.name < groupSort.getName() ? -1 : 1;
        } else if (this.num != groupSort.getNum()) {
            return this.num < groupSort.getNum() ? -1 : 1;
        } else {
            return 0;
        }
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(this.name);
        output.writeInt(this.num);
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        this.name = input.readInt();
        this.num = input.readInt();
    }

    @Override
    public String toString() {
        return name + "\t" + num;
    }

    @Override
    public int hashCode() {
        return this.name * 157 + this.num;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
