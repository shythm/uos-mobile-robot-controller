package uos.teamkernel.prototype;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import uos.teamkernel.common.Point;
import uos.teamkernel.common.Spot;
import uos.teamkernel.model.MapModel;
import uos.teamkernel.model.MobileRobotModel;
import uos.teamkernel.sim.SimAddOn;

public class VoiceRecognizerPrototype implements SimAddOn<Void> {
    private TargetDataLine line;
    private AudioInputStream audioInputStream;
    private File tempFile;
    private Thread recordingThread;
    private String recordedString;

    private VoiceRecognizeAPI voiceRecognizeAPI = new VoiceRecognizeAPI();
    private boolean startFlag = false;

    public void toggleStartFlag() {
        startFlag = (!startFlag);
    }

    private void startRecording() {
        try {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                return;
            }

            line = (TargetDataLine)AudioSystem.getLine(info);
            line.open(format);
            line.start();

            audioInputStream = new AudioInputStream(line);
            recordingThread = new Thread(() -> {
                try {
                    tempFile = File.createTempFile("recordingAudio", ".wav");
                    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, tempFile);
                } catch (IOException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            });

            recordingThread.start();
        } catch (LineUnavailableException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (line != null) {
            line.stop();
            line.close();
        }

        if (recordingThread != null) {
            recordingThread.interrupt();
        }

        recordedString = voiceRecognizeAPI.getString(tempFile);

        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    private Spot getSpotType(String context) {
        if (context.contains("중요")) {
            return Spot.COLOR_BLOB;
        } else if (context.contains("위험")) {
            return Spot.HAZARD;
        }
        return Spot.NONE;
    }

    private Point getPoint(String context) {
        Pattern pattern = Pattern.compile("(\\d+)-(\\d+)");
        Matcher matcher = pattern.matcher(context);

        if (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return new Point(x, y);
        }
        return null;
    }

    private void addSpot(MapModel map) {
        String context = recordedString.substring(9, recordedString.length() - 2);
        System.out.println(context);
        Spot newSpot = getSpotType(context);
        System.out.println(newSpot);
        Point newPoint = getPoint(context);
        System.out.println(newPoint);
        map.setSpot(newPoint, newSpot);
    }

    public Void call(MobileRobotModel mobileRobot, MapModel map) {
        if (!startFlag) {
            toggleStartFlag();
            startRecording();
        } else {
            toggleStartFlag();
            stopRecording();
            addSpot(map);
        }
        return null;
    }

}
