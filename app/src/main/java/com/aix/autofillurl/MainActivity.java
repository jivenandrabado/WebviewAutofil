package com.aix.autofillurl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.autofill.AutofillValue;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    JSONObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WebView webView = findViewById(R.id.webView);
        String lName = "jivenandrabado14@gmail.com";
        String password = "123456";
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
//        webView.loadUrl("http://192.168.1.252/#/client/login");
//        webView.loadUrl("http://192.168.1.251:4200/#/client/login");
        webView.loadUrl("http://192.168.1.251:5501");
//        webView.loadUrl("https://docs.google.com/forms/d/1t70k83lQqjDh5NVmYnTl8IYFoWrf_dOd2Vs2LwhqIZ0/edit");

        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        String js = "";



        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String jsCode = "" + "function parseForm(form){" +
                        "var values='';" +
                        "console.log('RESULT 1='+form.elements);" +
                        "for(var i=0 ; i< form.elements.length; i++){" +
                        "   values+=form.elements[i].name+'='+form.elements[i].value+'&'" +
                        "}" +
                        "return [values]" +
                        "   }"
//                        +
//                        "for(var i=0 ; i< document.forms.length ; i++){" +
//                        "   parseForm(document.forms[i]);" +
//                        "};"
                        ;
                //test
                String labels = "(function() {  var x = document.querySelectorAll('input'); var y = ''; for(var val of x.values()){ y+=val + ', '  } return y }) ()";
                String inputFields = "(function() { return (document.querySelectorAll('input')[0].name); }) ()";
                String inputFieldss = "(function() { var values = document.querySelectorAll('input'); var output = ''; for (var i = 0; i< values.length; i++){ output+=values[i]} return output }) ()";

                String jsonStr = "(function() {  var x = document.getElementsByTagName('input'); var y = ''; " +
                        "obj = {for(i=0; i<x.length; i++) { console.log(x[i].id + x[i].name); obj += id + i + ':' + x[i].id + ','; } obj += '}'+ return JSON.stringify(x) }) ()";

//                String y = "obj='(';for(i=0;i<x.length;i++){obj+='id'+i+' : \''+x[i].id \'' +' ,'}";
//                String z = "(function () {var x=document.getElementsByTagName('input');obj='(';for(i=0;i<x.length;i++){obj+='id'+i+' : \\''+x[i].id+'\\' ,'}obj+='}'; return obj }) ()";
//
//                String a = "(function() { var x = document.getElementsByTagName('input');obj = '{';for(i=0; i<x.length; i++) {obj += 'field' + i + ' : { id : \\'' + x[i].id + '\\'}';if(i<x.length-1)obj += ', '}obj += '}';return(JSON.stringify(obj));}) ()";
//
//                String b = "(function() {var inputs = document.getElementsByTagName('input');var labels = document.getElementsByTagName('label');obj = '{';for(i=0; i<inputs.length; i++) {obj += 'field' + i + ' : {id : \\'' + inputs[i].id + '\\'';for (j=0; j<labels.length; j++) {if(inputs[i].name === labels[j].htmlFor)obj += ', label : \\'' + labels[j].htmlFor + '\\'';}obj += '}';if(i<inputs.length-1)obj += ', '}obj += '}';return JSON.stringify(obj)) ()";
                String jsonInputLabel = "(function() {var inputs = document.getElementsByTagName('input');var labels = document.getElementsByTagName('label');obj = '{';for(i=0; i<inputs.length; i++) {obj += 'field' + i + ' : {id : \\'' + inputs[i].id + '\\'';for (j=0; j<labels.length; j++) {if(inputs[i].name === labels[j].htmlFor)obj += ', label : \\'' + labels[j].htmlFor + '\\'';}obj += '}';if(i<inputs.length-1)obj += ', ';}obj += '}'; return obj}) ()";


                String form = "(function() { return ('<html>'+document.getElementsByTagName('form')[0].innerHTML+'</html>'); })();";
                String input = "(function() {  var x = document.querySelectorAll('input'); var y = ''; for(var val of x.values()){ y+=val.id + ', '  } return y }) ()";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    view.evaluateJavascript(jsonInputLabel, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            JsonReader reader = new JsonReader(new StringReader(value));
                            reader.setLenient(true);
//                            Log.d("SNAPPPP", "onReceiveValue: "+ value);

                            try {
                                if(reader.peek() == JsonToken.STRING) {
                                    String domStr = reader.nextString();
                                    if(domStr != null) {
                                        Log.d("SNAPPPP", "onReceiveValue: "+ new JSONObject(domStr));
                                    }
                                }
                            } catch (IOException | JSONException e) {
                                Log.d("SNAPPPP", " " + e);
                            }
                        }
                    });

                }

            }
        });
    }
}