package nl.putoet.z669016.modernartui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

/**
 * AlertDialogFragment
 *
 * Customized alert dialog ... the Title and the message are centered in stead o (default)
 * left-aligned and have a customized padding to improve readability.
 *
 * Using an AlertDialog also means the button order is not as the sample app. The standard order
 * is 'neutral', 'negative', 'positive' ... and visiting the URL is what I consider positive.
 */
public class AlertDialogFragment extends DialogFragment {
    private Activity activity;

    public static AlertDialogFragment newInstance(final Activity activity) {
        final AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.activity = activity;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final TextView title = new TextView(activity);
        title.setText(getResources().getString(R.string.inspiration));
        title.setPadding(10, 60, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(18);

        final TextView msg = new TextView(activity);
        msg.setText(getResources().getString(R.string.click_more));
        msg.setPadding(10, 40, 10, 10);
        msg.setGravity(Gravity.CENTER);
        msg.setTextSize(18);

        return new AlertDialog.Builder(getActivity())
                .setCustomTitle(title)
                .setView(msg)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.visit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(getResources().getString(R.string.more_information_url)));
                                startActivity(intent);
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.not_now),
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int id) {
                            }
                        })
                .create();
    }
}