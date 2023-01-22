package android.print;

import android.os.Build;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PdfPrint;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;

public class PdfPrintMaster {
    private static final String TAG = PdfPrint.class.getSimpleName();
    private final PrintAttributes printAttributes;

    public PdfPrintMaster(PrintAttributes printAttributes) {
        this.printAttributes = printAttributes;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void print(final PrintDocumentAdapter printAdapter, final File path, final String fileName,
                      PdfPrint.CallbackPrint callback) {
        printAdapter.onLayout(null, printAttributes, null,
                new PrintDocumentAdapter.LayoutResultCallback() {
                    @Override
                    public void onLayoutFinished(PrintDocumentInfo info, boolean changed) {
                        printAdapter.onWrite(new PageRange[]{PageRange.ALL_PAGES}, getOutputFile(path, fileName),
                                new CancellationSignal(), new PrintDocumentAdapter.WriteResultCallback() {
                                    @Override
                                    public void onWriteFinished(PageRange[] pages) {
                                        super.onWriteFinished(pages);
                                        if (pages.length > 0) {
                                            File file = new File(path, fileName);
                                            String path = file.getAbsolutePath();
                                            callback.success(path);
                                        } else {
                                            callback.onFailure("Failed");
                                        }

                                    }
                                });
                    }
                }, null);
    }

    private ParcelFileDescriptor getOutputFile(File path, String fileName) {
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, fileName);
        try {
            file.createNewFile();
            return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE);
        } catch (Exception e) {
            Log.e(TAG, "Failed to open ParcelFileDescriptor", e);
        }
        return null;
    }


    public interface CallbackPrint {
        void onSuccess(String path);

        void onFailure(Exception ex);
    }
}
