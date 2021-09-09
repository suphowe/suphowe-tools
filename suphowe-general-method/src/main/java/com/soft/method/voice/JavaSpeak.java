package com.soft.method.voice;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.File;

/**
 * 播放语音并存入MP3文件
 * @author suphowe
 */
public class JavaSpeak {

    public static void main(String[] args) {
        JavaSpeak js = new JavaSpeak();
        String[] voice = js.voice();
        String[] device = js.device();
        String outputDir = "E:/voice/";

        for (int i = 0; i < device.length; i++) {

            for (int j = 1; j < voice.length; j++) {

                String voiceDir = outputDir + j;
                js.createVoiceDir(voiceDir);
                //指定文件音频输出文件位置
                String output = voiceDir + "/" + device[i] + "_" + j + ".mp3";


                ActiveXComponent ax = null;

                String str = device[i] + voice[j];
                try {
                    ax = new ActiveXComponent("Sapi.SpVoice");

                    //运行时输出语音内容
                    Dispatch spVoice = ax.getObject();
                    // 音量 0-100
                    ax.setProperty("Volume", new Variant(100));
                    // 语音朗读速度 -10 到 +10
                    ax.setProperty("Rate", new Variant(-1));
                    // 进行朗读
                    Dispatch.call(spVoice, "Speak", new Variant(str));

                    //下面是构建文件流把生成语音文件

                    ax = new ActiveXComponent("Sapi.SpFileStream");
                    Dispatch spFileStream = ax.getObject();

                    ax = new ActiveXComponent("Sapi.SpAudioFormat");
                    Dispatch spAudioFormat = ax.getObject();

                    //设置音频流格式
                    Dispatch.put(spAudioFormat, "Type", new Variant(22));
                    //设置文件输出流格式
                    Dispatch.putRef(spFileStream, "Format", spAudioFormat);
                    //调用输出 文件流打开方法，在指定位置输出一个.wav文件
                    Dispatch.call(spFileStream, "Open", new Variant(output), new Variant(3), new Variant(true));
                    //设置声音对象的音频输出流为输出文件对象
                    Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
                    //设置音量 0到100
                    Dispatch.put(spVoice, "Volume", new Variant(100));
                    //设置朗读速度
                    Dispatch.put(spVoice, "Rate", new Variant(0));
                    //开始朗读
                    Dispatch.call(spVoice, "Speak", new Variant(str));

                    //关闭输出文件
                    Dispatch.call(spFileStream, "Close");
                    Dispatch.putRef(spVoice, "AudioOutputStream", null);

                    spAudioFormat.safeRelease();
                    spFileStream.safeRelease();
                    spVoice.safeRelease();
                    ax.safeRelease();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


    }

    /**
     * 判断路径是否存在,不存在则新建文件夹
     * @param voiceDir 文件夹路径
     */
    private void createVoiceDir(String voiceDir){
        File voiceFile = new File(voiceDir);
        if(!voiceFile.isDirectory()){
            voiceFile.mkdirs();
        }
    }

    public String[] device(){
        String[] device = new String[4];
        device[0] = "K129+075";
        device[1] = "K130+275";
        device[2] = "K128+170";
        device[3] = "K130+507";
        return device;
    }

    public String[] voice(){
        String[] voice = new String[10];
        voice[0] = " 未检测到事件";
        voice[1] = " 检测到拥堵事件";
        voice[2] = " 检测到停车事件";
        voice[3] = " 检测到超速事件";
        voice[4] = " 检测到逆行事件";
        voice[5] = " 检测到行人事件";
        voice[6] = " 检测到穿越事件";
        voice[7] = " 检测到非机动车事件";
        voice[8] = " 检测到速度过大事件";
        voice[9] = " 检测到流量过大事件";
        return voice;
    }
}
