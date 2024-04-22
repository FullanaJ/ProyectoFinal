package com.fullana.proyectofinal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fullana.proyectofinal.model.bill.Bill;
import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.model.bill.FinalBill;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommonUtils {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String DATE_FORMAT = "dd/MM/yy";
    public static final int PICK_PDF_FILE = 2;
    public static final int CREATE_PDF_FILE = 1;

    public static <T> T readFile(File file, Class<T> aClass) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return GSON.fromJson(bufferedReader, TypeToken.of(aClass).getType());
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    System.out.println(ex);
                    return null;
                }
            }
            return null;
        }
    }

    /**
     * Write a file
     *
     * @param value
     * @param file
     * @param <T>
     */
    public static <T> void writeFile(T value, File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            GSON.toJson(value, fileWriter);
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                try {
                    file.createNewFile();
                    writeFile(value, file);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    /**
     * Add a bill to a file
     *
     * @param newBill
     * @param file
     */
    public static void addBillToFile(FinalBill newBill, File file) {
        List<FinalBill> bills = (List<FinalBill>) readFile(file, List.class);
        if (bills == null)
            bills = List.of(newBill);
        else
            bills.add(newBill);
        writeFile(bills, file);
    }

    /**
     * Show a toast
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Disable views
     *
     * @param views
     */
    public static void goneViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * Enable views
     *
     * @param views
     */
    public static void visibleViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Format a float number
     *
     * @param number
     * @return
     */
    public static String formatFloat(float number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(number);
    }

    public static File copyInputStreamToFile(InputStream in, File file) {

        try (OutputStream out = new FileOutputStream(file)) {

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Expand a view
     *
     * @param v
     */
    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = getAnimation(v, targetHeight);
        v.startAnimation(a);
    }

    /**
     * Get the animation
     *
     * @param v
     * @param targetHeight
     * @return
     */
    @NonNull
    private static Animation getAnimation(View v, int targetHeight) {
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 3dp/ms
        a.setDuration(((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 3L));
        return a;
    }

    /**
     * Collapse a view
     *
     * @param v
     * @param targetHeight
     */
    public static void collapse(final View v, final int targetHeight) {
        final int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
//                if (interpolatedTime == 1) {
//                    v.setVisibility(View.GONE);
//                } else {
//                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
//                    v.requestLayout();
//                }
                int newHeight = initialHeight - (int) (initialHeight * interpolatedTime);
                if (newHeight >= targetHeight) {
                    v.getLayoutParams().height = newHeight;
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration(((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) * 3L));
        v.startAnimation(a);
    }

    /**
     * Navigate to a fragment
     *
     * @param activity
     * @param b
     * @param navId
     */
    public static void navigate(Activity activity, Bundle b, int navId) {
        NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_content_main);
        navController.navigate(navId, b);
    }

    /**
     * Convert a Date to a string date
     *
     * @param date
     * @return
     */
    public static String stringDateFromDate(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    /**
     * Convert a string date to a Date
     *
     * @param dateInString
     * @return
     * @throws ParseException
     */
    public static Date datefromStringDate(String dateInString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.forLanguageTag("es-ES"));
        return formatter.parse(dateInString);
    }

    public static void openDocument(Activity activity, Uri pickerInitialUri) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
//        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        activity.startActivityForResult(intent, PICK_PDF_FILE);
    }


    /**
     * Create a PDF file
     *
     * @param activity
     * @param name
     */
    public static void createPDFFile(Activity activity, String name) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, name + ".pdf");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
//        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        activity.startActivityForResult(intent, CREATE_PDF_FILE);

    }
}

