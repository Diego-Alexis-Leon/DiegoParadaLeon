package Functions;

import java.io.File;
import java.io.IOException;


public class ExpandVideo {
    public static void expandVideo(String list, int n) throws IOException, InterruptedException {
        ProcessBuilder pb2 = new ProcessBuilder(
                "ffmpeg", "-y",
                "-f", "concat",
                "-safe", "0",
                "-i", list,
                "-c:v", "libx264",
                "-pix_fmt", "yuv420p",
                "video_v"+n+".mp4"
        );

        pb2.inheritIO();
        pb2.start().waitFor();

    }
    public static void fixSpeech(String speech) throws IOException, InterruptedException {
        ProcessBuilder analyzePb = new ProcessBuilder(
                "ffmpeg", "-i", speech,
                "-af", "loudnorm=I=-14:LRA=7:TP=-1.5:print_format=json",
                "-f", "null", "-"
        );
        analyzePb.inheritIO();
        analyzePb.start().waitFor();
        System.out.println("Fix Speech 1/3");

        ProcessBuilder normalizePb = new ProcessBuilder(
                "ffmpeg", "-y",
                "-i", speech,
                "-af", "loudnorm=I=-14:LRA=7:TP=-1.5:"
                + "measured_I=-20.1:"
                + "measured_LRA=4.8:"
                + "measured_TP=-3.2:"
                + "measured_thresh=-30.5:"
                + "offset=0.1:"
                + "linear=true",
                "speech_normalized.mp3"
        );
        normalizePb.inheritIO();
        normalizePb.start().waitFor();
        System.out.println("Fix Speech 2/3");
        File s = new File("speech_normalized.mp3");

        ProcessBuilder silencePb = new ProcessBuilder(
                "ffmpeg", "-y",
                "-f", "lavfi",
                "-i", "anullsrc=r=48000:cl=stereo",
                "-i", s.getAbsolutePath(),
                "-filter_complex", "[0:a]atrim=0:5[s];[s][1:a]concat=n=2:v=0:a=1[a]",
                "-map", "[a]",
                "speech_vfinal.mp4"
        );
        silencePb.inheritIO();
        silencePb.start().waitFor();
        System.out.println("Fix Speech 3/3");
    }

}
