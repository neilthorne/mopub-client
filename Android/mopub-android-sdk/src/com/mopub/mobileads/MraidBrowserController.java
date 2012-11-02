package com.mopub.mobileads;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

class MraidBrowserController extends MraidAbstractController {
    private static final String LOGTAG = "MraidBrowserController";

    MraidBrowserController(MraidView view) {
        super(view);
    }

    protected void open(String url) {
        Log.d(LOGTAG, "Opening in-app browser: " + url);

        MraidView view = getView();
        if (view.getOnOpenListener() != null) {
            view.getOnOpenListener().onOpen(view);
        }

        Uri uri = Uri.parse(url);
        String host = uri.getHost();

        if (url.startsWith("market:") || url.startsWith("tel:") ||
                url.startsWith("voicemail:") || url.startsWith("sms:") ||
                url.startsWith("mailto:") || url.startsWith("geo:") ||
                url.startsWith("google.streetview:") ||
                url.endsWith(".mp4") ||
                "play.google.com".equals(host) ||
                "market.android.com".equals(host)) {
            Context context = getView().getContext();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            i.putExtra(MraidBrowser.URL_EXTRA, url);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Context context = getView().getContext();
            Intent i = new Intent(context, MraidBrowser.class);
            i.putExtra(MraidBrowser.URL_EXTRA, url);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
