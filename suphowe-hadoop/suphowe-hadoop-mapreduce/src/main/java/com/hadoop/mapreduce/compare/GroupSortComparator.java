package com.hadoop.mapreduce.compare;

import com.hadoop.mapreduce.model.GroupSort;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 统计
 * @author suphowe
 */
public class GroupSortComparator extends WritableComparator {

    public GroupSortComparator() {
        super(GroupSort.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        GroupSort groupSort1 = (GroupSort) a;
        int num1 = groupSort1.getNum();
        GroupSort groupSort2 = (GroupSort) b;
        int num2 = groupSort2.getNum();
        // comparator输出：20 1
        // System.out.println("comparator输出：" + model.getName() + " " +
        // model.getNum());
        // comparator2输出：20 10
        // System.out.println("comparator2输出：" + model2.getName() + " " +
        // model2.getNum());
        return num1 == num2 ? 0 : (num1 < num2 ? -1 : 1);
    }
}
