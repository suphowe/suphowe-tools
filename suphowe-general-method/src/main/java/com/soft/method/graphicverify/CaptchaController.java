package com.soft.method.graphicverify;

import com.wf.captcha.*;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;

/**
 * 图片验证码
 * @author suphowe
 */
@Controller
public class CaptchaController {

    @RequestMapping("/graphicverify")
    public String getIndex(){
        return "graphicverify/index.html";
    }

    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // png类型
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48);
        // 获取验证码的字符
        specCaptcha.text();
        // 获取验证码的字符数组
        specCaptcha.textChar();

        // gif类型
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48);

        // 中文类型
        ChineseCaptcha chineseCaptcha = new ChineseCaptcha(130, 48);

        // 中文gif类型
        ChineseGifCaptcha chineseGifCaptcha = new ChineseGifCaptcha(130, 48);

        // 算术类型
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        // 几位数运算，默认是两位
        captcha.setLen(3);
        // 获取运算的公式：3+2=?
        captcha.getArithmeticString();
        // 获取运算的结果：5
        captcha.text();

        /*
         验证码字符类型
         TYPE_DEFAULT	数字和字母混合
         TYPE_ONLY_NUMBER	纯数字
         TYPE_ONLY_CHAR	纯字母
         TYPE_ONLY_UPPER	纯大写字母
         TYPE_ONLY_LOWER	纯小写字母
         TYPE_NUM_AND_UPPER	数字和大写字母
         只有SpecCaptcha和GifCaptcha设置才有效果
         SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
         captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
         */
        /*
         字体设置
         SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
         设置内置字体
         captcha.setFont(Captcha.FONT_1);
         设置系统字体
         */
        chineseCaptcha.setFont(new Font("楷体", Font.PLAIN, 28));

        /*
         SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
         specCaptcha.toBase64();
         如果不想要base64的头部data:image/png;base64,
         加一个空的参数即可
         specCaptcha.toBase64("");
         */

        /*
         输出到文件
         FileOutputStream outputStream = new FileOutputStream(new File("C:/captcha.png"))
         SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
         specCaptcha.out(outputStream);
         */

        //输出
        CaptchaUtil.out(chineseCaptcha, request, response);
    }

    @ResponseBody
    @RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
    public String verifyCode(HttpServletRequest request, String verCode){
        if (!CaptchaUtil.ver(verCode, request)) {
            // 清除session中的验证码
            CaptchaUtil.clear(request);
            return "验证码不正确";
        }
        return "正确";
    }

}
