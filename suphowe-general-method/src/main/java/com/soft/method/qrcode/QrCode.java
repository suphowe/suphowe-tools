package com.soft.method.qrcode;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;

/**
 * 二维码文件公共类
 * @author suphowe
 */
public class QrCode {

    /**
     * 生成二维码并显示二维码图片
     * @param height 高
     * @param weight 宽
     * @param qrCodeFile 生成的二维码图片文件
     * @param qrInfo 二维码消息
     * @throws Exception 异常
     */
    public void createQrCode(int height, int weight, String qrCodeFile, String qrInfo) throws Exception {
        QrConfig qc = new QrConfig();
        qc.setHeight(height);
        qc.setWidth(weight);
        QrCodeUtil.generate(qrInfo, qc, FileUtil.file(qrCodeFile));
    }

}
