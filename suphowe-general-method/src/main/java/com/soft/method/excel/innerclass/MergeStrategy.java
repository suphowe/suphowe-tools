package com.soft.method.excel.innerclass;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 合并excel单元格处理
 * @author suphowe
 */
public class MergeStrategy extends AbstractMergeStrategy{

    /**
     * 合并坐标集合
     */
    private final List<CellRangeAddress> cellRangeAddress;

    public MergeStrategy(List<CellRangeAddress> cellRangeAddress) {
        this.cellRangeAddress = cellRangeAddress;
    }

    /**
     * merge
     * @param sheet sheet
     * @param cell cell
     * @param head 头
     * @param relativeRowIndex 行号
     */
    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        //合并单元格
        /**
         *  ****加个判断:if (cell.getRowIndex() == 1 && cell.getColumnIndex() == 0) {}****
         * 保证每个cell被合并一次，如果不加上面的判断，因为是一个cell一个cell操作的，
         * 例如合并A2:A3,当cell为A2时，合并A2,A3，但是当cell为A3时，又是合并A2,A3，
         * 但此时A2,A3已经是合并的单元格了
         */
        if (CollectionUtils.isNotEmpty(cellRangeAddress)) {
            if (cell.getRowIndex() == 1 && cell.getColumnIndex() == 0) {
                for (CellRangeAddress item : cellRangeAddress) {
                    sheet.addMergedRegionUnsafe(item);
                }
            }
        }
    }
}
