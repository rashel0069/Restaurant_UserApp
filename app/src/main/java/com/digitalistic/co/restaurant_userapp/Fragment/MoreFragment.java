package com.digitalistic.co.restaurant_userapp.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalistic.co.restaurant_userapp.OpenBrowser;
import com.digitalistic.co.restaurant_userapp.Qr_Scanner;
import com.digitalistic.co.restaurant_userapp.R;
import com.digitalistic.co.restaurant_userapp.User_Login;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MoreFragment extends Fragment {
    public static final String MY_User = "com.digitalistic.co.restaurant_userapp";
    LinearLayout logout,qrActivity;
    TextView edit_tx, done_tx;
    boolean check=false;
    TextView update_address;

    public MoreFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        done_tx = view.findViewById(R.id.done_id);
        edit_tx = view.findViewById(R.id.edit_id);
        update_address = view.findViewById(R.id.address_update_id);
        logout = view.findViewById(R.id.logout_id);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

        //qr activity
        qrActivity = view.findViewById(R.id.qr_scanner_id);
        qrActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withContext(getContext()).withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                scanCode();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();

            }
        });

        return view;

    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(Qr_Scanner.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {

        if (result.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Digi_Food");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  Intent intent = new Intent(getContext(), OpenBrowser.class);
                  intent.putExtra("URL",result.getContents());
                  startActivity(intent);
                }
            }).show();
        }
    });

    private void LogOut(){
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences(MY_User,MODE_PRIVATE).edit();
        editor.clear().commit();
        Intent intent = new Intent(getContext(), User_Login.class);
        startActivity(intent);

    }
}