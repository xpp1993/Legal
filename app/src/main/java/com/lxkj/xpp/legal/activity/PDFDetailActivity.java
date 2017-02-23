package com.lxkj.xpp.legal.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.lxkj.xpp.legal.R;
import com.lxkj.xpp.legal.base.BaseActivity;
import com.lxkj.xpp.legal.base.BaseMvpPresenter;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 熊萍萍 on 2017/1/5/005.
 * 加载pdf 详情页
 */

public class PDFDetailActivity extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener {
    @BindView(R.id.pdfView)
    public PDFView pdfView;
    @BindView(R.id.bar_iv_left)
    public ImageView bar_left;
    @BindView(R.id.bar_tv_title_center)
    public TextView bar_title;
    private static final String TAG = PDFDetailActivity.class.getSimpleName();
    private Uri uri = null;
    private Integer pageNumber = 0;
    private String pdfFileName;

    @Override
    protected void initWidgets(Bundle savedInstanceState) {
        super.initWidgets(savedInstanceState);
        bar_left.setVisibility(View.VISIBLE);
        bar_title.setVisibility(View.VISIBLE);
        bar_title.setText("详情");
        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            uri = intent.getData();
            Log.d(TAG, uri.toString());
            displayFromUri(uri);
        }
    }

    @Override
    protected int setContentViewResId() {
        return R.layout.activity_pdf_detail;
    }

    @Override
    protected BaseMvpPresenter setPresenter() {
        return null;
    }

    /**
     * 通过uri 展示pdf文件
     *
     * @param uri
     */
    private void displayFromUri(Uri uri) {
        //pdfFileName = getFileName(uri);
        pdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .onLoad(this)
                .load();
    }

    /**
     * 获取文件的名称
     *
     * @param uri
     * @return
     */
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title=" + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @OnClick({R.id.bar_iv_left})
    void click(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }
}
