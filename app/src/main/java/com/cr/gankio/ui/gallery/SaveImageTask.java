package com.cr.gankio.ui.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cr.gankio.R;

import java.io.File;

/**
 * @author RUI CAI
 * @date 2018/8/6
 */
public class SaveImageTask extends ThreadUtils.Task<File> {

    private static final String savePath = Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_PICTURES).getAbsolutePath();
    private Context mContext;
    private Bitmap bitmap;

    public SaveImageTask(Context context, Bitmap bitmap) {
        mContext = context;
        this.bitmap = bitmap;
    }

    @Nullable
    @Override
    public File doInBackground() throws Throwable {
        String fileName = System.currentTimeMillis() + ".jpg";
        File save_file = new File(savePath, fileName);
        boolean success = ImageUtils.save(bitmap, save_file, Bitmap.CompressFormat.JPEG);
        if (success) {
            return save_file;
        } else {
            throw new Exception();
        }
    }

    @Override
    public void onSuccess(@Nullable File result) {
        MediaScannerConnection.scanFile(mContext, new String[]{result.getAbsolutePath()}, null,
                (path, uri) -> ToastUtils.showShort(R.string.save_complete)
        );
    }


    @Override
    public void onCancel() {

    }

    @Override
    public void onFail(Throwable t) {
        ToastUtils.showShort(R.string.save_fail);
    }
}
