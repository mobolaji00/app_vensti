package com.example.mobolajioo.vensti;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by mobolajioo on 3/14/17.
 */

public class ViewVenstiStrategies {

    private static final String PDF_MIME_TYPE = "application/pdf";
    private static final String HTML_MIME_TYPE = "text/html";
    public static Uri pdfURIObject = null;
    public static String pdfFileName = null;

    public ViewVenstiStrategies(Context context, String fileURL, Activity oA)
    {
        String pdf_file_ext = context.getResources().getString(R.string.pdf_file_ext);
        String video_file_ext = context.getResources().getString(R.string.video_file_ext);
        String video = context.getResources().getString(R.string.video);
        String chooser_title = context.getResources().getString(R.string.chooser_title);

        if (fileURL.contains(pdf_file_ext))
        {
            viewPdfs(context,chooser_title,fileURL,oA);
        }
        else if (fileURL.contains(video_file_ext))
        {
            viewVideos(context,chooser_title,fileURL,oA);
        }
    }

    public static void viewPdfs (Context context, String chooser_title, String pdfURL, Activity oA)
    {
        //create chooser
        Intent chooser;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File("test.pdf")),PDF_MIME_TYPE);

        // Verify the intent will resolve to at least one activity
        if (checkIfViewerSupported(intent, context)) {
            intent.setDataAndType(getPdfFile(intent,context,pdfURL,oA),PDF_MIME_TYPE);

            chooser = Intent.createChooser(intent, chooser_title);
            chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(chooser);
        }
        else
        {
            intent.setDataAndType((Uri.parse(pdfURL)),HTML_MIME_TYPE);
            chooser = Intent.createChooser(intent, chooser_title);
            chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (checkIfViewerSupported(intent, context))
            {
                context.startActivity(chooser);
            }
        }

    }

    public static boolean checkIfViewerSupported(Intent intent, Context context)
    {
        return intent.resolveActivity(context.getPackageManager()) != null;
    }

    public static Uri getPdfFile(Intent intent, Context context, String pdfURL, Activity oA)
    {
        Uri pdfUri = createPdfFileObject(context,pdfURL);

        if (new File(pdfUri.getPath()).exists())
        {
            return pdfUri;
        }
        else
        {
            DownloadPdfFile(context, oA, pdfURL);
            return ViewVenstiStrategies.pdfURIObject;
        }
    }

    public static Uri createPdfFileObject (Context context,String pdfURL)

    {
        // Get pdf file name
        ViewVenstiStrategies.pdfFileName = pdfURL.substring(pdfURL.lastIndexOf( "/" ) + 1 );

        //Modify pdf file object
        ViewVenstiStrategies.pdfURIObject = Uri.fromFile(new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), ViewVenstiStrategies.pdfFileName));

        return ViewVenstiStrategies.pdfURIObject;
    }

    public static void DownloadPdfFile(Context context, Activity oA, String pdfURL)
    {

        // Create the download request
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(pdfURL));
        r.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, ViewVenstiStrategies.pdfFileName);

        final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                context.unregisterReceiver(this);

                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Uri pdfURI = dm.getUriForDownloadedFile(downloadId);
                if (pdfURI != null) {
                    ViewVenstiStrategies.pdfURIObject = pdfURI;
                } else {
                    System.out.println("Download Unsuccessful");
                }
            }
        };
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        // Enqueue the request
        dm.enqueue(r);
    }
public static void viewVideos(Context context, String chooser_title, String videoURL, Activity oA){

    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(videoURL));
    Intent chooser;

    // Verify the intent will resolve to at least one activity
    if (checkIfViewerSupported(intent, context)) {

        chooser = Intent.createChooser(intent, chooser_title);
        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(chooser);
        //context.startActivity(intent);
    }
    else
    {
        intent.setType(HTML_MIME_TYPE);
        //intent.setData(Uri.parse("https://drive.google.com/drive/my-drive"));
        chooser = Intent.createChooser(intent, chooser_title);
        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (checkIfViewerSupported(intent, context))
        {
            context.startActivity(chooser);
            //context.startActivity(intent);

        }
    }

}


}
