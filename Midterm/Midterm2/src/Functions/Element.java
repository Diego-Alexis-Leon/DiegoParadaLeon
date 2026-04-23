package Functions;

import java.io.File;

public class Element {
    private File file;
    private long date;
    private String extension;
    private String path;

    public Element(File file, long date, String extension, String path) {
        this.file = file;
        this.date = date;
        this.extension = extension;
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public long getDate() {
        return date;
    }

    public String getExtension() {
        return extension;
    }
    public String getPath() {
        return path;
    }
}