/* 
 *	Created by Ronald Phillip C. Cui Feb 3, 2014
 */
package UnlitechDevFramework.src.ud.framework.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;

public class WebServiceUtil {

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();

    }

    public static HttpGet createHttpGetObject(String url) {
        return new HttpGet(url);
    }

    public static HttpPost createhttpPostObject(String url) {
        return new HttpPost(url);
    }

    public static HttpClient createClient() {
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        return client;
    }

    public static HttpURLConnection createMultipartConnect(URL url, String method) throws IOException {

        HttpURLConnection conn = null;
        // Open a HTTP  connection to  the URL
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true); // Allow Outputs
        conn.setRequestMethod(method);
        conn.setRequestProperty("Connection", "close");
        conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + FileUtil.BOUNDARY);
        System.setProperty("http.keepAlive", "false");
        return conn;

    }

    public static InputStream getInputStream(WebServiceInfo wsInfo, HttpPost post) throws IOException, Exception {

        post = WebServiceUtil.createhttpPostObject(wsInfo.getUrl());
        HttpClient client = WebServiceUtil.createClient();

        if (wsInfo.getParam() != null)
            post.setEntity(new UrlEncodedFormEntity(wsInfo.getParam()));

        HttpResponse response = client.execute(post);

        if (response != null) {

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK)
                return response.getEntity().getContent();

        }

        return null;

    }

    public static InputStream getInputStream(WebServiceInfo wsInfo, HttpGet get) throws IOException, Exception {
        get = WebServiceUtil.createHttpGetObject(wsInfo.getUrl());
        HttpClient client = WebServiceUtil.createClient();

        HttpResponse response = client.execute(get);

        if (response != null) {

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK)
                return response.getEntity().getContent();

        }

        return null;
    }

    public static HttpEntity getHttpEntity(WebServiceInfo wsInfo, HttpPost post) throws IOException, Exception {
        post = WebServiceUtil.createhttpPostObject(wsInfo.getUrl());
//        post.setHeader("User-Agent","UPS Mobile App");
//        post.addHeader("Authorization", "Basic " + Base64.encodeToString(("Pleasewaitforconnectionstatus" + ":" + "please(><)wa!t").getBytes(), Base64.NO_WRAP));
        HttpClient client = WebServiceUtil.createClient();

        if (wsInfo.getParam() != null)
            post.setEntity(new UrlEncodedFormEntity(wsInfo.getParam()));

        HttpResponse response = client.execute(post);

        if (response != null) {

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK)
                return response.getEntity();

        }

        return null;
    }

    public static HttpEntity getHttpEntity(WebServiceInfo wsInfo, HttpGet get) throws IOException, Exception {
        get = WebServiceUtil.createHttpGetObject(wsInfo.getUrl());
//        get.setHeader("User-Agent","UPS Mobile App");
//        get.addHeader("Authorization", "Basic " + Base64.encodeToString(("Pleasewaitforconnectionstatus" + ":" + "please(><)wa!t").getBytes(), Base64.NO_WRAP));
        HttpClient client = WebServiceUtil.createClient();
        HttpResponse response = client.execute(get);

        if (response != null) {

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK)
                return response.getEntity();

        }

        return null;
    }
}
