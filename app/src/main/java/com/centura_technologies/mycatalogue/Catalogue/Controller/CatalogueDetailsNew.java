package com.centura_technologies.mycatalogue.Catalogue.Controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.centura_technologies.mycatalogue.Catalogue.View.DetailMenuAdapter;
import com.centura_technologies.mycatalogue.R;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

public class CatalogueDetailsNew extends AppCompatActivity {

    RecyclerView menulyaout;
    ImageView productImage;
    VideoView productDetailvedio;
    WebView productDetailwebview;
    LinearLayout imagelayout,vediolayout,weblayout,pdflayout,pptlayout,infolayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue_details_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



        menulyaout= (RecyclerView) findViewById(R.id.menulyaout);
        imagelayout= (LinearLayout) findViewById(R.id.imagelayout);
        vediolayout= (LinearLayout) findViewById(R.id.vediolayout);
        weblayout= (LinearLayout) findViewById(R.id.weblayout);
        pdflayout= (LinearLayout) findViewById(R.id.pdflayout);
        pptlayout= (LinearLayout) findViewById(R.id.pptlayout);
        infolayout= (LinearLayout) findViewById(R.id.infolayout);
        productImage= (ImageView) findViewById(R.id.productDetailImageview);
        productDetailvedio= (VideoView) findViewById(R.id.productDetailvedio);
        productDetailwebview= (WebView) findViewById(R.id.productDetailwebview);
        menulyaout.setLayoutManager(new LinearLayoutManager(CatalogueDetailsNew.this));


        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void LoadHTML(){
        productDetailwebview.getSettings().setJavaScriptEnabled(true);
        productDetailwebview.loadUrl("");
    }

    public void LoadVedio(){
        String vidAddress;
        vidAddress=CatalogueDetails.videourl;
        Uri vidUri = Uri.parse(vidAddress);
        productDetailvedio.setVideoURI(vidUri);
        productDetailvedio.start();
        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(productDetailvedio);
        productDetailvedio.setMediaController(vidControl);
    }

    public void LoadPDF(){
        Intent intent = new Intent(this, PdfView.class);
        intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, CatalogueDetails.pdfurl);
        startActivity(intent);
    }

}
