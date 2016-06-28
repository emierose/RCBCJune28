/* 
 *	Created by Ronald Phillip C. Cui Feb 3, 2014
 */
package UnlitechDevFramework.src.ud.framework.webservice.data;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WebServiceInfo {

    private String mUrl;
    private File mUploadFile;
    private List<NameValuePair> mParam;
    private String mMultipartName = "file";

    public WebServiceInfo(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public WebServiceInfo addParam(String name, String value) {

        if (mParam == null) mParam = new ArrayList<NameValuePair>();
        mParam.add(new BasicNameValuePair(name, value));

        return this;
    }

    public List<NameValuePair> getParam() {
        return mParam;
    }

    public File getUploadFile() {
        return mUploadFile;
    }

    public void setUploadFile(File file) {
        mUploadFile = file;
    }

    public String getMultipartName() {
        return mMultipartName;
    }

    public void setMultipartName(String fileName) {
        mMultipartName = fileName;
    }

}
