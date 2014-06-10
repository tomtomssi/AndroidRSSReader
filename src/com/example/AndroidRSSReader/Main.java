package com.example.AndroidRSSReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Main extends Activity{
    /**
     * Called when the activity is first created.
     */

    ListView listView;
    Intent singleItemIntet;
    XmlReader reader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        reader = new XmlReader();
        initialize(reader);
    }

    private void initialize(XmlReader reader) {
        this.listView = (ListView) this.findViewById(R.id.mainList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, android.R.id.text1, reader.getTitleValues());
        listView.setAdapter(adapter);
        initializeIntents();
        initializeListeners();
    }

    private void initializeListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                prepareExtras((int)l);
                startActivity(singleItemIntet);
            }
        });
    }

    private void initializeIntents() {
        Intent intent = new Intent(getApplicationContext(), DisplaySingleItem.class);
        this.singleItemIntet = intent;
    }

    private void prepareExtras(int l){
        singleItemIntet.putExtra("ITEM", reader.getNodeValueById(l, XmlReader.Operators.ITEM));
        singleItemIntet.putExtra("CATEGORY", reader.getNodeValueById(l, XmlReader.Operators.CATEGORY));
        singleItemIntet.putExtra("PUBLISH_DATE", reader.getNodeValueById(l, XmlReader.Operators.PUBLISH_DATE));
        singleItemIntet.putExtra("LINK", reader.getNodeValueById(l, XmlReader.Operators.LINK));
        singleItemIntet.putExtra("TITLE", reader.getNodeValueById(l, XmlReader.Operators.TITLE));
    }
}
