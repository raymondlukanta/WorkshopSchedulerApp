package com.lukanta.raymond.workshopschedulerapp.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.lukanta.raymond.workshopschedulerapp.R;

/**
 * Created by raymondlukanta on 23/06/16.
 */
public class BaseFragment extends Fragment {
    private ProgressDialog progressDialog;

    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.setMessage(message);

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    protected void showProgressDialog() {
        showProgressDialog(getString(R.string.dialog_loading));
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        hideProgressDialog();
        super.onDestroy();
    }
}
