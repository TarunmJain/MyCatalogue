package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.centura_technologies.mycatalogue.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.ScrollBar;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import java.io.File;

/**
 * Created by Centura User1 on 18-09-2016.
 */
public class PdfActivity extends AppCompatActivity {
    Toolbar toolbar;
    static PDFView pdfView;
    static ScrollBar scrollBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("PDF");
        pdfView = (PDFView) findViewById(R.id.pdfView);
        scrollBar = (ScrollBar) findViewById(R.id.scrollBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pdfView.setScrollBar(scrollBar);
        scrollBar.setHorizontal(false);
        pdfView.useBestQuality(true);
        Intent i = getIntent();
        File file = new File(i.getStringExtra("url"));
        if (file.canRead()) {
            pdfView.fromFile(file).defaultPage(1).swipeVertical(true).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    //Toast.makeText(PdfActivity.this, String.valueOf(nbPages), Toast.LENGTH_LONG).show();
                }
            }).load();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {                //On Back Arrow pressed
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
