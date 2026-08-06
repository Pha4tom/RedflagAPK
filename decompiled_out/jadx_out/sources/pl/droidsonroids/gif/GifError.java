package pl.droidsonroids.gif;

/* JADX INFO: loaded from: classes2.dex */
public enum GifError {
    /* JADX INFO: Fake field, exist only in values array */
    EF0("NO_ERROR", "No error"),
    c("OPEN_FAILED", "Failed to open given input"),
    /* JADX INFO: Fake field, exist only in values array */
    EF3("READ_FAILED", "Failed to read from given input"),
    /* JADX INFO: Fake field, exist only in values array */
    EF5("NOT_GIF_FILE", "Data is not in GIF format"),
    /* JADX INFO: Fake field, exist only in values array */
    EF7("NO_SCRN_DSCR", "No screen descriptor detected"),
    /* JADX INFO: Fake field, exist only in values array */
    EF9("NO_IMAG_DSCR", "No image descriptor detected"),
    /* JADX INFO: Fake field, exist only in values array */
    EF11("NO_COLOR_MAP", "Neither global nor local color map found"),
    /* JADX INFO: Fake field, exist only in values array */
    EF12("WRONG_RECORD", "Wrong record type detected"),
    /* JADX INFO: Fake field, exist only in values array */
    EF10("DATA_TOO_BIG", "Number of pixels bigger than width * height"),
    /* JADX INFO: Fake field, exist only in values array */
    EF8("NOT_ENOUGH_MEM", "Failed to allocate required memory"),
    /* JADX INFO: Fake field, exist only in values array */
    EF6("CLOSE_FAILED", "Failed to close given input"),
    /* JADX INFO: Fake field, exist only in values array */
    EF4("NOT_READABLE", "Given file was not opened for read"),
    /* JADX INFO: Fake field, exist only in values array */
    EF2("IMAGE_DEFECT", "Image is defective, decoding aborted"),
    /* JADX INFO: Fake field, exist only in values array */
    EF4("EOF_TOO_SOON", "Image EOF detected before image complete"),
    /* JADX INFO: Fake field, exist only in values array */
    EF2("NO_FRAMES", "No frames found, at least one frame required"),
    /* JADX INFO: Fake field, exist only in values array */
    EF4("INVALID_SCR_DIMS", "Invalid screen size, dimensions must be positive"),
    /* JADX INFO: Fake field, exist only in values array */
    EF2("INVALID_IMG_DIMS", "Invalid image size, dimensions must be positive"),
    /* JADX INFO: Fake field, exist only in values array */
    EF4("IMG_NOT_CONFINED", "Image size exceeds screen size"),
    /* JADX INFO: Fake field, exist only in values array */
    EF2("REWIND_FAILED", "Input source rewind failed, animation stopped"),
    /* JADX INFO: Fake field, exist only in values array */
    EF4("INVALID_BYTE_BUFFER", "Invalid and/or indirect byte buffer specified"),
    d("UNKNOWN", "Unknown error");

    public final String a;
    public int b;

    GifError(String str, String str2) {
        this.b = i;
        this.a = str2;
    }
}
