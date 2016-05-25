package moun.com.wimf;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import moun.com.wimf.database.WIMF_UserDAO;
import moun.com.wimf.model.WIMF_Utilisateur;

/**
 * Created by maiga mariam on 25/05/2016.
 */

public class ProfilActivity  extends Activity {

    private final int SELECT_PHOTO = 1;
    private ImageView imageView;
    private TextView nom;
    private TextView tel;
    private WIMF_UserDAO userDAO;
    private WIMF_Utilisateur utilisateur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_infos_layout);
        GridLayout vue = new GridLayout(this);
        vue.setBackgroundColor(Color.GREEN);
        userDAO = new WIMF_UserDAO(this);
        WIMF_Utilisateur utilisateur = userDAO.getUserDetails();

        imageView = (ImageView)findViewById(R.id.imageView);
        tel = (TextView)findViewById(R.id.user_tel) ;
        nom = (TextView)findViewById(R.id.user_name) ;

        tel.setText(utilisateur.get_tel());
        nom.setText(utilisateur.get_nom());
        Button pickImage = (Button) findViewById(R.id.btn_pick);
        pickImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        final Intent intent = getIntent();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        // Toast.makeText(this, selectedImage.toString(), Toast.LENGTH_LONG).show();
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}