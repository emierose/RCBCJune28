/* 
 *	Created by Ronald Phillip C. Cui Feb 12, 2014
 */
package UnlitechDevFramework.src.ud.framework.webservice.commands;

import android.util.Log;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import UnlitechDevFramework.src.ud.framework.data.Response;
import UnlitechDevFramework.src.ud.framework.data.enums.Status;
import UnlitechDevFramework.src.ud.framework.utilities.FileUtil;
import UnlitechDevFramework.src.ud.framework.utilities.StringUtil;
import UnlitechDevFramework.src.ud.framework.utilities.WebServiceUtil;
import UnlitechDevFramework.src.ud.framework.webservice.NetworkTask.NetworkTaskListener;
import UnlitechDevFramework.src.ud.framework.webservice.data.WebServiceInfo;
import UnlitechDevFramework.src.ud.framework.webservice.interfaces.WebCommand;

public class UploadFileCommand implements WebCommand {

    HttpURLConnection conn = null;
    private WebServiceInfo wsInfo;
    private NetworkTaskListener mListener;

    public UploadFileCommand(WebServiceInfo wsInfo) {
        this.wsInfo = wsInfo;
    }

    @Override
    public Response execute() throws IOException, Exception {
        // TODO Auto-generated method stub
        File file = wsInfo.getUploadFile();

        Response response = new Response();

        if (file == null) {
            response.setStatus(Status.FAILED);
            response.setMessage(FileUtil.NO_FILE);
            return response;
        }

        if (!file.exists()) {
            response.setStatus(Status.FAILED);
            response.setMessage(FileUtil.FILE_NOT_FOUND + " " + file.getName());
            return response;
        }
        if (!file.isFile()) {
            response.setStatus(Status.FAILED);
            response.setMessage(FileUtil.NOT_A_FILE.replace("<file>", file.getName()));
            return response;
        }

        DataOutputStream dos = null;
        String lineEnd = FileUtil.LINE_END;
        String twoHyphens = FileUtil.TWO_HYPEN;
        String boundary = FileUtil.BOUNDARY;

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        URL url = new URL(wsInfo.getUrl());

        conn = WebServiceUtil.createMultipartConnect(url, "POST");

        dos = new DataOutputStream(conn.getOutputStream());

        if (wsInfo.getParam() != null) {
            for (NameValuePair param : wsInfo.getParam()) {
                FileUtil.writeURLConnectionParam(dos, param.getName(), param.getValue());
            }
        }

        String fileName = wsInfo.getMultipartName();

        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: form-data; name=\"" + fileName + "\";filename=\"" + file.getName() + "\"" + lineEnd);
        dos.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName()) + lineEnd);
        dos.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

        dos.writeBytes(lineEnd);

        dos.flush();

        FileInputStream fileInputStream = new FileInputStream(file);

        // create a buffer of  maximum size
        bytesAvailable = fileInputStream.available();

        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];

        // read file and write it into form...
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {

            dos.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        }

        // send multipart form data necesssary after file data...
        dos.writeBytes(lineEnd);
        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        // Responses from the server (code and message)
        int serverResponseCode = conn.getResponseCode();

        String serverResponseMessage = "";

        if (serverResponseCode == HttpStatus.SC_OK) {

            serverResponseMessage = conn.getResponseMessage();

            response.setResponse(StringUtil.streamToString(conn.getInputStream()));
            response.setStatus(Status.SUCCESS);
            response.setMessage(serverResponseCode + " " + serverResponseMessage);

            if (mListener != null)
                mListener.onProgressUpdate(1);

        } else {

            serverResponseMessage = conn.getResponseMessage();

            response.setResponse(StringUtil.streamToString(conn.getErrorStream()));
            response.setStatus(Status.FAILED);
            response.setMessage(serverResponseCode + " " + serverResponseMessage);

        }

        Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

        //close the streams //
        fileInputStream.close();
        dos.flush();
        dos.close();
        conn.disconnect();

        return response;

    }

    @Override
    public void cancel() {
        // TODO Auto-generated method stub
        if (conn != null)
            conn.disconnect();
    }

    @Override
    public void setProgessListener(NetworkTaskListener listener) {
        mListener = listener;
    }

}
