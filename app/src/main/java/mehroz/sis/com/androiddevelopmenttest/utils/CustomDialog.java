package mehroz.sis.com.androiddevelopmenttest.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mehroz.sis.com.androiddevelopmenttest.R;

public class CustomDialog {

    @SuppressLint("StaticFieldLeak")
    private static CustomDialog customDialog;
    @SuppressLint("StaticFieldLeak")
    public DialogNotifier.AlertDialogNotifier alertDialogNotifier;


    public static CustomDialog getInstance() {
        if (customDialog == null) {
            synchronized (CustomDialog.class) {
                customDialog = new CustomDialog();
            }
        }
        return customDialog;
    }


    public void setAlertDialogNotifier(DialogNotifier.AlertDialogNotifier alertDialogNotifier) {
        this.alertDialogNotifier = alertDialogNotifier;
    }


    public void createAlertDialog(Context context, String message) {

        @SuppressLint("RestrictedApi")
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Activity activity = (Activity) context;
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.utils_custom_alert_dialog, null);

        TextView dialogTitle = (TextView) dialogView.findViewById(R.id.AlertDialog_message);
        Button dialogYesButton = (Button) dialogView.findViewById(R.id.AlertDialog_OKBtn);
        ImageView dialogCancelButton = (ImageView) dialogView.findViewById(R.id.AlertDialog_cancelImage);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        if (alertDialog.getWindow() != null)
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialogTitle.setText(message);
        dialogYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                alertDialogNotifier.dialogAcceptNotifier();
            }
        });

        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                alertDialogNotifier.dialogCancelNotifier();
            }
        });
        alertDialog.show();

    }


    public interface DialogNotifier {

        interface AlertDialogNotifier {
            void dialogCancelNotifier();
            void dialogAcceptNotifier();
        }
    }
}
