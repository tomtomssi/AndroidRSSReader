package com.example.AndroidRSSReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.w3c.dom.NodeList;

/**
 * Created by Tatu on 10.6.2014.
 */
public class DisplaySingleItem extends Activity{
    private NodeList rootNodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize(){
        Intent intent = getIntent();
        this.setTitle(intent.getStringExtra("PUBLISH_DATE"));
    }
}