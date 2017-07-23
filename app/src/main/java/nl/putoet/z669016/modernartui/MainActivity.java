package nl.putoet.z669016.modernartui;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity class
 *
 * Show the Art and allow changing the color values using a slider. Only the blocks with a red,
 * yellow or blue background will be changed. The blocks will be detected automatically by
 * inspecting the TextViews in the ViewGroup.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ModernArtUI";

    // Lists used to store references to colored blocks in the ViewGroup to be changed using the slider
    private List<TextView> redViews = null;
    private List<TextView> yellowViews = null;
    private List<TextView> blueViews = null;

    // Color values to look for
    private int red = 0;
    private int yellow = 0;
    private int blue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewGroup viewGroup = getTopLevelViewGroup();

        red = getColorForColorId(R.color.red);
        yellow = getColorForColorId(R.color.yellow);
        blue = getColorForColorId(R.color.navy);

        redViews = findTextViewsForColor(viewGroup, red);
        yellowViews = findTextViewsForColor(viewGroup, yellow);
        blueViews = findTextViewsForColor(viewGroup, blue);

        Log.i(TAG, "Found " + redViews.size() + " red blocks");
        Log.i(TAG, "Found " + yellowViews.size() + " yellow blocks");
        Log.i(TAG, "Found " + blueViews.size() + " blue blocks");

        final SeekBar seek = (SeekBar) findViewById(R.id.seek);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final int change = (progress * (0xff / 100));

                changeTextViewColor(yellowViews, addBlueToColor(change, yellow));
                changeTextViewColor(redViews, addGreenToColor(change, red));
                changeTextViewColor(blueViews, addRedToColor(change, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private ViewGroup getTopLevelViewGroup() {
        return (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
    }

    private int getColorForColorId(final int colorId) {
        return ResourcesCompat.getColor(getResources(), colorId, null);
    }

    protected static int addRedToColor(final int change, final int color) {
        return addShiftedChangeToColor(change, 16, color);
    }

    protected static int addGreenToColor(final int change, final int color) {
        return addShiftedChangeToColor(change, 8, color);
    }

    protected static int addBlueToColor(final int change, final int color) {
        return addShiftedChangeToColor(change, 0, color);
    }

    protected static int addShiftedChangeToColor(final int change, final int shift, final int color) {
        return color | (change << shift);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more_information:
                final DialogFragment dialog = AlertDialogFragment.newInstance(this);
                dialog.show(getFragmentManager(), "Alert");
                return true;
            default:
                return false;
        }
    }

    protected static void changeTextViewColor(final List<TextView> views, final int color) {
        for (int idx = 0; idx < views.size(); idx++) {
            views.get(idx).setBackgroundColor(color);
        }
    }

    protected static  List<TextView> findTextViewsForColor(final ViewGroup group, final int color) {
        final List<TextView> views = new ArrayList<>();

        for (int i = group.getChildCount() - 1; i >= 0; i--) {
            final View child = group.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(findTextViewsForColor((ViewGroup) child, color));
            } else {
                if (child instanceof TextView) {
                    final Drawable background = child.getBackground();
                    if (background instanceof ColorDrawable) {
                        final int backgroundColor = ((ColorDrawable) background).getColor();
                        if (backgroundColor == color) {
                            views.add((TextView) child);
                        }
                    }
                }
            }
        }

        return views;
    }
}
