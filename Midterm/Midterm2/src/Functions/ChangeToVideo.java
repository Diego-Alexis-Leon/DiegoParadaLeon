package Functions;

import java.io.IOException;

public class ChangeToVideo {

    public static void  imageToVideo(String image, int elementNumber) throws IOException, InterruptedException {
        String n = "clip_"+elementNumber+".mp4";
        String t;
        if (elementNumber ==201){
            t = "5";
        }else {
            t ="10";
        }

        ProcessBuilder pb1 = new ProcessBuilder(
                "ffmpeg", "-y",
                "-loop", "1",
                "-i", image,
                "-t", t,
                "-r", "30",
                "-vf", "scale=1024:576:force_original_aspect_ratio=decrease,"
                + "pad=1024:576:(ow-iw)/2:(oh-ih)/2",
                "-an",
                "-c:v", "libx264",
                "-pix_fmt", "yuv420p",
                n
        );
        pb1.inheritIO();
        pb1.start().waitFor();
    }
    public static void imageToVideoWithText(String image, int elements, String subtitle)
            throws IOException, InterruptedException {

        String n = "clip_" + elements + ".mp4";

        // Escapar caracteres problemáticos para FFmpeg
        String safeSubtitle = subtitle
                .replace("\\", "\\\\")
                .replace(":", "\\:")
                .replace("'", "\\'")
                .replace(",", "\\,")
                .replace("[", "\\[")
                .replace("]", "\\]");

        String vf = "scale=1024:576:force_original_aspect_ratio=decrease,"
                + "pad=1024:576:(ow-iw)/2:(oh-ih)/2,"
                + "drawtext=text='" + safeSubtitle + "':"
                + "fontcolor=white:"
                + "fontsize=30:"
                + "box=1:"
                + "boxcolor=black@0.45:"
                + "boxborderw=12:"
                + "x=(w-text_w)/2:"
                + "y=h-80";

        ProcessBuilder pb1 = new ProcessBuilder(
                "ffmpeg", "-y",
                "-loop", "1",
                "-i", image,
                "-t", "5",
                "-r", "30",
                "-vf", vf,
                "-an",
                "-c:v", "libx264",
                "-pix_fmt", "yuv420p",
                n
        );

        pb1.inheritIO();
        pb1.start().waitFor();
    }
    public static void videoToVideo(String video, int elementNumber) throws IOException, InterruptedException {
        String n = "clip_"+elementNumber+".mp4";/*
        if (elements ==0){
            n = "final_video.mp4";
        }else {
            n =  "part_of_video.mp4";
        }*/
        ProcessBuilder pb1 = new ProcessBuilder(
                "ffmpeg", "-y",
                "-i", video,
                "-r", "30",
                "-vf", "scale=1024:576:force_original_aspect_ratio=decrease,"
                + "pad=1024:576:(ow-iw)/2:(oh-ih)/2",
                "-an",
                "-c:v", "libx264",
                "-pix_fmt", "yuv420p",
                n
        );
        pb1.inheritIO();
        pb1.start().waitFor();
    }
}
